package com.pkpmdesktopcloud.mediacontentserver.service;

public interface IMediaService {
	
	/**
	 * 
	 */
	void convertPdf(String docName);
    
	/**
	 * 
	 */
    void captureByPdf(String pdfName);

    /**
	 * 
	 */
    void captureByVideo(String videoName);
}
