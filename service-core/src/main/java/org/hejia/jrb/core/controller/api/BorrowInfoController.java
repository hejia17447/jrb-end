package org.hejia.jrb.core.controller.api;


import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hejia.common.result.Result;
import org.hejia.jrb.base.utils.JwtUtils;
import org.hejia.jrb.core.pojo.entity.BorrowInfo;
import org.hejia.jrb.core.service.BorrowInfoService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * <p>
 * 借款信息表 前端控制器
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/core/borrowInfo")
public class BorrowInfoController {

    private final BorrowInfoService borrowInfoService;

    /**
     * 获取借款额度
     * @param request 网络请求
     * @return 获取结果
     */
    @GetMapping("/auth/getBorrowAmount")
    public Result getBorrowAmount(HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        BigDecimal borrowAmount = borrowInfoService.getBorrowAmount(userId);
        return Result.success().data("borrowAmount", borrowAmount);
    }

    /**
     * 提交借款申请
     * @param borrowInfo 借款信息
     * @param request 网络请求
     * @return 提交结果
     */
    @PostMapping("/auth/save")
    public Result save(@RequestBody BorrowInfo borrowInfo, HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        borrowInfoService.saveBorrowInfo(borrowInfo, userId);
        return Result.success().message("提交成功");
    }

    /**
     * 获取借款申请审批状态
     * @param request 网络请求
     * @return 借款申请审批状态
     */
    @GetMapping("/auth/getBorrowInfoStatus")
    public Result getBorrowerStatus(HttpServletRequest request){
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        Integer status = borrowInfoService.getStatusByUserId(userId);
        return Result.success().data("borrowInfoStatus", status);
    }

}

