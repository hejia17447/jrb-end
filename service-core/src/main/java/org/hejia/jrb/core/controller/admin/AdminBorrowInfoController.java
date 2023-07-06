package org.hejia.jrb.core.controller.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hejia.common.result.Result;
import org.hejia.jrb.core.pojo.entity.BorrowInfo;
import org.hejia.jrb.core.service.BorrowInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/admin/core/borrowInfo")
public class AdminBorrowInfoController {

    private final BorrowInfoService borrowInfoService;

    /**
     * 获取借款信息列表
     * @return 列表
     */
    @GetMapping("/list")
    public Result list() {
        List<BorrowInfo> borrowInfoList = borrowInfoService.selectList();
        return Result.success().data("list", borrowInfoList);
    }



}
