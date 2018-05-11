package com.pkpmcloud.fileserver.service;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pkpmcloud.fileserver.domain.PkpmFileInfo;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class FileServiceTest {

    @Resource
	private IFileService fileService;
    
    
    @Test
    public void insert() throws IOException {
    		
    	PkpmFileInfo fileInfo = new PkpmFileInfo();
    	fileInfo.setCreateTime(LocalDateTime.now());
    	fileInfo.setFileSize("100MB");
    	fileInfo.setGroupName(1);
    	fileInfo.setMd5("123456");
    	fileInfo.setOriginFileName("test.mp4");
    	fileInfo.setDestFileName("jklfdsjlgjdslfds.mp4");
    	fileInfo.setPostfix("mp4");
		int num = fileService.insert(fileInfo );
		Assert.assertTrue(num == 1);
    }
    
    @Test
    public void select() throws IOException {
    		
    	PkpmFileInfo fileInfo = fileService.selectByMd5("e40339c5249a024fd7811c5c7909801c");
		log.info("fileInfo:" + fileInfo);
    }
    
    @Test
    public void update() throws IOException {
    		
    	PkpmFileInfo fileInfo = new PkpmFileInfo();
    	fileInfo.setMd5("123456");
    	fileInfo.setFileSize("200MB");
    	int num = fileService.update(fileInfo);
    	Assert.assertTrue(num == 1);
    }

}
