package com.pkpmcloud.fileserver.protocol.storage;

import com.pkpmcloud.fileserver.protocol.storage.request.TruncateRequest;
import org.cleverframe.fastdfs.protocol.BaseResponse;
import org.cleverframe.fastdfs.protocol.storage.request.TruncateRequest;

/**
 * 文件Truncate命令
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 18:53 <br/>
 */
public class TruncateCommand extends StorageCommand<Void> {

    /**
     * 文件Truncate命令
     *
     * @param path     文件路径
     * @param fileSize 文件大小
     */
    public TruncateCommand(String path, long fileSize) {
        super();
        this.request = new TruncateRequest(path, fileSize);
        // 输出响应
        this.response = new BaseResponse<Void>() {
        };
    }
}
