package org.hejia.jrb.core.controller.api;


import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hejia.common.result.Result;
import org.hejia.jrb.base.utils.JwtUtils;
import org.hejia.jrb.core.pojo.entity.LendItemReturn;
import org.hejia.jrb.core.service.LendItemReturnService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 标的出借回款记录表 前端控制器
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/core/lendItemReturn")
public class LendItemReturnController {

    private final LendItemReturnService lendItemReturnService;

    /**
     * 获取列表
     * @param lendId 标的id
     * @param request 请求。用于获取token
     * @return 回款列表
     */
    @GetMapping("/auth/list/{lendId}")
    public Result list(@PathVariable Long lendId, HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        List<LendItemReturn> list = lendItemReturnService.selectByLendId(lendId, userId);
        return Result.success().data("list", list);
    }

}

