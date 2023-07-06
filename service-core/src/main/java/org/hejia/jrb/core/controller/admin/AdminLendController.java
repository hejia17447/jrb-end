package org.hejia.jrb.core.controller.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hejia.common.result.Result;
import org.hejia.jrb.core.pojo.entity.Lend;
import org.hejia.jrb.core.service.LendService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/admin/core/lend")
public class AdminLendController {

    private final LendService lendService;

    @GetMapping("/list")
    public Result list() {
        List<Lend> lendList = lendService.selectList();
        return Result.success().data("list", lendList);
    }

}
