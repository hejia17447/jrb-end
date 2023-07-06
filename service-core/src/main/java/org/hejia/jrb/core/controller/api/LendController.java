package org.hejia.jrb.core.controller.api;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hejia.common.result.Result;
import org.hejia.jrb.core.pojo.entity.Lend;
import org.hejia.jrb.core.service.LendService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标的准备表 前端控制器
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/core/lend")
public class LendController {

    private final LendService lendService;

    /**
     * 获取标的列表
     * @return 标的信息
     */
    @GetMapping("/list")
    public Result list() {
        List<Lend> lendList = lendService.selectList();
        return Result.success().data("lendList", lendList);
    }

    /**
     * 获取标的信息
     * @param id 标id
     * @return 表的信息
     */
    @GetMapping("/show/{id}")
    public Result show(@PathVariable Long id) {
        Map<String, Object> lendDetail = lendService.getLendDetail(id);
        return Result.success().data("lendDetail", lendDetail);
    }

}

