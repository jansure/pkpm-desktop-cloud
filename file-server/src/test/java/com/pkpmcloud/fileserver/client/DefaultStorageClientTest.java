package com.pkpmcloud.fileserver.client;

import com.pkpmcloud.fileserver.conn.DefaultCommandExecutor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import com.pkpmcloud.fileserver.conn.DefaultCommandExecutor;
import com.pkpmcloud.fileserver.model.FileInfo;
import com.pkpmcloud.fileserver.model.MateData;
import com.pkpmcloud.fileserver.model.StorePath;
import com.pkpmcloud.fileserver.pool.ConnectionPool;
import com.pkpmcloud.fileserver.pool.PooledConnectionFactory;
import com.pkpmcloud.fileserver.protocol.storage.callback.DownloadFileWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/21 19:22 <br/>
 */
public class DefaultStorageClientTest {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(DefaultTrackerClientTest.class);

    private static ConnectionPool connectionPool;
    private static StorageClient storageClient;

    @Before
    public void init() {
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(500, 500);
        GenericKeyedObjectPoolConfig conf = new GenericKeyedObjectPoolConfig();
        conf.setMaxTotal(200);
        conf.setMaxTotalPerKey(200);
        conf.setMaxIdlePerKey(100);
        connectionPool = new ConnectionPool(pooledConnectionFactory, conf);
        Set<String> trackerSet = new HashSet<String>();
        trackerSet.add("139.159.254.232:22122");
        trackerSet.add("139.159.254.106:22122");
        DefaultCommandExecutor commandExecutor = new DefaultCommandExecutor(trackerSet, connectionPool);
        TrackerClient trackerClient = new DefaultTrackerClient(commandExecutor);
        storageClient = new DefaultStorageClient(commandExecutor, trackerClient);
    }

    @After
    public void close() {
        connectionPool.close();
    }

    @Test
    public void uploadFileTest() throws IOException {
        File file = new File("F:\\123456.txt");
        FileInputStream fileInputStream = FileUtils.openInputStream(file);
        StorePath storePath = storageClient.uploadFile("group1", fileInputStream, file.length(), "txt");
        fileInputStream.close();
        logger.info("#####===== " + storePath);
    }

    @Test
    public void uploadSlaveFileTest() throws IOException {
        File file = new File("F:\\数据提取-乐山市.xlsx");
        FileInputStream fileInputStream = FileUtils.openInputStream(file);
        StorePath storePath = storageClient.uploadSlaveFile("group1", "M00/00/00/wKgKgFgzZxuATInzAACM0xlEIJM55.xlsx", fileInputStream, file.length(), "-1", "xlsx");
        fileInputStream.close();
        logger.info("#####===== " + storePath);
    }

    @Test
    public void mergeMetadataTest() {
        Set<MateData> mateDataSet = new HashSet<MateData>();
        mateDataSet.add(new MateData("width", "100"));
        mateDataSet.add(new MateData("long", "200"));
        boolean flag = storageClient.mergeMetadata("group1", "wKgAslr0Ms-EenO7AAAAAN_P6X4238.mkv", mateDataSet);
        System.out.println(flag);
        logger.info("#####===== " + flag);
    }

    @Test
    public void getMetadataTest() {
        Set<MateData> mateDataSet = storageClient.getMetadata("group1", "M00/00/00/wKgKgFgzZxuATInzAACM0xlEIJM55.mkv");
        for (MateData mateData : mateDataSet) {
            logger.info("#####===== " + mateData);
        }
    }

    @Test
    public void overwriteMetadataTest() {
        Set<MateData> mateDataSet = new HashSet<MateData>();
        mateDataSet.add(new MateData("key5", "value5"));
        mateDataSet.add(new MateData("key6", "value6"));
        mateDataSet.add(new MateData("key7", "value7"));
        boolean flag = storageClient.overwriteMetadata("group1", "M00/00/00/wKgKgFgzZxuATInzAACM0xlEIJM55.xlsx", mateDataSet);
        logger.info("#####===== " + flag);
    }

    @Test
    public void queryFileInfoTest() {
        FileInfo fileInfo = storageClient.queryFileInfo("group2", "M00/00/00/wKgA3lrz4xmEMEJVAAAAAJIl9Sw87.java");
        logger.info("#####===== " + fileInfo);
    }

    @Test
    public void deleteFileTest() {
        boolean flag = storageClient.deleteFile("group1", "M00/00/00/wKgKgFgzZxuATInzAACM0xlEIJM55-1.xlsx");
        logger.info("#####===== " + flag);
    }

    @Test
    public void downloadFileTest() {
        DownloadFileWriter downloadFileWriter = new DownloadFileWriter("G:\\1234.xlsx");
        String filePath = storageClient.downloadFile("group2", "M00/00/00/wKgA3lrzslKAZhcGAAAbu6bFmJg15.conf", downloadFileWriter);
        logger.info("#####===== " + filePath);
        System.out.println(filePath);
    }

    @Test
    public void downloadFileTest2() {
        long fileOffset = 50;
        long fileSize = 0;
        DownloadFileWriter downloadFileWriter = new DownloadFileWriter("G:\\dddd.conf");
        String filePath = storageClient.downloadFile("group2", "M00/00/00/wKgA3lrzslKAZhcGAAAbu6bFmJg15.conf", fileOffset, fileSize, downloadFileWriter);
        logger.info("#####===== " + filePath);
    }

    @Test
    public void Test() {
//        boolean flag = storageClient. ;
//        logger.info("#####===== " + flag);
    	String url = "M00/00/00/wKgA3lrzslKAZhcGAAAbu6bFmJg15.conf";
    	String str = url.substring(url.lastIndexOf("/") + 1, url.length());
    	System.out.println(str);
    }
    
    

}
