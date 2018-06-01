package com.pkpmdesktopcloud.mediacontentserver.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desktop.utils.page.ResultObject;
import com.pkpmdesktopcloud.mediacontentserver.service.IMediaService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/media")
@Slf4j
public class MediaController {

	
	@Resource
	private IMediaService fileService;
	
	@Value("${nginx.url}")
	private String url;
	
	
	
	/**
	 * 获取档
	 * @param multipartFile
	 * @return
	 */
	@PostMapping("/getDocByName")
	public ResultObject getMD5(String fileName) {
		
		return ResultObject.failure("获取失败,请重新获取!");
	}
	
	
	
}

