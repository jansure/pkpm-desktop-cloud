package com.pkpmcloud.fileserver.testbase;

import com.pkpmcloud.fileserver.conn.Connection;
import com.pkpmcloud.fileserver.conn.SocketConnection;
import com.pkpmcloud.fileserver.conn.Connection;
import com.pkpmcloud.fileserver.conn.SocketConnection;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 12:53 <br/>
 */
public final class GetTrackerConnection {
    private static final InetSocketAddress address = new InetSocketAddress("192.168.10.128", 22122);
    private static final int soTimeout = 1500;
    private static final int connectTimeout = 600;
    private static final Charset charset = Charset.forName("UTF-8");

    public static Connection getDefaultConnection() {
        return new SocketConnection(address, soTimeout, connectTimeout, charset);
    }
}