package org.hejia.jrb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.hejia.common.exception.Assert;
import org.hejia.common.result.ResponseEnum;
import org.hejia.jrb.core.enums.LendStatusEnum;
import org.hejia.jrb.core.enums.TransTypeEnum;
import org.hejia.jrb.core.hfb.FormHelper;
import org.hejia.jrb.core.hfb.HfbConst;
import org.hejia.jrb.core.hfb.RequestHelper;
import org.hejia.jrb.core.mapper.LendItemMapper;
import org.hejia.jrb.core.mapper.LendMapper;
import org.hejia.jrb.core.mapper.UserAccountMapper;
import org.hejia.jrb.core.pojo.bo.TransFlowBO;
import org.hejia.jrb.core.pojo.entity.Lend;
import org.hejia.jrb.core.pojo.entity.LendItem;
import org.hejia.jrb.core.pojo.vo.InvestVO;
import org.hejia.jrb.core.service.*;
import org.hejia.jrb.core.util.LendNoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标的出借记录表 服务实现类
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
@Slf4j
@Service
public class LendItemServiceImpl extends ServiceImpl<LendItemMapper, LendItem> implements LendItemService {

    private LendMapper lendMapper;

    private LendService lendService;

    private UserAccountService userAccountService;

    private UserBindService userBindService;

    private TransFlowService transFlowService;

    private UserAccountMapper userAccountMapper;

    @Autowired
    public void setLendMapper(LendMapper lendMapper) {
        this.lendMapper = lendMapper;
    }

    @Autowired
    public LendItemServiceImpl(@Lazy LendService lendService) {
        this.lendService = lendService;
    }

    @Autowired
    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Autowired
    public void setUserBindService(UserBindService userBindService) {
        this.userBindService = userBindService;
    }

    @Autowired
    public void setTransFlowService(TransFlowService transFlowService) {
        this.transFlowService = transFlowService;
    }

    @Autowired
    public void setUserAccountMapper(UserAccountMapper userAccountMapper) {
        this.userAccountMapper = userAccountMapper;
    }

    /**
     * 提交投资
     * @param investVO 投资信息
     * @return 投资结果
     */
    @Override
    public String commitInvest(InvestVO investVO) {

        // 输入校验
        Long lendId = investVO.getLendId();
        // 1.获取标的信息
        Lend lend = lendMapper.selectById(lendId);
        // 2.标的状态必须为募资中
        Assert.isTrue(
                lend.getStatus().intValue() == LendStatusEnum.INVEST_RUN.getStatus().intValue(),
                ResponseEnum.LEND_INVEST_ERROR);
        // 3.标的不能超卖：(已投金额 + 本次投资金额 )>=标的金额（超卖）
        BigDecimal sum = lend.getInvestAmount().add(new BigDecimal(investVO.getInvestAmount()));
        Assert.isTrue(sum.doubleValue() <= lend.getAmount().doubleValue(),
                ResponseEnum.LEND_FULL_SCALE_ERROR);
        // 4.账户可用余额充足：当前用户的余额 >= 当前用户的投资金额（可以投资）
        Long investUserId = investVO.getInvestUserId();
        BigDecimal amount = userAccountService.getAccount(investUserId);//获取当前用户的账户余额
        Assert.isTrue(amount.doubleValue() >= Double.parseDouble(investVO.getInvestAmount()),
                ResponseEnum.NOT_SUFFICIENT_FUNDS_ERROR);

        // 在商户平台中生成投资信息
        // 1.标下的投资信息
        LendItem lendItem = new LendItem();
        lendItem.setInvestUserId(investUserId);//投资人id
        lendItem.setInvestName(investVO.getInvestName());//投资人名字
        String lendItemNo = LendNoUtils.getLendItemNo();
        lendItem.setLendItemNo(lendItemNo); //投资条目编号（一个Lend对应一个或多个LendItem）
        lendItem.setLendId(investVO.getLendId());//对应的标的id
        lendItem.setInvestAmount(new BigDecimal(investVO.getInvestAmount())); //此笔投资金额
        lendItem.setLendYearRate(lend.getLendYearRate());//年化
        lendItem.setInvestTime(LocalDateTime.now()); //投资时间
        lendItem.setLendStartDate(lend.getLendStartDate()); //开始时间
        lendItem.setLendEndDate(lend.getLendEndDate()); //结束时间

        // 2.预期收益
        BigDecimal expectAmount = lendService.getInterestCount(
                lendItem.getInvestAmount(),
                lendItem.getLendYearRate(),
                lend.getPeriod(),
                lend.getReturnMethod());
        lendItem.setExpectAmount(expectAmount);

        // 3.实际收益
        lendItem.setRealAmount(new BigDecimal(0));

        lendItem.setStatus(0);//默认状态：刚刚创建
        baseMapper.insert(lendItem);

        //组装投资相关的参数，提交到汇付宝资金托管平台==========================================
        //在托管平台同步用户的投资信息，修改用户的账户资金信息==========================================
        //获取投资人的绑定协议号
        String bindCode = userBindService.getBindCodeByUserId(investUserId);
        //获取借款人的绑定协议号
        String benefitBindCode = userBindService.getBindCodeByUserId(lend.getUserId());
        //封装提交至汇付宝的参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("agentId", HfbConst.AGENT_ID);
        paramMap.put("voteBindCode", bindCode);
        paramMap.put("benefitBindCode",benefitBindCode);
        paramMap.put("agentProjectCode", lend.getLendNo());//项目标号
        paramMap.put("agentProjectName", lend.getTitle());

        //在资金托管平台上的投资订单的唯一编号，要和lendItemNo保持一致。
        paramMap.put("agentBillNo", lendItemNo);//订单编号
        paramMap.put("voteAmt", investVO.getInvestAmount());
        paramMap.put("votePrizeAmt", "0");
        paramMap.put("voteFeeAmt", "0");
        paramMap.put("projectAmt", lend.getAmount()); //标的总金额
        paramMap.put("note", "");
        paramMap.put("notifyUrl", HfbConst.INVEST_NOTIFY_URL); //检查常量是否正确
        paramMap.put("returnUrl", HfbConst.INVEST_RETURN_URL);
        paramMap.put("timestamp", RequestHelper.getTimestamp());
        String sign = RequestHelper.getSign(paramMap);
        paramMap.put("sign", sign);

        //构建充值自动提交表单
        return FormHelper.buildForm(HfbConst.INVEST_URL, paramMap);

    }

    /**
     * 回调方法，添加交易流水
     * @param paramMap 回调信息
     */
    @Override
    public void notify(Map<String, Object> paramMap) {

        // 获取投资编号
        String agentBillNo = (String)paramMap.get("agentBillNo");

        boolean result = transFlowService.isSaveTransFlow(agentBillNo);
        if(result){
            log.warn("幂等性返回");
            return;
        }

        // 获取用户的绑定协议号
        String bindCode = (String)paramMap.get("voteBindCode");
        String voteAmt = (String)paramMap.get("voteAmt");

        // 修改商户系统中的用户账户金额：余额、冻结金额
        userAccountMapper.updateAccount(bindCode, new BigDecimal("-" + voteAmt), new BigDecimal(voteAmt));

        // 修改投资记录的投资状态改为已支付
        LendItem lendItem = this.getByLendItemNo(agentBillNo);
        lendItem.setStatus(1);//已支付
        baseMapper.updateById(lendItem);

        //修改标的信息：投资人数、已投金额
        Long lendId = lendItem.getLendId();
        Lend lend = lendMapper.selectById(lendId);
        lend.setInvestNum(lend.getInvestNum() + 1);
        lend.setInvestAmount(lend.getInvestAmount().add(lendItem.getInvestAmount()));
        lendMapper.updateById(lend);

        //新增交易流水
        TransFlowBO transFlowBO = new TransFlowBO(
                agentBillNo,
                bindCode,
        new BigDecimal(voteAmt),
        TransTypeEnum.INVEST_LOCK,
        "投资项目编号：" + lend.getLendNo() + "，项目名称：" + lend.getTitle());
        transFlowService.saveTransFlow(transFlowBO);

        log.info("投标成功");

    }

    /**
     * 根据lendId获取投资记录
     * @param lendId 投资id
     * @param status 投资状态
     * @return 投资信息
     */
    @Override
    public List<LendItem> selectByLendId(Long lendId, int status) {
        QueryWrapper<LendItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("lend_id", lendId);
        queryWrapper.eq("status", status);
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 根据标id查询标的投资信息
     * @param lendId 标id
     * @return 投资信息
     */
    @Override
    public List<LendItem> selectByLendId(Long lendId) {
        QueryWrapper<LendItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("lend_id", lendId);
        return baseMapper.selectList(queryWrapper);
    }

    private LendItem getByLendItemNo(String lendItemNo) {
        QueryWrapper<LendItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("lend_item_no", lendItemNo);
        return baseMapper.selectOne(queryWrapper);
    }

}
