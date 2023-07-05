package org.hejia.jrb.core.controller.api;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hejia.common.result.Result;
import org.hejia.jrb.core.pojo.entity.Dict;
import org.hejia.jrb.core.service.DictService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("api/core/dict")
public class DictController {

    private final DictService dictService;

    /**
     * 根据dictCode获取下级节点
     * @param dictCode 字典代码
     * @return 字典数据
     */
    @GetMapping("/findByDictCode/{dictCode}")
    public Result findByDictCode(@PathVariable("dictCode") String dictCode) {
        List<Dict> list = dictService.findByDictCode(dictCode);
        return Result.success().data("dictList", list);
    }

}

