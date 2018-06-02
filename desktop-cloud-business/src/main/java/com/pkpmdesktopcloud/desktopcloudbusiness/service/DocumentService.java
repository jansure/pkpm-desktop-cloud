package com.pkpmdesktopcloud.desktopcloudbusiness.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文档接口类
 * 
 * @author yangpengfei
 * @date 2018年6月1日
 */
public interface DocumentService {
	/**
	 * 获取txt文档内容
	 * 
	 * @param filename
	 * @date 2018年6月1日
	 * @return
	 */
	String getTxtContent(String filename);

	/**
	 * 判断服务器中是否存在该文件
	 * 
	 * @param filename
	 * @return
	 */
	Boolean existsFile(String filename);

	/**
	 * 下载文件
	 * @param filename
	 * @param isOnLine
	 * @param request
	 * @param response
	 * @return
	 */
	String downloadFile(String filename, boolean isOnLine, HttpServletRequest request, HttpServletResponse response);
}
