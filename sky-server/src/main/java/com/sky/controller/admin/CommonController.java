package com.sky.controller.admin;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Api(tags = "通用接口")
@RequestMapping(value="/admin/common")
public class CommonController {

	@Autowired
	private AliOssUtil aliOssUtil;
    @ApiOperation("文件上传接口")
    @PostMapping(value="/upload")
    public Result<String> upload(MultipartFile file) {
    	try {
    	    String originalFilename = file.getOriginalFilename();
    	    String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
    	    String imageName = UUID.randomUUID().toString() + substring;
    	    String upload = aliOssUtil.upload(file.getBytes(), imageName);

    	    return Result.success(upload);
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
    	return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
