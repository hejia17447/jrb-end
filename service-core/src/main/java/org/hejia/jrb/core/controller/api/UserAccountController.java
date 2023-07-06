package org.hejia.jrb.core.controller.api;


import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hejia.common.result.Result;
import org.hejia.jrb.base.utils.JwtUtils;
import org.hejia.jrb.core.hfb.RequestHelper;
import org.hejia.jrb.core.service.UserAccountService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

