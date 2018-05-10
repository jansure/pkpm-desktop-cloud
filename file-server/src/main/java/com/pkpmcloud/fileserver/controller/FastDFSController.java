package com.pkpmcloud.fileserver.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.pkpmcloud.fileserver.client.StorageClient;
import com.pkpmcloud.fileserver.client.TrackerClient;
import com.pkpmcloud.fileserver.model.StorageNode;
import com.pkpmcloud.fileserver.model.StorePath;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
			   
			   StorePath storePath = storageClient.uploadFile(groupName, inputStream, multipartFile.getSize(), ext);
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
	  
	  
	  
	  @GetMapping("/")
	  public String test1(){
		  
		  System.out.println();
		  return "test";
	  }
}
