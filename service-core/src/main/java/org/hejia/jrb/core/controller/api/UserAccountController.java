package org.hejia.jrb.core.controller.api;


import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hejia.common.result.Result;
import org.hejia.jrb.base.utils.JwtUtils;
import org.hejia.jrb.core.hfb.RequestHelper;
import org.hejia.jrb.core.service.UserAccountService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 用户账户 前端控制器
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/core/userAccount")
public class UserAccountController {

    private final UserAccountService userAccountService;

    /**
     * 充值
     * @param chargeAmt 充值金额
     * @param request 网络请求
     * @return 充值结果
     */
    @PostMapping("/auth/commitCharge/{chargeAmt}")
    public final Result commitCharge(@PathVariable BigDecimal chargeAmt, HttpServletRequest request) {

        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        String formStr = userAccountService.commitCharge(chargeAmt, userId);
        return Result.success().data("formStr", formStr);

    }

    /**
     * 用户充值异步回调
     * @param request 网络请求
     * @return 回调结果
     */
    @PostMapping("/notify")
    public String notify(HttpServletRequest request) {

        Map<String, Object> paramMap = RequestHelper.switchMap(request.getParameterMap());

        log.info("用户充值异步回调：" + JSON.toJSONString(paramMap));

        // 校验签名
        if(RequestHelper.isSignEquals(paramMap)) {
            // 充值成功交易
            if("0001".equals(paramMap.get("resultCode"))) {
                return userAccountService.notify(paramMap);
            } else {
                log.info("用户充值异步回调充值失败：" + JSON.toJSONString(paramMap));
                return "success";
            }
        } else {
            log.info("用户充值异步回调签名错误：" + JSON.toJSONString(paramMap));
            return "fail";
        }

    }

    /**
     * 查询账户余额
     * @param request 请求，获取token
     * @return 查询结果
     */
    @GetMapping("/auth/getAccount")
    public Result getAccount(HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        BigDecimal account = userAccountService.getAccount(userId);
        return Result.success().data("account", account);
    }

    /**
     * 用户提现
     * @param fetchAmt 体现金额
     * @param request 获取token
     * @return 提现结果
     */
    @PostMapping("/auth/commitWithdraw/{fetchAmt}")
    public Result commitWithdraw(@PathVariable BigDecimal fetchAmt, HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        String formStr = userAccountService.commitWithdraw(fetchAmt, userId);
        return Result.success().data("formStr", formStr);
    }

    /**
     * 用户提现异步回调
     * @param request 请求
     * @return 回调结果
     */
    @PostMapping("/notifyWithdraw")
    public String notifyWithdraw(HttpServletRequest request) {
        Map<String, Object> paramMap = RequestHelper.switchMap(request.getParameterMap());
        log.info("提现异步回调：" + JSON.toJSONString(paramMap));

        // 校验签名
        if(RequestHelper.isSignEquals(paramMap)) {
            // 提现成功交易
            if("0001".equals(paramMap.get("resultCode"))) {
                userAccountService.notifyWithdraw(paramMap);
            } else {
                log.info("提现异步回调充值失败：" + JSON.toJSONString(paramMap));
                return "fail";
            }
        } else {
            log.info("提现异步回调签名错误：" + JSON.toJSONString(paramMap));
            return "fail";
        }
        return "success";
    }

}

