package org.hejia.jrb.core.controller.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hejia.common.exception.BusinessException;
import org.hejia.common.result.ResponseEnum;
import org.hejia.common.result.Result;
import org.hejia.jrb.core.service.DictService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author HJ
 * @date 2023/7/2 10:29
 * @description 字典接口
 */
@Slf4j
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/admin/core/dict")
public class AdminDictController {

    private final DictService dictService;

    /**
     * Excel批量导入数据字典
     * @param file Excel文件
     * @return 导入结果
     */
    @PostMapping("/import")
    public Result batchImport(@RequestParam("file")MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            dictService.importData(inputStream);
            return Result.success().message("批量导入成功");
        } catch (IOException e) {
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR, e);
        }
    }

}
