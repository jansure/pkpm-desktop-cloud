package org.cleverframe.fastdfs.client;

import org.apache.commons.io.FileUtils;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.cleverframe.fastdfs.conn.DefaultCommandExecutor;
import org.cleverframe.fastdfs.model.FileInfo;
import org.cleverframe.fastdfs.model.MateData;
import org.cleverframe.fastdfs.model.StorageNode;
import org.cleverframe.fastdfs.model.StorePath;
import org.cleverframe.fastdfs.pool.ConnectionPool;
import org.cleverframe.fastdfs.pool.PooledConnectionFactory;
import org.cleverframe.fastdfs.protocol.storage.callback.DownloadFileWriter;
import org.junit.After;
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
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MyStorageClientTest {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(DefaultTrackerClientTest.class);

    @Resource
	private TrackerClient trackerClient;
    
    @Resource
    private StorageClient storageClient;

//    @Test
//    public void uploadFileTest() throws IOException {
//    	StorageNode storageNode = trackerClient.getStorageNode();
//    	logger.info("#===== " + storageNode);
//    	
//        File file = new File("G:\\centos7_1.vdi");
//        FileInputStream fileInputStream = FileUtils.openInputStream(file);
//        StorePath storePath = storageClient.uploadFile(storageNode.getGroupName(), fileInputStream, file.length(), "txt");
//        fileInputStream.close();
//        logger.info("#####===== " + storePath);
//    }
    
    @Test
    public void uploadAppenderFileTest() throws IOException {
    	while(true) {
    		
    		File file = new File("G:\\\\centos7_1.vdi");
    		FileInputStream fileInputStream = FileUtils.openInputStream(file);
    		StorePath storePath = storageClient.uploadAppenderFile("group1", fileInputStream, file.length(), "mkv");
    		fileInputStream.close();
    		logger.info("#####===== " + storePath);
    	}
    }

//    @Test
//    public void uploadSlaveFileTest() throws IOException {
//        File file = new File("F:\\数据提取-乐山市.xlsx");
//        FileInputStream fileInputStream = FileUtils.openInputStream(file);
//        StorePath storePath = storageClient.uploadSlaveFile("group1", "M00/00/00/wKgKgFgzZxuATInzAACM0xlEIJM55.xlsx", fileInputStream, file.length(), "-1", "xlsx");
//        fileInputStream.close();
//        logger.info("#####===== " + storePath);
//    }
//
//    @Test
//    public void mergeMetadataTest() {
//        Set<MateData> mateDataSet = new HashSet<MateData>();
//        mateDataSet.add(new MateData("key1", "value1"));
//        mateDataSet.add(new MateData("key2", "value2"));
//        mateDataSet.add(new MateData("key3", "value3"));
//        boolean flag = storageClient.mergeMetadata("group1", "M00/00/00/wKgKgFgzZxuATInzAACM0xlEIJM55.xlsx", mateDataSet);
//        logger.info("#####===== " + flag);
//    }
//
//    @Test
//    public void getMetadataTest() {
//        Set<MateData> mateDataSet = storageClient.getMetadata("group1", "M00/00/00/wKgKgFgzZxuATInzAACM0xlEIJM55.xlsx");
//        for (MateData mateData : mateDataSet) {
//            logger.info("#####===== " + mateData);
//        }
//    }
//
//    @Test
//    public void overwriteMetadataTest() {
//        Set<MateData> mateDataSet = new HashSet<MateData>();
//        mateDataSet.add(new MateData("key5", "value5"));
//        mateDataSet.add(new MateData("key6", "value6"));
//        mateDataSet.add(new MateData("key7", "value7"));
//        boolean flag = storageClient.overwriteMetadata("group1", "M00/00/00/wKgKgFgzZxuATInzAACM0xlEIJM55.xlsx", mateDataSet);
//        logger.info("#####===== " + flag);
//    }
//
//    @Test
//    public void queryFileInfoTest() {
//        FileInfo fileInfo = storageClient.queryFileInfo("group1", "M00/00/00/wKg4i1gxzYWAJiwjAATA4WNjQT42751.jpg");
//        logger.info("#####===== " + fileInfo);
//    }
//
//    @Test
//    public void deleteFileTest() {
//        boolean flag = storageClient.deleteFile("group1", "M00/00/00/wKgKgFgzZxuATInzAACM0xlEIJM55-1.xlsx");
//        logger.info("#####===== " + flag);
//    }
//
//    @Test
//    public void downloadFileTest() {
//        DownloadFileWriter downloadFileWriter = new DownloadFileWriter("F:\\123.xlsx");
//        String filePath = storageClient.downloadFile("group1", "M00/00/00/wKgKgFg02TaAY3mTADCUhuWQdRc53.xlsx", downloadFileWriter);
//        logger.info("#####===== " + filePath);
//    }
//
//    @Test
//    public void downloadFileTest2() {
//        long fileOffset = 50;
//        long fileSize = 0;
//        DownloadFileWriter downloadFileWriter = new DownloadFileWriter("F:\\QAZXSW.txt");
//        String filePath = storageClient.downloadFile("group1", "M00/00/00/wKgKgFgzb3iAErG9AAAAbjxjgS8801.txt", fileOffset, fileSize, downloadFileWriter);
//        logger.info("#####===== " + filePath);
//    }
//
//    @Test
//    public void Test() {
////        boolean flag = storageClient. ;
////        logger.info("#####===== " + flag);
//    }

}
