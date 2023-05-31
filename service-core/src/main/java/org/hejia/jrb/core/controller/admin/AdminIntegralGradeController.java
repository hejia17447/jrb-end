package org.hejia.jrb.core.controller.admin;

import org.hejia.common.result.Result;
import org.hejia.jrb.core.pojo.entity.IntegralGrade;
import org.hejia.jrb.core.service.IntegralGradeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 积分等级接口
 */

// 用来处理跨域请求的注解
@CrossOrigin
@RestController
@RequestMapping("admin/core/integralGrade")
public class AdminIntegralGradeController {

    private final IntegralGradeService integralGradeService;

    public AdminIntegralGradeController(IntegralGradeService integralGradeService) {
        this.integralGradeService = integralGradeService;
    }


    /**
     * 积分等级列表接口
     * @return 积分等级列表
     */
    @GetMapping("/list")
    public Result listAll() {
        List<IntegralGrade> list = integralGradeService.list();
        return Result.success().data("list", list);
    }

    /**
     * 根据id删除积分等级
     * @param id 用户id
     * @return 删除结果
     */
    @DeleteMapping("/remove/{id}")
    public Result removeById(@PathVariable Long id) {
        boolean result = integralGradeService.removeById(id);
        if (result) {
            return Result.success().message("删除成功");
        } else {
            return Result.error().message("删除失败");
        }
    }

}
