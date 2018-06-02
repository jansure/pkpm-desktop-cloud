package com.pkpmdesktopcloud.mediacontentserver.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MediaServiceTests {
	
	@Resource
	private IMediaService mediaService;

	@Test
	public void convertPdfTest() {
		mediaService.convertPdf();
	}

}
