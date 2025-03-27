package com.yuunik.selection.manager.controller;

import cn.hutool.core.bean.BeanUtil;
import com.yuunik.selection.manager.service.FileUploadService;
import com.yuunik.selection.model.vo.common.Result;
import com.yuunik.selection.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Tag(name = "文件上传接口")
@RequestMapping("/admin/system/file")
@RestController
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;

    @Operation(summary = "文件上传")
    @PostMapping("/upload")
    public Result<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String avatarUrl = fileUploadService.uploadFile(file);
        return Result.build(avatarUrl, ResultCodeEnum.SUCCESS);
    }
}
