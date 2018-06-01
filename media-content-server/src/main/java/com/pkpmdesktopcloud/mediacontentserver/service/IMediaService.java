package com.pkpmdesktopcloud.mediacontentserver.service;

import com.pkpmdesktopcloud.mediacontentserver.domain.PkpmFileInfo;

public interface IMediaService {
	
	/**
	 * 转换pdf文件
	 */
	void convertPdf();
    
	/**
	 * 转换pdf缩略图
	 */
    void captureByPdf();

    /**
	 * 转换视频缩略图
	 */
    void captureByVideo();

	  
	/**  
	 * @Title: selectFile  
	 * @Description: TODO(这里用一句话描述这个方法的作用)  
	 * @param @param fileInfo
	 * @param @return    参数  
	 * @return PkpmFileInfo    返回类型  
	 * @throws  
	 */  
	PkpmFileInfo selectFile(PkpmFileInfo fileInfo);
}
