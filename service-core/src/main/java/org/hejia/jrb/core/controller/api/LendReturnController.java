package org.hejia.jrb.core.controller.api;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hejia.common.result.Result;
import org.hejia.jrb.core.pojo.entity.LendReturn;
import org.hejia.jrb.core.service.LendReturnService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}

