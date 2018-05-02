package com.pkpmdesktopcloud.desktopcloudbusiness.controller;


import com.desktop.utils.FileUtil;
import com.desktop.utils.page.ResultObject;
import com.google.common.base.Preconditions;
import com.pkpm.httpclientutil.common.util.JsonUtil;
import com.pkpmdesktopcloud.desktopcloudbusiness.constants.SysConstant;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.SysConfig;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.FileServerResponse;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.SysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequestMapping(value = "/document")
public class DocumentController {
    @Resource
    private SysConfigService sysConfigService;

    public ResultObject getHelp(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 允许跨域调用
        response.setHeader("Access-Control-Allow-Origin", "*");
        boolean isOnLine = false;
        //filename = "BIM协同设计管理云平台.pdf";

        Preconditions.checkArgument(StringUtils.isBlank(filename), "文件名不能为空");

        log.debug("filename:" + filename + "; isOnLine:" + isOnLine);

        // 文件系统路径
        String fileUrl = sysConfigService.getSysConfigByKey(SysConstant.FILE_BASE_URL).getValue();

        String json = FileUtil.loadJson(fileUrl);
        // System.out.println(json); //检测是否正确获得

        FileServerResponse fileServerResponse = JsonUtil.deserialize(json, FileServerResponse.class);
        if (fileServerResponse != null && fileServerResponse.getFiles() != null) {
            // 遍历已有的文件列表，看文件名是否存在
            try {
                if (fileServerResponse.getFiles().contains(filename)) {
                    // 若存在该文件名，则开启下载；若不存在，抛出异常
                    String filePath = fileUrl + "/" + filename;
                    FileUtil.downloadFile(filePath, isOnLine, request, response);
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

}
