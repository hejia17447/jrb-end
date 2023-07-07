package org.hejia.jrb.core.controller.admin;

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

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/admin/core/lendReturn")
public class AdminLendReturnController {

    private final LendReturnService lendReturnService;

    /**
     * 获取列表
     * @param lendId 标id
     * @return 还款计划
     */
    @GetMapping("/list/{lendId}")
    public Result list(@PathVariable("lendId") Long lendId) {
        List<LendReturn> list = lendReturnService.selectByLendId(lendId);
        return Result.success().data("list", list);
    }

}
