package org.hejia.jrb.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hejia.common.exception.Assert;
import org.hejia.common.result.ResponseEnum;
import org.hejia.jrb.base.dto.SmsDTO;
import org.hejia.jrb.core.enums.TransTypeEnum;
import org.hejia.jrb.core.hfb.FormHelper;
import org.hejia.jrb.core.hfb.HfbConst;
import org.hejia.jrb.core.hfb.RequestHelper;
import org.hejia.jrb.core.mapper.UserAccountMapper;
import org.hejia.jrb.core.mapper.UserInfoMapper;
import org.hejia.jrb.core.pojo.bo.TransFlowBO;
import org.hejia.jrb.core.pojo.entity.UserAccount;
import org.hejia.jrb.core.pojo.entity.UserInfo;
import org.hejia.jrb.core.service.TransFlowService;
import org.hejia.jrb.core.service.UserAccountService;
import org.hejia.jrb.core.service.UserBindService;
import org.hejia.jrb.core.service.UserInfoService;
import org.hejia.jrb.core.util.LendNoUtils;
import org.hejia.jrb.mq.constant.MQConst;
import org.hejia.jrb.mq.service.MQService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户账户 服务实现类
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService {

    private final UserInfoMapper userInfoMapper;

    private final UserAccountMapper userAccountMapper;

    private final TransFlowService transFlowService;

    private final UserBindService userBindService;

    private final UserInfoService userInfoService;

    private final MQService mQService;


    @Override
    public String commitCharge(BigDecimal chargeAmt, Long userId) {

        UserInfo userInfo = userInfoMapper.selectById(userId);
        String bindCode = userInfo.getBindCode();

        //判断账户绑定状态
        Assert.notEmpty(bindCode, ResponseEnum.USER_NO_BIND_ERROR);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("agentId", HfbConst.AGENT_ID);
        paramMap.put("agentBillNo", LendNoUtils.getNo());
        paramMap.put("bindCode", bindCode);
        paramMap.put("chargeAmt", chargeAmt);
        paramMap.put("feeAmt", new BigDecimal("0"));
        // 检查常量是否正确
        paramMap.put("notifyUrl", HfbConst.RECHARGE_NOTIFY_URL);
        paramMap.put("returnUrl", HfbConst.RECHARGE_RETURN_URL);
        paramMap.put("timestamp", RequestHelper.getTimestamp());
        String sign = RequestHelper.getSign(paramMap);
        paramMap.put("sign", sign);

        //构建充值自动提交表单
        return FormHelper.buildForm(HfbConst.RECHARGE_URL, paramMap);

    }

    /**
     * 充值回调接口
     * @param paramMap 回调参数
     * @return 回调结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String notify(Map<String, Object> paramMap) {

        log.info("充值成功：" + JSONObject.toJSONString(paramMap));

        //判断交易流水是否存在
        String agentBillNo = (String)paramMap.get("agentBillNo"); //商户充值订单号
        boolean isSave = transFlowService.isSaveTransFlow(agentBillNo);
        if(isSave){
            log.warn("幂等性返回");
            return "success";
        }

        // 充值人绑定协议号
        String bindCode = (String)paramMap.get("bindCode");
        // 充值金额
        String chargeAmt = (String)paramMap.get("chargeAmt");

        // 优化
        userAccountMapper.updateAccount(bindCode, new BigDecimal(chargeAmt), new BigDecimal(0));

        // 增加交易流水
        // 商户充值订单号
        TransFlowBO transFlowBO = new TransFlowBO(
                agentBillNo,
                bindCode,
        new BigDecimal(chargeAmt),
        TransTypeEnum.RECHARGE,

        "充值");
        transFlowService.saveTransFlow(transFlowBO);

        // 发信息
        log.info("发消息");
        String mobile = userInfoService.getMobileByBindCode(bindCode);
        SmsDTO smsDTO = new SmsDTO();
        smsDTO.setMobile(mobile);
        smsDTO.setMessage("充值成功");
        mQService.sendMessage(MQConst.EXCHANGE_TOPIC_SMS, MQConst.ROUTING_SMS_ITEM, smsDTO);

        return "success";
    }

    /**
     * 根据用户id查询用户账户信息
     * @param userId 用户id
     * @return 账户信息
     */
    @Override
    public BigDecimal getAccount(Long userId) {
        // 根据userId查找用户账户
        QueryWrapper<UserAccount> userAccountQueryWrapper = new QueryWrapper<>();
        userAccountQueryWrapper.eq("user_id", userId);
        UserAccount userAccount = baseMapper.selectOne(userAccountQueryWrapper);

        return userAccount.getAmount();
    }

    /**
     * 提现
     * @param fetchAmt 金额
     * @param userId 用户id
     * @return 结果
     */
    @Override
    public String commitWithdraw(BigDecimal fetchAmt, Long userId) {

        // 账户可用余额充足：当前用户的余额 >= 当前用户的提现金额
        // 获取当前用户的账户余额
        BigDecimal amount = this.getAccount(userId);
        Assert.isTrue(amount.doubleValue() >= fetchAmt.doubleValue(),
                ResponseEnum.NOT_SUFFICIENT_FUNDS_ERROR);

        String bindCode = userBindService.getBindCodeByUserId(userId);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("agentId", HfbConst.AGENT_ID);
        paramMap.put("agentBillNo", LendNoUtils.getWithdrawNo());
        paramMap.put("bindCode", bindCode);
        paramMap.put("fetchAmt", fetchAmt);
        paramMap.put("feeAmt", new BigDecimal(0));
        paramMap.put("notifyUrl", HfbConst.WITHDRAW_NOTIFY_URL);
        paramMap.put("returnUrl", HfbConst.WITHDRAW_RETURN_URL);
        paramMap.put("timestamp", RequestHelper.getTimestamp());
        String sign = RequestHelper.getSign(paramMap);
        paramMap.put("sign", sign);

        // 构建自动提交表单
        return FormHelper.buildForm(HfbConst.WITHDRAW_URL, paramMap);

    }

    /**
     * 提现回调
     * @param paramMap 参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void notifyWithdraw(Map<String, Object> paramMap) {
        log.info("提现成功");
        String agentBillNo = (String)paramMap.get("agentBillNo");
        boolean result = transFlowService.isSaveTransFlow(agentBillNo);
        if(result){
            log.warn("幂等性返回");
            return;
        }

        String bindCode = (String)paramMap.get("bindCode");
        String fetchAmt = (String)paramMap.get("fetchAmt");

        // 根据用户账户修改账户金额
        baseMapper.updateAccount(bindCode, new BigDecimal("-" + fetchAmt), new BigDecimal(0));

        // 增加交易流水
        TransFlowBO transFlowBO = new TransFlowBO(
                agentBillNo,
                bindCode,
        new BigDecimal(fetchAmt),
        TransTypeEnum.WITHDRAW,
        "提现");
        transFlowService.saveTransFlow(transFlowBO);
    }

}
