package com.pkpmcloud.fileserver.protocol.tracker.request;

import com.pkpmcloud.fileserver.constant.CmdConstants;
import com.pkpmcloud.fileserver.protocol.BaseRequest;
import com.pkpmcloud.fileserver.protocol.ProtocolHead;

/**
 * 获取存储节点请求
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 15:23 <br/>
 */
public class GetStorageNodeRequest extends BaseRequest {
    private static final byte withoutGroupCmd = CmdConstants.TRACKER_PROTO_CMD_SERVICE_QUERY_STORE_WITHOUT_GROUP_ONE;

    /**
     * 获取存储节点
     */
    public GetStorageNodeRequest() {
        super();
        this.head = new ProtocolHead(withoutGroupCmd);
    }
}
