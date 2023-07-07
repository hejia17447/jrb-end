package org.hejia.jrb.core.controller.api;


import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hejia.common.result.Result;
import org.hejia.jrb.base.utils.JwtUtils;
import org.hejia.jrb.core.hfb.RequestHelper;
import org.hejia.jrb.core.pojo.entity.LendReturn;
import org.hejia.jrb.core.service.LendReturnService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 还款记录表 前端控制器
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/core/lendReturn")
public class LendReturnController {

    private final LendReturnService lendReturnService;

    /**
     * 获取列表
     * @param lendId 标的id
     * @return 标的还款计划
     */
    @GetMapping("/list/{lendId}")
    public Result list(@PathVariable Long lendId) {
        List<LendReturn> list = lendReturnService.selectByLendId(lendId);
        return Result.success().data("list", list);
    }

    /**
     * 用户还款
     * @param lendReturnId 还款计划id
     * @param request 请求
     * @return 还款结果
     */
    @PostMapping("/auth/commitReturn/{lendReturnId}")
    public Result commitReturn(
        @PathVariable Long lendReturnId, HttpServletRequest request) {

        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        String formStr = lendReturnService.commitReturn(lendReturnId, userId);
        return Result.success().data("formStr", formStr);
    }

    /**
     * 还款异步回调
     * @param request 请求
     * @return 回调结果
     */
    @PostMapping("/notifyUrl")
    public String notifyUrl(HttpServletRequest request) {

        Map<String, Object> paramMap = RequestHelper.switchMap(request.getParameterMap());
        log.info("还款异步回调：" + JSON.toJSONString(paramMap));

        // 校验签名
        if(RequestHelper.isSignEquals(paramMap)) {
            if("0001".equals(paramMap.get("resultCode"))) {
                lendReturnService.notify(paramMap);
            } else {
                log.info("还款异步回调失败：" + JSON.toJSONString(paramMap));
                return "fail";
            }
        } else {
            log.info("还款异步回调签名错误：" + JSON.toJSONString(paramMap));
            return "fail";
        }
        return "success";
    }

}

