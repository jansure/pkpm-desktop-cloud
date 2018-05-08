package com.pkpm.fastdfs.fastdfs;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.pkpm.fastdfs.entity.FastDFSFile;
import com.pkpm.fastdfs.util.SplitUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * Created by Chenjing on 2018/1/18.
 *
 * @author Chenjing
 */
public class FastDFSClient {
    private static Logger logger = LoggerFactory.getLogger(FastDFSClient.class);
    private static TrackerClient trackerClient;

    static {
        try {
            Properties properties = new Properties();
            properties.load(FastDFSClient.class.getClassLoader().getResourceAsStream("fast-dfs.properties"));
            ClientGlobal.initByProperties(properties);
            trackerClient = new TrackerClient();
        } catch (Exception e) {
            logger.error("FastDFS 客户端初始化失败，信息{}", e.toString());
        }
    }

    /**
     * 保存文件
     *
     * @param multipartFile 文件
     * @return 文件路径 组名+服务器路径
     * @throws Exception
     */
    public static String saveFile(MultipartFile multipartFile) throws Exception {
        String[] fileAbsolutePath;
        String fileName = multipartFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        byte[] fileBuff = null;
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
            if (inputStream != null) {
                int len1 = inputStream.available();
                fileBuff = new byte[len1];
                inputStream.read(fileBuff);
            }
        } catch (Exception e) {
            logger.error("保存文件出现了异常，信息{}", e.toString());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        FastDFSFile file = new FastDFSFile(fileName, fileBuff, ext, "Chenjing");
        fileAbsolutePath = FastDFSClient.upload(file);
        return fileAbsolutePath[0] + "/" + fileAbsolutePath[1];
    }

    /**
     * 上传文件
     *
     * @param file 文件实体类
     * @return groupName 和 serverName的数组
     * @throws IOException io异常
     * @throws MyException dfs框架作者定义的异常
     * @see FastDFSFile
     */
    private static String[] upload(FastDFSFile file) throws IOException, MyException {
        TrackerServer trackerServer = null;
        StorageServer storageServer = null;
        String[] uploadResults;
        try {
            logger.info("上传文件名: " + file.getName() + "，文件长度:" + file.getContent().length);
            NameValuePair[] metaList = new NameValuePair[1];
            metaList[0] = new NameValuePair("author", file.getAuthor());
            trackerServer = trackerClient.getConnection();
            storageServer = trackerClient.getStoreStorage(trackerServer);
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), metaList);
            String groupName = uploadResults[0];
            String remoteFileName = uploadResults[1];
            logger.info("上传文件成功!" + "group_name:" + groupName + ", remoteFileName:" + " " + remoteFileName);
        } finally {
            if (trackerServer != null) {
                logger.debug("关闭trackerServer连接");
                trackerServer.close();
            }
            if (storageServer != null) {
                logger.debug("关闭storageServer连接");
                storageServer.close();
            }
        }
        return uploadResults;
    }

    /**
     * 下载文件
     *
     * @param path 保存文件返回的路径
     * @return byte[]
     * @throws Exception io异常 数组异常
     */
    private static byte[] downFile(String path) throws Exception {
        logger.info("下载文件路径:{}", path);
        List<String> list = SplitUtil.split("/", path);
        if (list.isEmpty()) {
            return null;
        }
        String groupName = list.get(0);
        StringBuilder remoteFileName = new StringBuilder("");
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                continue;
            }
            remoteFileName.append(list.get(i)).append("/");
        }
        remoteFileName.deleteCharAt(remoteFileName.length() - 1);
        TrackerServer trackerServer = null;
        StorageServer storageServer = null;
        StorageClient storageClient;
        byte[] result;
        try {
            trackerServer = trackerClient.getConnection();
            storageServer = trackerClient.getStoreStorage(trackerServer);
            storageClient = new StorageClient(trackerServer, storageServer);
            result = storageClient.download_file(groupName, remoteFileName.toString());
        } finally {
            if (trackerServer != null) {
                logger.debug("关闭trackerServer连接");
                trackerServer.close();
            }
            if (storageServer != null) {
                logger.debug("关闭storageServer连接");
                storageServer.close();
            }
        }

        return result;
    }

    /**
     * 文件下载
     *
     * @param fileUrl 文件路径：group path+ server path
     * @return byte[]
     * @throws Exception 可能抛出字符串分割异常和IO异常和数组越界异常
     */
    public static ResponseEntity<byte[]> download(String fileUrl) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        byte[] bytes = downFile(fileUrl);
        headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("UTF-8"), "iso-8859-1"));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(bytes, headers, HttpStatus.CREATED);
    }

    /**
     * 删除文件
     *
     * @param groupName      组名
     * @param remoteFileName 服务器路径
     * @throws Exception io异常
     */
    public static int deleteFile(String groupName, String remoteFileName)
            throws Exception {
        logger.info("删除文件的路径：" + groupName + "/" + remoteFileName);
        TrackerServer trackerServer = null;
        StorageServer storageServer = null;
        StorageClient storageClient;
        int i;
        try {
            trackerServer = trackerClient.getConnection();
            storageServer = trackerClient.getStoreStorage(trackerServer);
            storageClient = new StorageClient(trackerServer, storageServer);
            i = storageClient.delete_file(groupName, remoteFileName);
        } finally {
            if (trackerServer != null) {
                logger.debug("关闭trackerServer连接");
                trackerServer.close();
            }
            if (storageServer != null) {
                logger.debug("关闭storageServer连接");
                storageServer.close();
            }
        }
        logger.info("删除文件成功" + i);
        return i;
    }

    /**
     * 查看文件信息
     *
     * @param groupName      组名
     * @param remoteFileName 文件路径
     * @return 文件信息 {@link FileInfo}
     * @throws Exception io异常
     */
    public static FileInfo getFileInfo(String groupName, String remoteFileName) throws Exception {
        logger.info("获取文件信息的路径：" + groupName + "/" + remoteFileName);
        TrackerServer trackerServer = null;
        StorageServer storageServer = null;
        StorageClient storageClient;
        FileInfo fileInfo;
        try {
            trackerServer = trackerClient.getConnection();
            storageServer = trackerClient.getStoreStorage(trackerServer);
            storageClient = new StorageClient(trackerServer, storageServer);
            fileInfo = storageClient.get_file_info(groupName, remoteFileName);
        } finally {
            if (trackerServer != null) {
                logger.debug("关闭trackerServer连接");
                trackerServer.close();
            }
            if (storageServer != null) {
                logger.debug("关闭storageServer连接");
                storageServer.close();
            }
        }
        return fileInfo;
    }
}
