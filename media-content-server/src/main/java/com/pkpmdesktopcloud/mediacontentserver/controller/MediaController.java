package com.pkpmdesktopcloud.mediacontentserver.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.desktop.utils.FileUtil;
import com.desktop.utils.page.ResultObject;
import com.pkpmdesktopcloud.mediacontentserver.domain.PkpmFileInfo;
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
	
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ResultObject download(String path, HttpServletRequest request,
			HttpServletResponse response) {

		if (StringUtils.isEmpty(path)) {
			return ResultObject.failure("请选择您要下载的文件名！");
		}
		
		//获取组Id和真实路径
		int index = path.indexOf("/");
		String groupNameStr = path.substring(5, index);
		String destFileName = path.substring(index + 1);
		
		PkpmFileInfo fileInfo = new PkpmFileInfo();
		fileInfo.setGroupName(Integer.parseInt(groupNameStr));
		fileInfo.setDestFileName(destFileName);
		PkpmFileInfo pkpmFileInfo = fileService.selectFile(fileInfo);
		
		if( null == pkpmFileInfo) {
			return ResultObject.failure("您下载的文件不存在!");
		}
		
		//获取文件的原始名字,下载的时候将 服务器的base64编码后的文件名 替换为 原始名字
		String originFileName = pkpmFileInfo.getOriginFileName();
		
		String fileUrl = url + path;
		try {
			
			FileUtil.downloadFile(fileUrl,originFileName, true, request, response);
			return ResultObject.success("下载成功!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultObject.success("下载失败,请重新尝试!");

	}
	
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

