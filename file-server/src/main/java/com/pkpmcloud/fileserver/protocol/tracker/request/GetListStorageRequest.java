package com.pkpmcloud.fileserver.protocol.tracker.request;

import com.pkpmcloud.fileserver.constant.CmdConstants;
import com.pkpmcloud.fileserver.constant.OtherConstants;
import com.pkpmcloud.fileserver.mapper.DynamicFieldType;
import com.pkpmcloud.fileserver.mapper.FastDFSColumn;
import com.pkpmcloud.fileserver.protocol.BaseRequest;
import com.pkpmcloud.fileserver.protocol.ProtocolHead;
import com.pkpmcloud.fileserver.utils.Validate;


/**
 * 获取Storage服务器状态请求
 *
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 12:44 <br/>
 */
public class GetListStorageRequest extends BaseRequest {
    /**
     * 组名
     */
    @FastDFSColumn(index = 0, max = OtherConstants.FDFS_GROUP_NAME_MAX_LEN)
    private String groupName;

    /**
     * 存储服务器ip地址
     */
    @FastDFSColumn(index = 1, max = OtherConstants.FDFS_IPADDR_SIZE - 1, dynamicField = DynamicFieldType.nullable)
    private String storageIpAddr;

    private GetListStorageRequest() {
        head = new ProtocolHead(CmdConstants.TRACKER_PROTO_CMD_SERVER_LIST_STORAGE);
    }

    /**
     * 列举存储服务器状态
     */
    public GetListStorageRequest(String groupName, String storageIpAddr) {
        this();
        Validate.notBlank(groupName, "分组不能为空");
        this.groupName = groupName;
        this.storageIpAddr = storageIpAddr;
    }

    /**
     * 列举组当中存储节点状态
     */
    public GetListStorageRequest(String groupName) {
        this();
        this.groupName = groupName;
        Validate.notBlank(groupName, "分组不能为空");
    }

    public String getGroupName() {
        return groupName;
    }

    public String getStorageIpAddr() {
        return storageIpAddr;
    }
}