package org.hejia.jrb.core.controller.api;


import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hejia.common.result.Result;
import org.hejia.jrb.base.utils.JwtUtils;
import org.hejia.jrb.core.pojo.vo.BorrowerVO;
import org.hejia.jrb.core.service.BorrowerService;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 借款人 前端控制器
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/core/borrower")
public class BorrowerController {

    private final BorrowerService borrowerService;

    /**
     * 保存借款人信息
     * @param borrowerVO 借款人信息
     * @param request 网络请求，获取token
     * @return 保存结果
     */
    @PostMapping("/auth/save")
    public Result save(@RequestBody BorrowerVO borrowerVO, HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        borrowerService.saveBorrowerVOByUserId(borrowerVO, userId);
        return Result.success().message("信息提交成功");
    }

    /**
     * 获取借款人认证状态
     * @param request 网络请求，获取token
     * @return 借款人认证状态
     */
    @GetMapping("/auth/getBorrowerStatus")
    public Result getBorrowerStatus(HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        Integer status = borrowerService.getStatusByUserId(userId);
        return Result.success().data("borrowerStatus", status);
    }

}

