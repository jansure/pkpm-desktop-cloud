package com.pkpmdesktopcloud.desktopcloudbusiness.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.desktop.utils.page.ResultObject;
import com.github.pagehelper.util.StringUtil;
import com.google.common.base.Preconditions;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.DocumentService;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmSysConfigService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Api(description = "文件管理")
@Slf4j
@RequestMapping(value = "/document")
public class DocumentController {
	@Resource
    private DocumentService documentService;
    
    @Resource
    private PkpmSysConfigService sysConfigService;

    /**
     * 文件下载
     * @param filename
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ApiOperation("文件下载")
    @ResponseBody
	@RequestMapping(value = "/downloads", method = RequestMethod.GET)
    public ResultObject getDownloads(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 允许跨域调用
        response.setHeader("Access-Control-Allow-Origin", "*");
        //filename = "BIM协同设计管理云平台.pdf";
        Preconditions.checkArgument(StringUtil.isNotEmpty(filename), "文件名不能为空");
        
        boolean isOnLine = false;
        log.debug("filename:" + filename + "; isOnLine:" + isOnLine);
        String msg = filename + documentService.downloadFile(filename, isOnLine, request, response);
        return ResultObject.success(msg);
    }
    
    @ApiOperation("文件预览")
    @ResponseBody
	@RequestMapping(value = "/preview", method = RequestMethod.GET)
    public ResultObject getPreview(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 允许跨域调用
        response.setHeader("Access-Control-Allow-Origin", "*");
        //filename = "BIM协同设计管理云平台.pdf";
        Preconditions.checkArgument(StringUtil.isNotEmpty(filename), "文件名不能为空");
        
        boolean isOnLine = true;
        log.debug("filename:" + filename + "; isOnLine:" + isOnLine);
        String msg = filename + documentService.downloadFile(filename, isOnLine, request, response);
        return ResultObject.success(msg);
    }
    
	/**
	 * 获取法律条款说明
	 * 
	 * @param response
	 * @return
	 */
	@ResponseBody
	@ApiOperation("获取法律条款声明内容")
	@RequestMapping(value = "/legalTerms", method = RequestMethod.GET)
	public ResultObject getLegalTerms(HttpServletResponse response) {
		// 允许跨域调用
		response.setHeader("Access-Control-Allow-Origin", "*");
		String filename = "法律条款声明";
		String termsJson = documentService.getTxtContent(filename);
		return ResultObject.success(termsJson, "获取成功");
	}
	
    /**
	 * 获取使用教程说明
	 * @author yangpengfei
	 * @param response
	 * @return
	 */
	@ResponseBody
	@ApiOperation("获取使用教程说明内容")
	@RequestMapping(value = "/manual", method = RequestMethod.GET)
	public ResultObject getManual(HttpServletResponse response) {
		// 允许跨域调用
		response.setHeader("Access-Control-Allow-Origin", "*");
		String filename = "使用教程说明";
		String termsJson = documentService.getTxtContent(filename);
		return ResultObject.success(termsJson, "获取成功");
	}
	
	/**
	 * 获取自助服务相关文档内容
	 * @author yangpengfei
	 * @param filename
	 * @return
	 */
	@ResponseBody
	@ApiOperation("获取自助服务相关文档内容")
	@RequestMapping(value = "/help", method = RequestMethod.GET)
	public ResultObject getHelp(String filename, HttpServletResponse response) {
		// 允许跨域调用
		response.setHeader("Access-Control-Allow-Origin", "*");
		String termsJson = documentService.getTxtContent(filename);
		return ResultObject.success(termsJson, "获取成功");
	}

}
