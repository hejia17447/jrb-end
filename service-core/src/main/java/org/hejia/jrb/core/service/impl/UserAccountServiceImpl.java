package org.hejia.jrb.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.hejia.common.exception.Assert;
import org.hejia.common.result.ResponseEnum;
import org.hejia.jrb.core.hfb.FormHelper;
import org.hejia.jrb.core.hfb.HfbConst;
import org.hejia.jrb.core.hfb.RequestHelper;
import org.hejia.jrb.core.mapper.UserAccountMapper;
import org.hejia.jrb.core.mapper.UserInfoMapper;
import org.hejia.jrb.core.pojo.entity.UserAccount;
import org.hejia.jrb.core.pojo.entity.UserInfo;
import org.hejia.jrb.core.service.UserAccountService;
import org.hejia.jrb.core.util.LendNoUtils;
import org.springframework.stereotype.Service;

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
@Service
@AllArgsConstructor
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService {

    private final UserInfoMapper userInfoMapper;

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
}
