package com.sky.config;

import org.springframework.context.annotation.Configuration;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class OssConfiguration {
	public AliOssUtil aliOsUtil(AliOssProperties aliOSProperties) {
	    return new AliOssUtil(aliOSProperties.getEndpoint(),
	    aliOSProperties.getAccessKeyId(),
	    aliOSProperties.getAccessKeySecret(),
	    aliOSProperties.getBucketName());
	}
}
