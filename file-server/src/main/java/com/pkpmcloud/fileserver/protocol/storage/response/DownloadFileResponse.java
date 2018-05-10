package com.pkpmcloud.fileserver.protocol.storage.response;

import com.pkpmcloud.fileserver.protocol.storage.callback.DownloadCallback;
import com.pkpmcloud.fileserver.protocol.BaseResponse;
import com.pkpmcloud.fileserver.protocol.storage.callback.DownloadCallback;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 16:06 <br/>
 */
public class DownloadFileResponse<T> extends BaseResponse<T> {

    private DownloadCallback<T> callback;

    public DownloadFileResponse(DownloadCallback<T> callback) {
        super();
        this.callback = callback;
    }

    /**
     * 解析反馈内容
     */
    @Override
    public T decodeContent(InputStream in, Charset charset) throws IOException {
        // 解析报文内容
        FastDFSInputStream input = new FastDFSInputStream(in, getContentLength());
        return callback.receive(input);
    }
}

