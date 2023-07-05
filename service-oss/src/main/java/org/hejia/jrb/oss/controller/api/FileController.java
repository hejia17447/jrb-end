package org.hejia.jrb.oss.controller.api;

import lombok.AllArgsConstructor;
import org.hejia.common.exception.BusinessException;
import org.hejia.common.result.ResponseEnum;
import org.hejia.common.result.Result;
import org.hejia.jrb.oss.service.FileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@AllArgsConstructor
@RequestMapping("/api/oss/file")
public class FileController {

    private final FileService fileService;

    /**
     * 文件上传API
     * @param file 文件数据
     * @param module 上传类型
     * @return 上传结果
     */
    @PostMapping("/upload")
    public Result upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("module") String module
    ) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String uploadUrl = fileService.upload(inputStream, module, originalFilename);

            return Result.success().message("文件上传成功").data("url", uploadUrl);

        } catch (IOException e) {
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR, e);
        }
    }


    @DeleteMapping("/remove")
    public Result remove(
        @RequestParam("url") String url) {
        fileService.removeFile(url);
        return Result.success().message("删除成功");
    }

}
