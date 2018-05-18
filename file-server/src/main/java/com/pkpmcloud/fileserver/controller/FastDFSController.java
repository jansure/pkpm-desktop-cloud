package com.pkpmcloud.fileserver.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

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
import com.desktop.utils.Md5CalculateUtil;
import com.desktop.utils.StringUtil;
import com.desktop.utils.page.Page;
import com.desktop.utils.page.PageUtils;
import com.desktop.utils.page.ResultObject;
import com.pkpmcloud.fileserver.VO.PkpmFileInfoVO;
import com.pkpmcloud.fileserver.client.StorageClient;
import com.pkpmcloud.fileserver.client.TrackerClient;
import com.pkpmcloud.fileserver.constant.OtherConstants;
import com.pkpmcloud.fileserver.domain.PkpmFileInfo;
import com.pkpmcloud.fileserver.model.GroupState;
import com.pkpmcloud.fileserver.model.StorageNode;
import com.pkpmcloud.fileserver.model.StorePath;
import com.pkpmcloud.fileserver.service.IFileService;
import com.pkpmcloud.fileserver.utils.BytesUtil;
import com.pkpmdesktopcloud.redis.RedisCache;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/fast")
@Slf4j
@Api("FastDFS文件接口")
public class FastDFSController {

	@Resource
	private TrackerClient trackerClient;
	
	@Resource
	private StorageClient storageClient;
	
	@Resource
	private IFileService fileService;
	
	@Value("${nginx.url}")
	private String url;
	
	
	
	/**
	 * 获取文件的MD5值
	 * @param multipartFile
	 * @return
	 */
	@ApiOperation(value = "获取MD5")
	@PostMapping("/getMD5")
	public ResultObject getMD5(@RequestParam("file")  @ApiParam(value = "文件,小于1024M") MultipartFile multipartFile) {
		
		if (multipartFile.isEmpty()) {
			return ResultObject.failure("请选择上传文件!");
		}
		
		try {
			
			//获取文件Md5
			String md5 = Md5CalculateUtil.MD5ByMultipartFile(multipartFile);
			BytesUtil.threadLocalMd5.set(md5);
			return ResultObject.success(md5, "获取MD5成功");
			
		} catch (IOException e) {
			e.printStackTrace();

		} 
		return ResultObject.failure("获取MD5失败,请重新获取!");
	}
	
	
	/**
	 * 文件上传
	 * 如果有传过来MD5,直接上传  ;如果没有MD5重新生成
	 * @param multipartFile
	 * @param response
	 * @return
	 */
	
	@ApiOperation(value = "文件上传")
	@PostMapping("/upload")
	public ResultObject upload(@RequestParam("file")  @ApiParam(value = "文件,小于1024M") MultipartFile multipartFile ,
			                   @RequestParam(value="md5", required =false) @ApiParam(value = "md5值") String md5) {
		
		if (multipartFile.isEmpty()) {
			return ResultObject.failure("请上传文件!");
		}
		
		boolean isUpdate = false;
		StorePath storePath = null;
		String groupName = null;
		
		try {
			
			//判断md5是否传过来
			if( StringUtils.isEmpty(md5)) {			
				md5 = Md5CalculateUtil.MD5ByMultipartFile(multipartFile);			
			}
			BytesUtil.threadLocalMd5.set(md5);
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
			
		} 
		
		return ResultObject.failure("上传文件失败,请重新尝试!");
	}
	
	
	/**
	 * 获取文件上传进度
	 * @return
	 */
	@ApiOperation(value = "上传进度")
	@GetMapping("/process")
	public ResultObject getProcess(@RequestParam("md5") @ApiParam(value = "md5值",required =true) String md5) {
		
		if(StringUtils.isEmpty(md5)) {
			return ResultObject.failure("请输入正确的md5值!");
		}
		
		//从Redis缓存中获取
		RedisCache cache = new RedisCache(OtherConstants.FILE_UPLOAD_PERCENT_REDIS_KEY);
		Integer percent = (Integer)cache.getObject(md5);
		percent = percent == null ? 0 : percent;
		
		return ResultObject.success(percent);
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
	@ApiOperation(value = "文件下载")
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public ResultObject download(StorePath storePath, HttpServletRequest request,
			HttpServletResponse response) {

		String path = storePath.getPath();
		String group = storePath.getGroup();
		
		if (StringUtils.isEmpty(path) || StringUtils.isEmpty(group)) {
			return ResultObject.failure("请选择您要下载的文件名！");
		}
		
		//数据库存的组名是 Integer类型，前端传过来的是group1 字符串类型的,需要截取一下
		String str = StringUtils.substring(group, 5, group.length());
		if(StringUtils.isEmpty(str)) {
			return ResultObject.failure("请重新输入文件所属的组名!");
		}
		PkpmFileInfo fileInfo = new PkpmFileInfo();
		fileInfo.setGroupName(Integer.parseInt(str));
		fileInfo.setDestFileName(path);
		PkpmFileInfo pkpmFileInfo = fileService.selectFile(fileInfo);
		
		if( null == pkpmFileInfo) {
			return ResultObject.failure("您下载的文件不存在!");
		}
		//获取文件的原始名字,下载的时候将 服务器的base64编码后的文件名 替换为 原始名字
		String originFileName = pkpmFileInfo.getOriginFileName();
		
		
		String fullPath = storePath.getFullPath();
		String fileUrl = url + fullPath;
		try {
			
			FileUtil.downloadFile(fileUrl,originFileName, false, request, response);
			return ResultObject.success("下载成功!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultObject.success("下载失败,请重新尝试!");

	}
    
	
	/**
	 * 1、传文件名根据文件名查询
	 * 2、不传入文件名,按照上传时间倒序查询
	 * @param fileName
	 * @return
	 */
	@ApiOperation(value = "文件列表")
	@GetMapping("/fileList")
	//public ResultObject fileList(String fileName  ,HttpServletResponse response){
	public ResultObject fileList(@RequestParam(value ="fileName",required =false) @ApiParam(value = "文件名")   String fileName,
			@RequestParam(value ="pageNo",required = true) @ApiParam(value = "页码") Integer pageNo,
			@RequestParam(value ="pageSize",required = true) @ApiParam(value = "每面条数") Integer pageSize,HttpServletResponse response){
		
		//获取记录起始下标
		Integer beginPos = PageUtils.getBeginPos(pageNo, pageSize);
		
		Page<PkpmFileInfoVO>  page = fileService.filePageListByName(fileName, beginPos, pageSize);
		return ResultObject.success(page);
	}
	
	@ApiOperation(value = "获取组")
	@GetMapping("/getGroupStates")
	public ResultObject getGroupStates(HttpServletResponse response) {
		
		List<GroupState> list = trackerClient.getGroupStates();
        if(null != list) {
        	return ResultObject.success(list,"查询组列表成功!");
        }
        return ResultObject.success(list,"查询组列表失败!");
	}
}

