package org.hejia.jrb.core.controller.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hejia.common.result.Result;
import org.hejia.jrb.core.pojo.entity.LendItem;
import org.hejia.jrb.core.service.LendItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 标的的投资
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/admin/core/lendItem")
public class AdminLendItemController {

    private final LendItemService lendItemService;

    /**
     * 获取列表
     * @param lendId 标id
     * @return 标列表
     */
    @GetMapping("/list/{lendId}")
    public Result list(@PathVariable Long lendId) {
        List<LendItem> lendItemList = lendItemService.selectByLendId(lendId);
        return Result.success().data("list", lendItemList);
    }

}
