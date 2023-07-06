package org.hejia.jrb.core.controller.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hejia.common.result.Result;
import org.hejia.jrb.core.pojo.entity.BorrowInfo;
import org.hejia.jrb.core.pojo.vo.BorrowInfoApprovalVO;
import org.hejia.jrb.core.service.BorrowInfoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    /**
     * 获取借款信息
     * @param id 借款id
     * @return 借款详情
     */
    @GetMapping("/show/{id}")
    public Result show(@PathVariable long id) {
        Map<String, Object> borrowInfoDetail = borrowInfoService.getBorrowInfoDetail(id);
        return Result.success().data("borrowInfoDetail", borrowInfoDetail);
    }


    /**
     * 审批借款信息
     * @param borrowInfoApprovalVO 借款审批信息
     * @return 审批结果
     */
    @PostMapping("/approval")
    public Result approval(@RequestBody BorrowInfoApprovalVO borrowInfoApprovalVO) {
        borrowInfoService.approval(borrowInfoApprovalVO);
        return Result.success().message("审批完成");
    }

}
