package com.cabr.pkpm.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cabr.pkpm.entity.PkpmCloudVideoCapture;
import com.cabr.pkpm.service.IVideoCaptureService;
import com.cabr.pkpm.utils.ResponseResult;

/**
 * 
 * @ClassName: VideoCaptureController  
 * @Description: 视频截图Controller
 * @author wangxiulong  
 * @date 2018年5月7日  
 *
 */
@RestController
@RequestMapping("/videoCapture")
public class VideoCaptureController {
	
	@Resource
	private IVideoCaptureService videoCaptureService;
	
	protected ResponseResult result = new ResponseResult();
	
	//获取所有视频截图
	@PostMapping("/getAll")
	public ResponseResult desktopStatus(){
		
		List<PkpmCloudVideoCapture> list = videoCaptureService.selectAll();
		if(CollectionUtils.isEmpty(list)) {
			result.set("查询视频截图失败,请重新尝试!", 0);
			return result;
		}
		
		result.set("查询视频截图成功!",1 , list);
		return result;
		
	}

	//根据主键获取单个视频截图
	@RequestMapping(value = "/getByCapId", method = RequestMethod.GET)
	public void operateStatus(Integer capId, HttpServletResponse response){
		
		PkpmCloudVideoCapture videoCapture = videoCaptureService.selectByPrimaryKey(capId);
		if(videoCapture == null) {
			return;
		}
		
		try {
			BufferedImage img = ImageIO.read(new ByteArrayInputStream(videoCapture.getCapValue()));  
			ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();  
	        ImageIO.write(img, videoCapture.getCapType(), outPutStream); 
			
	        response.setContentType("image/" + videoCapture.getCapType());  
	        response.setCharacterEncoding("UTF-8");  
	        OutputStream outputSream = response.getOutputStream();  
	        InputStream in = new ByteArrayInputStream(outPutStream.toByteArray());
	        
	        int len = 0; 
	        byte[] buf = new byte[1024];  
	        while ((len = in.read(buf, 0, 1024)) != -1) {  
	            outputSream.write(buf, 0, len);
	        }
	        
	        outputSream.close();
	        in.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
}
