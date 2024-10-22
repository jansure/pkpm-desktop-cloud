package com.pkpmcloud.fileserver.protocol.tracker.request;

import com.pkpmcloud.fileserver.constant.CmdConstants;
import com.pkpmcloud.fileserver.constant.OtherConstants;
import com.pkpmcloud.fileserver.mapper.DynamicFieldType;
import com.pkpmcloud.fileserver.mapper.FastDFSColumn;
import com.pkpmcloud.fileserver.protocol.BaseRequest;
import com.pkpmcloud.fileserver.protocol.ProtocolHead;
import com.pkpmcloud.fileserver.utils.Validate;


/**
 * 获取文件源存储服务器 请求
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 15:32 <br/>
 */
public class GetFetchStorageRequest extends BaseRequest {
    private static final byte fetchCmd = CmdConstants.TRACKER_PROTO_CMD_SERVICE_QUERY_FETCH_ONE;
    private static final byte updateCmd = CmdConstants.TRACKER_PROTO_CMD_SERVICE_QUERY_UPDATE;

    /**
     * 组名
     */
    @FastDFSColumn(index = 0, max = OtherConstants.FDFS_GROUP_NAME_MAX_LEN)
    private String groupName;

    /**
     * 路径名
     */
    @FastDFSColumn(index = 1, dynamicField = DynamicFieldType.allRestByte)
    private String path;

    /**
     * 获取文件源服务器
     *
     * @param groupName 组名称
     * @param path      路径
     * @param toUpdate  toUpdate
     */
    public GetFetchStorageRequest(String groupName, String path, boolean toUpdate) {
        Validate.notBlank(groupName, "分组不能为空");
        Validate.notBlank(path, "文件路径不能为空");
        this.groupName = groupName;
        this.path = path;
        if (toUpdate) {
            head = new ProtocolHead(updateCmd);
        } else {
            head = new ProtocolHead(fetchCmd);
        }
    }

    public String getGroupName() {
        return groupName;
    }

    public String getPath() {
        return path;
    }

}
