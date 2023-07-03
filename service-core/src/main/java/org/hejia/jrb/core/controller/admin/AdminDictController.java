package org.hejia.jrb.core.controller.admin;

import com.alibaba.excel.EasyExcel;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hejia.common.exception.BusinessException;
import org.hejia.common.result.ResponseEnum;
import org.hejia.common.result.Result;
import org.hejia.jrb.core.pojo.dto.ExcelDictDTO;
import org.hejia.jrb.core.pojo.entity.Dict;
import org.hejia.jrb.core.service.DictService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

    /**
     * 导出字典API
     * @param response 数据流
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // URLEncoder.encode可以防止中文乱码
        String fileName = URLEncoder.encode("MyDict", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        try {
            EasyExcel.write(response.getOutputStream(), ExcelDictDTO.class).sheet("数据字典").doWrite(dictService.listDictData());
        } catch (IOException e) {
            //EXPORT_DATA_ERROR(104, "数据导出失败"),
            throw  new BusinessException(ResponseEnum.EXPORT_DATA_ERROR, e);
        }

    }

    /**
     * 根据上级id获取子节点数据列表API
     * @param parentId 父id
     * @return 子节点列表
     */
    @GetMapping("/listByParentId/{parentId}")
    public Result listByParentId(@PathVariable Long parentId) {
        List<Dict> dictList = dictService.listByParentId(parentId);
        return Result.success().data("list", dictList);
    }

}
