package org.hejia.jrb.core.controller.api;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hejia.common.result.Result;
import org.hejia.jrb.base.utils.JwtUtils;
import org.hejia.jrb.core.hfb.RequestHelper;
import org.hejia.jrb.core.pojo.vo.UserBindVO;
import org.hejia.jrb.core.service.UserBindService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/core/userBind")
public class UserBindController {

    private final UserBindService userBindService;

    /**
     * 汇付宝绑定功能
     * @param userBindVO 用户信息
     * @param request 网络请求
     * @return 绑定结果
     */
    @PostMapping("/auth/bind")
    public Result bind(@RequestBody UserBindVO userBindVO, HttpServletRequest   request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        String formStr = userBindService.commitBindUser(userBindVO, userId);
        return Result.success().data("formStr", formStr);
    }

    @PostMapping("/notify")
    public String notify(HttpServletRequest request) {
        Map<String, Object> paramMap = RequestHelper.switchMap(request.getParameterMap());
        log.info("用户账号绑定异步回调：" + JSON.toJSONString(paramMap));
        // 校验签名
        if(!RequestHelper.isSignEquals(paramMap)) {
            log.error("用户账号绑定异步回调签名错误：" + JSON.toJSONString(paramMap));
            return "fail";
        }
        // 修改绑定状态
        userBindService.notify(paramMap);
        return "success";
    }


}
