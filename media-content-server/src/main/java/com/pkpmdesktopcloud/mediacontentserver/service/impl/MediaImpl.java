package com.pkpmdesktopcloud.mediacontentserver.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkpmdesktopcloud.mediacontentserver.dao.PkpmFileConfigDao;
import com.pkpmdesktopcloud.mediacontentserver.dao.PkpmFileInfoDao;
import com.pkpmdesktopcloud.mediacontentserver.service.IMediaService;

@Service
public class MediaImpl implements IMediaService {

	@Resource
	private PkpmFileInfoDao fileInfoDao;

	@Resource
	private PkpmFileConfigDao fileConfigDao;

	/*
	 * (非 Javadoc)
	 * 
	 * 
	 * @param docName
	 * 
	 * @see
	 * com.pkpmdesktopcloud.mediacontentserver.service.IMediaService#convertPdf(java
	 * .lang.String)
	 */
	@Override
	public void convertPdf(String docName) {
		// TODO Auto-generated method stub

	}

	/*
	 * (非 Javadoc)
	 * 
	 * 
	 * @param pdfName
	 * 
	 * @see
	 * com.pkpmdesktopcloud.mediacontentserver.service.IMediaService#captureByPdf(
	 * java.lang.String)
	 */
	@Override
	public void captureByPdf(String pdfName) {
		// TODO Auto-generated method stub

	}

	/*
	 * (非 Javadoc)
	 * 
	 * 
	 * @param videoName
	 * 
	 * @see
	 * com.pkpmdesktopcloud.mediacontentserver.service.IMediaService#captureByVideo(
	 * java.lang.String)
	 */
	@Override
	public void captureByVideo(String videoName) {
		// TODO Auto-generated method stub

	}
}
