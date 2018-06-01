package com.pkpmdesktopcloud.desktopcloudbusiness.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.desktop.utils.FileUtil;
import com.gateway.common.dto.FileServerResponse;
import com.pkpm.httpclientutil.common.util.JsonUtil;
import com.pkpmdesktopcloud.desktopcloudbusiness.constants.SysConstant;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.DocumentService;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmSysConfigService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yangpengfei 文档接口实现类
 * @date 2018年6月1日
 */
@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService {

	@Resource
	private PkpmSysConfigService sysConfigService;

	@Override
	public String getTxtContent(String filename) {
		String txtContent = "";
		try {
			filename = URLDecoder.decode(filename, "UTF-8") + ".txt";
			if (existsFile(filename)) {
				// 若存在文件返回文件内容
				String url = sysConfigService.getPkpmSysConfigByKey(SysConstant.FILE_BASE_URL).getValue() + "/" + filename;
				FileUtil.getHttpResponse(url);
				return txtContent;
			}
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		// 不存在文件返回空
		return txtContent;
	}

	@Override
	public Boolean existsFile(String filename) {
		// 文件系统路径
		String fileUrl = sysConfigService.getPkpmSysConfigByKey(SysConstant.FILE_BASE_URL).getValue();
		// 获取所有存在的文件名
		String json = FileUtil.loadJson(fileUrl);
		FileServerResponse fileServerResponse = JsonUtil.deserialize(json, FileServerResponse.class);

		if (fileServerResponse != null && fileServerResponse.getFiles() != null) {
			// 遍历已有的文件列表，看文件名是否存在
			if (fileServerResponse.getFiles().contains(filename)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String downloadFile(String filename, boolean isOnLine, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (existsFile(filename)) {
				// 若存在该文件名，则开启下载；若不存在，抛出异常
				String filePath = sysConfigService.getPkpmSysConfigByKey(SysConstant.FILE_BASE_URL).getValue() + "/" + filename;
				FileUtil.downloadFile(filePath, null, isOnLine, request, response);
				log.debug("FileUtil.downloadFile:" + filePath);
				return "下载成功！";
			} else {
				return "该文件不存在！";
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

}
