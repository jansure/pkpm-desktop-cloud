package com.pkpmdesktopcloud.desktopcloudbusiness.controller;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.desktop.utils.FileUtil;
import com.desktop.utils.page.ResultObject;
import com.gateway.common.dto.FileServerResponse;
import com.github.pagehelper.util.StringUtil;
import com.google.common.base.Preconditions;
import com.pkpm.httpclientutil.common.util.JsonUtil;
import com.pkpmdesktopcloud.desktopcloudbusiness.constants.SysConstant;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmSysConfigService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Api(description = "文件管理")
@Slf4j
@RequestMapping(value = "/document")
public class DocumentController {
    @Resource
    private PkpmSysConfigService sysConfigService;

    @ApiOperation("文件下载")
    @ResponseBody
	@RequestMapping(value = "/downloads", method = RequestMethod.GET)
    public ResultObject getDownloads(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 允许跨域调用
        response.setHeader("Access-Control-Allow-Origin", "*");
        boolean isOnLine = false;
        //filename = "BIM协同设计管理云平台.pdf";

        Preconditions.checkArgument(StringUtil.isNotEmpty(filename), "文件名不能为空");

        log.debug("filename:" + filename + "; isOnLine:" + isOnLine);

        // 文件系统路径
        String fileUrl = sysConfigService.getPkpmSysConfigByKey(SysConstant.FILE_BASE_URL).getValue();

        String json = FileUtil.loadJson(fileUrl);
        // System.out.println(json); //检测是否正确获得

        FileServerResponse fileServerResponse = JsonUtil.deserialize(json, FileServerResponse.class);
        if (fileServerResponse != null && fileServerResponse.getFiles() != null) {
            // 遍历已有的文件列表，看文件名是否存在
            try {
                if (fileServerResponse.getFiles().contains(filename)) {
                    // 若存在该文件名，则开启下载；若不存在，抛出异常
                    String filePath = fileUrl + "/" + filename;
                    FileUtil.downloadFile(filePath,null,isOnLine, request, response);
                    log.debug("FileUtil.downloadFile:" + filePath);
                    return ResultObject.success(filePath, "下载成功！");
                }

                return ResultObject.failure("该文件不存在！");

            } catch (Exception e) {

                log.error(e.getMessage());

            }
        }

        String msg = "<" + filename + ">下载失败！";
        return ResultObject.failure(msg);
    }
    
	/**
	 * 获取法律条款说明
	 * 
	 * @param response
	 * @param response
	 * @return
	 */
	@ResponseBody
	@ApiOperation("法律条款下载")
	@RequestMapping(value = "/legalTerms", method = RequestMethod.GET)
	public ResultObject getLegalTerms(HttpServletResponse response) {
		// 允许跨域调用
		response.setHeader("Access-Control-Allow-Origin", "*");
		String filename = "法律条款声明.txt";
//		Preconditions.checkArgument(StringUtils.isNotEmpty(filename), "文件名不能为空");
		
		String url = sysConfigService.getPkpmSysConfigByKey(SysConstant.FILE_BASE_URL).getValue() + "/" + filename;
		String termsJson = FileUtil.getHttpResponse(url);
		if (!StringUtils.isEmpty(termsJson)) {
			return ResultObject.success(termsJson, "获取成功");
		} 
		
		return ResultObject.failure("获取失败");
	}
	
    /**
	 * 获取使用教程说明
	 * @author yangpengfei
	 * @param response
	 * @return
	 */
	@ResponseBody
	@ApiOperation("教程下载")
	@RequestMapping(value = "/manual", method = RequestMethod.GET)
	public ResultObject getManual(HttpServletResponse response) {
		// 允许跨域调用
		response.setHeader("Access-Control-Allow-Origin", "*");
		String filename = "使用教程说明.txt";
		String url = sysConfigService.getPkpmSysConfigByKey(SysConstant.FILE_BASE_URL).getValue() + "/" + filename;
		String termsJson = FileUtil.getHttpResponse(url);
		if (!StringUtils.isEmpty(termsJson)) {
			return ResultObject.success(termsJson, "获取成功");
		}
		
		return ResultObject.failure("获取失败");
	}
	
	/**
	 * 获取自助服务相关文档内容
	 * @author yangpengfei
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody
	@ApiOperation("自助服务相关文档")
	@RequestMapping(value = "/help", method = RequestMethod.GET)
	public ResultObject getHelp(String filename, HttpServletResponse response) throws UnsupportedEncodingException {
		// 允许跨域调用
		response.setHeader("Access-Control-Allow-Origin", "*");
		filename =  URLDecoder.decode(filename,"UTF-8") + ".txt";
		String url = sysConfigService.getPkpmSysConfigByKey(SysConstant.FILE_BASE_URL).getValue() + "/" + filename;
		String termsJson = FileUtil.getHttpResponse(url);
		if (!StringUtils.isEmpty(termsJson)) {
			return ResultObject.success(termsJson, "获取成功");
		}
		
		return ResultObject.failure("获取失败");
	}

}
