package com.pkpmcloud.fileserver.testbase;

import com.pkpmcloud.fileserver.conn.Connection;
import com.pkpmcloud.fileserver.conn.SocketConnection;
import com.pkpmcloud.fileserver.conn.Connection;
import com.pkpmcloud.fileserver.conn.SocketConnection;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 16:49 <br/>
 */
public class GetStorageConnection {
    private static final InetSocketAddress address = new InetSocketAddress("192.168.10.128", 23000);
    private static final int soTimeout = 1500;
    private static final int connectTimeout = 600;
    private static final Charset charset = Charset.forName("UTF-8");

    public static Connection getDefaultConnection() {
        return new SocketConnection(address, soTimeout, connectTimeout, charset);
    }
}
