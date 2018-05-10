package com.pkpmcloud.fileserver.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.pkpmcloud.fileserver.client.StorageClient;
import com.pkpmcloud.fileserver.client.TrackerClient;
import com.pkpmcloud.fileserver.model.StorageNode;
import com.pkpmcloud.fileserver.model.StorePath;
import com.pkpmcloud.fileserver.protocol.storage.callback.DownloadCallback;
import com.pkpmcloud.fileserver.protocol.storage.callback.DownloadFileWriter;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.desktop.utils.page.ResultObject;

@RestController
@RequestMapping("/fast")
public class FastDFSController {
	  
	 @Resource
	 private TrackerClient trackerClient;
	 @Resource
	 private StorageClient storageClient;
	  
	  @PostMapping("/upload")
	  public ResultObject upload(@RequestParam("file") MultipartFile multipartFile,HttpServletResponse response) {
		  response.setHeader("Access-Control-Allow-Origin", "*");
	       if(multipartFile.isEmpty()){
	    	   return ResultObject.failure("请选择上传文件!");
	       }
	       InputStream inputStream = null;
	       try {
	    	   StorageNode storageNode = trackerClient.getStorageNode();
			   String groupName = storageNode.getGroupName();
			   
			   String fileName=multipartFile.getOriginalFilename();
			   String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
			   
			   inputStream = multipartFile.getInputStream();
			   
			   StorePath storePath = storageClient.uploadAppenderFile(groupName, inputStream,multipartFile.getSize(), ext);
			   System.out.println(storePath);
			   return ResultObject.success(storePath);
			} catch (IOException e) {
				e.printStackTrace();
				
			}finally{
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
	     * @param groupName  组名称
	     * @param path       主文件路径
	     * @param fileOffset 开始位置
	     * @param fileSize   文件大小(经过测试好像这个参数值只能是“0”)
	     * @param callback   下载回调接口
	     * @return 下载回调接口返回结果
	 */
	 @SuppressWarnings("unused")
	 @PostMapping("/download")
	  public ResultObject download(@RequestBody StorePath storePath,HttpServletResponse response){
		  
		  response.setHeader("Access-Control-Allow-Origin", "*");
		  String path = storePath.getPath();
		  String group = storePath.getGroup();
		  if(StringUtils.isEmpty(path)  || StringUtils.isEmpty(group)){
			 return ResultObject.failure("请选择您要下载的文件");
		  }
		  
		 try {
			String fileName = path.substring(path.lastIndexOf("/") + 1, path.length());
			  long fileOffset = 50;
			  long fileSize = 0;
			  DownloadFileWriter downloadFileWriter = new DownloadFileWriter(fileName);
			  String filePath = storageClient.downloadFile(group, path, fileOffset, fileSize, downloadFileWriter);
			  if(StringUtils.isNotBlank(filePath)){
				  return ResultObject.success("下载成功");
			  }
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	      
		  return ResultObject.failure("下载失败,请重新尝试!");
		  
	  }
	  
}
