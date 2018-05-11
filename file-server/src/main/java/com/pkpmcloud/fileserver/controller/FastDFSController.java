package com.pkpmcloud.fileserver.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.desktop.utils.FileUtil;
import com.desktop.utils.Md5CaculateUtil;
import com.desktop.utils.page.ResultObject;
import com.pkpmcloud.fileserver.client.StorageClient;
import com.pkpmcloud.fileserver.client.TrackerClient;
import com.pkpmcloud.fileserver.model.StorageNode;
import com.pkpmcloud.fileserver.model.StorePath;
import com.pkpmcloud.fileserver.service.IFileService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/fast")
@Slf4j
public class FastDFSController {

	@Resource
	private TrackerClient trackerClient;
	
	@Resource
	private StorageClient storageClient;
	
	@Resource
	private IFileService fileService;
	
	@Value("${nginx.url}")
	private String url;

	@PostMapping("/upload")
	public ResultObject upload(@RequestParam("file") MultipartFile multipartFile, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		if (multipartFile.isEmpty()) {
			return ResultObject.failure("请选择上传文件!");
		}
		InputStream inputStream = null;
		try {
			
			//获取文件Md5
			String md5 = Md5CaculateUtil.MD5ByMultipartFile(multipartFile);
			log.debug("md5:" + md5);
			
			StorageNode storageNode = trackerClient.getStorageNode();
			String groupName = storageNode.getGroupName();

			String fileName = multipartFile.getOriginalFilename();
			String ext = fileName.substring(fileName.lastIndexOf(".") + 1);

			inputStream = multipartFile.getInputStream();

			StorePath storePath = storageClient.uploadAppenderFile(groupName, inputStream, multipartFile.getSize(),
					ext);
			System.out.println(storePath);
			return ResultObject.success(storePath);
		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ResultObject.failure("上传文件失败,请重新尝试!");
	}

	/**
	 * 下载文件片段(断点续传)
	 *
	 * @param groupName
	 *            组名称
	 * @param path
	 *            主文件路径
	 * @param fileOffset
	 *            开始位置
	 * @param fileSize
	 *            文件大小(经过测试好像这个参数值只能是“0”)
	 * @param callback
	 *            下载回调接口
	 * @return 下载回调接口返回结果
	 */
	/**
	 * 
	 * @param storePath
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public ResultObject download(StorePath storePath, HttpServletRequest request,
			HttpServletResponse response) {

		response.setHeader("Access-Control-Allow-Origin", "*");
		String path = storePath.getPath();
		String group = storePath.getGroup();
		if (StringUtils.isEmpty(path) || StringUtils.isEmpty(group)) {
			return ResultObject.failure("请选择您要下载的文件名！");
		}
		String fullPath = storePath.getFullPath();
		String fileUrl = url + fullPath;
		try {
			
			FileUtil.downloadFile(fileUrl, false, request, response);
			return ResultObject.success("下载成功!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultObject.success("下载失败,请重新尝试!");

	}

	@GetMapping("/")
	public String test1() {
		return "test1";
	}
}
