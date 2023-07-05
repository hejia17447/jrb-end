package org.hejia.jrb.core.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hejia.common.result.Result;
import org.hejia.jrb.core.pojo.entity.Borrower;
import org.hejia.jrb.core.service.BorrowerService;
import org.springframework.web.bind.annotation.*;

/**
 * 借款人管理
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/admin/core/borrower")
public class AdminBorrowerController {

    private final BorrowerService borrowerService;

    /**
     * 获取借款人分页列表
     * @param page 当前页码
     * @param limit 每页记录数
     * @param keyword 查询关键字
     * @return 获取结果
     */
    @GetMapping("/list/{page}/{limit}")
    public Result listPage(
            @PathVariable Long page,
            @PathVariable Long limit,
            @RequestParam String keyword) {
        Page<Borrower> pageParam = new Page<>(page, limit);
        IPage<Borrower> pageModel = borrowerService.listPage(pageParam, keyword);
        return Result.success().data("pageModel", pageModel);

    }


}
