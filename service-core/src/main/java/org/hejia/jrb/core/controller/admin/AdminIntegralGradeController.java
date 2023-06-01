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
            return Result.success().message("删除成功！！！");
        } else {
            return Result.error().message("删除失败！！！");
        }
    }

    /**
     * 新增积分等级
     * @param integralGrade 积分信息
     * @return 新增结果
     */
    @PostMapping("/save")
    public Result save(@RequestBody IntegralGrade integralGrade) {
        boolean result = integralGradeService.save(integralGrade);
        if (result) {
            return Result.success().message("保存成功！！！");
        } else {
            return Result.error().message("保存失败！！！");
        }
    }

    /**
     * 根据id查询
     * @param id 用户id
     * @return 积分信息
     */
    @GetMapping("/get/{id}")
    public Result getById(@PathVariable Long id) {
        IntegralGrade integralGrade = integralGradeService.getById(id);
        if (integralGrade != null) {
            return Result.success().data("record", integralGrade);
        } else {
            return Result.error().message("数据不存在！！！");
        }
    }

    /**
     * 根据id修改
     * @param integralGrade 用户信息
     * @return 修改结果
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody IntegralGrade integralGrade) {
        boolean result = integralGradeService.updateById(integralGrade);
        if (result) {
            return Result.success().message("修改成功！！！");
        } else {
            return Result.error().message("修改失败！！！");
        }
    }

}
