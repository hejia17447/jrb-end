package org.hejia.jrb.core.controller.admin;

import org.hejia.jrb.core.pojo.entity.IntegralGrade;
import org.hejia.jrb.core.service.IntegralGradeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
