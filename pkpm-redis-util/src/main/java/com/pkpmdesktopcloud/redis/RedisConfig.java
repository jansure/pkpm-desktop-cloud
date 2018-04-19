package com.pkpmdesktopcloud.redis;

import com.oracle.webservices.internal.api.databinding.DatabindingMode;
import lombok.Data;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocketFactory;

@Data
public class RedisConfig extends JedisPoolConfig {
    private String host = Protocol.DEFAULT_HOST;
    private Integer port = Protocol.DEFAULT_PORT;
    private Integer connectionTimeout = Protocol.DEFAULT_TIMEOUT;
    private Integer soTimeout = Protocol.DEFAULT_TIMEOUT;
    private String password;
    private Integer dataBase = Protocol.DEFAULT_DATABASE;
    private String clientName;
    private Boolean ssl;
    private SSLSocketFactory sslSocketFactory;
    private SSLParameters sslParameters;
    private HostnameVerifier hostNameVerifier;
    private Serializer serializer = JDKSerializer.INSTANCE;
}
