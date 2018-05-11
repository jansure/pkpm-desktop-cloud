package com.pkpmcloud.fileserver.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import com.pkpmcloud.fileserver.conn.DefaultCommandExecutor;
import com.pkpmcloud.fileserver.domain.PkpmFileInfo;
import com.pkpmcloud.fileserver.model.FileInfo;
import com.pkpmcloud.fileserver.model.MateData;
import com.pkpmcloud.fileserver.model.StorageNode;
import com.pkpmcloud.fileserver.model.StorePath;
import com.pkpmcloud.fileserver.pool.ConnectionPool;
import com.pkpmcloud.fileserver.pool.PooledConnectionFactory;
import com.pkpmcloud.fileserver.protocol.storage.callback.DownloadFileWriter;

import lombok.extern.slf4j.Slf4j;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

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
    		
    	PkpmFileInfo fileInfo = fileService.selectByMd5("123456");
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
