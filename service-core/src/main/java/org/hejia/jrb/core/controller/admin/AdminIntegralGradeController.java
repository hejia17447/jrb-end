package org.hejia.jrb.core.controller.admin;

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
    public List<IntegralGrade> list() {
        return integralGradeService.list();
    }

    /**
     * 根据id删除积分等级
     * @param id 用户id
     * @return 删除结果
     */
    @DeleteMapping("/remove/{id}")
    public boolean removeById(@PathVariable Long id) {
        return integralGradeService.removeById(id);
    }

}
