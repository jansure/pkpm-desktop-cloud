package com.pkpmcloud.fileserver.protocol.storage;

import com.pkpmcloud.fileserver.protocol.storage.request.QueryFileInfoRequest;
import com.pkpmcloud.fileserver.model.FileInfo;
import com.pkpmcloud.fileserver.protocol.BaseResponse;
import com.pkpmcloud.fileserver.protocol.storage.request.QueryFileInfoRequest;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 18:34 <br/>
 */
public class QueryFileInfoCommand extends StorageCommand<FileInfo> {

    /**
     * 文件上传命令
     *
     * @param groupName 组名称
     * @param path      文件路径
     */
    public QueryFileInfoCommand(String groupName, String path) {
        super();
        this.request = new QueryFileInfoRequest(groupName, path);
        // 输出响应
        this.response = new BaseResponse<FileInfo>() {
        };
    }
}