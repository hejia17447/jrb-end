package org.hejia.jrb.core.service;

import org.hejia.jrb.core.pojo.entity.UserAccount;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 用户账户 服务类
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
public interface UserAccountService extends IService<UserAccount> {

    /**
     * 提交充值金额
     * @param chargeAmt 充值金额
     * @param userId 用户id
     * @return 提交结果
     */
    String commitCharge(BigDecimal chargeAmt, Long userId);

    /**
     * 充值回调接口
     * @param paramMap 回调参数
     * @return 回调结果
     */
    String notify(Map<String, Object> paramMap);

    /**
     * 根据用户id查询用户账户信息
     * @param userId 用户id
     * @return 账户信息
     */
    BigDecimal getAccount(Long userId);

    /**
     * 提现
     * @param fetchAmt 金额
     * @param userId 用户id
     * @return 结果
     */
    String commitWithdraw(BigDecimal fetchAmt, Long userId);

    /**
     * 提现回调
     * @param paramMap 参数
     */
    void notifyWithdraw(Map<String, Object> paramMap);

}
