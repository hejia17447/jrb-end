package org.hejia.jrb.core.controller.admin;

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

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/admin/core/lend")
public class AdminLendController {

    private final LendService lendService;

    /**
     * 获取标的列表
     * @return 列表
     */
    @GetMapping("/list")
    public Result list() {
        List<Lend> lendList = lendService.selectList();
        return Result.success().data("list", lendList);
    }

    /**
     * 获取标的信息
     * @param id 标的id
     * @return 标的信息
     */
    @GetMapping("/show/{id}")
    public Result show(@PathVariable Long id) {
        Map<String, Object> result = lendService.getLendDetail(id);
        return Result.success().data("lendDetail", result);
    }

    /**
     * 放款
     * @param id 标id
     * @return 放款结果
     */
    @GetMapping("/makeLoan/{id}")
    public Result makeLoan(@PathVariable("id") Long id) {
        lendService.makeLoan(id);
        return Result.success().message("放款成功");
    }

}
