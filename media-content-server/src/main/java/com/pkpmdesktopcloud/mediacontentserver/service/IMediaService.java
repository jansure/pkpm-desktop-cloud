package com.pkpmdesktopcloud.mediacontentserver.service;

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
}
