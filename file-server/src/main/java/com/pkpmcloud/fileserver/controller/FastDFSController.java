package com.pkpmcloud.fileserver.controller;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

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
import com.desktop.utils.StringUtil;
import com.desktop.utils.page.ResultObject;
import com.pkpmcloud.fileserver.client.StorageClient;
import com.pkpmcloud.fileserver.client.TrackerClient;
import com.pkpmcloud.fileserver.domain.PkpmFileInfo;
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
		
		boolean isUpdate = false;
		StorePath storePath = null;
		InputStream inputStream = null;
		String groupName = null;
		
		try {
			
			//获取文件Md5
			String md5 = Md5CaculateUtil.MD5ByMultipartFile(multipartFile);
			PkpmFileInfo fileInfo = fileService.selectByMd5(md5);
			
			//文件存在
			if(fileInfo != null) {
				
				//上传完成，不需要上传
				if(StringUtils.isNotEmpty(fileInfo.getDestFileName())) {
					storePath = new StorePath();
					storePath.setGroup("group" + fileInfo.getGroupName()) ;
					storePath.setPath(fileInfo.getDestFileName());
					
					return ResultObject.success(storePath);
				}
				
				//保证本次上传的组名与上次一样
				groupName = "group" + fileInfo.getGroupName();
				isUpdate = true;
			}else {
				
				//重新获取组名
				StorageNode storageNode = trackerClient.getStorageNode();
				groupName = storageNode.getGroupName();
			}
			
			fileInfo = fileService.getPkpmFileInfo(multipartFile);

			//插入
			if(!isUpdate) {
				
				//获取组名中的数字
				List<Integer> numList = StringUtil.getIntegerByStr(groupName);
				
		    	fileInfo.setGroupName(numList.get(0));
		    	fileInfo.setMd5(md5);
		    	
				int num = fileService.insert(fileInfo );
			}
			
			//上传文件
			storePath = storageClient.uploadAppenderFile(groupName, multipartFile.getInputStream(), 
								multipartFile.getSize(), fileInfo.getPostfix());
			
			//上传成功，更新目标文件名
			fileInfo = new PkpmFileInfo();
			fileInfo.setMd5(md5);
			fileInfo.setDestFileName(storePath.getPath());
			fileService.update(fileInfo);
			
			return ResultObject.success(storePath);
		} catch (IOException e) {
			e.printStackTrace();

		} finally {
//			try {
//				inputStream.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
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
