package com.pkpmdesktopcloud.redis;

import java.util.HashSet;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocketFactory;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class RedisConfig extends JedisPoolConfig {
	private String host = Protocol.DEFAULT_HOST;
	private int port = Protocol.DEFAULT_PORT;
	private int connectionTimeout = Protocol.DEFAULT_TIMEOUT;
	private int soTimeout = Protocol.DEFAULT_TIMEOUT;
	private String password;
	private int database = Protocol.DEFAULT_DATABASE;
	private String clientName;
	private boolean ssl;
	private SSLSocketFactory sslSocketFactory;
	private SSLParameters sslParameters;
	private HostnameVerifier hostnameVerifier;
	private Serializer serializer = JDKSerializer.INSTANCE;

	private String master;
	private Set<String> nodes = new HashSet<String>();

	public boolean isSsl() {
		return ssl;
	}

	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}

	public SSLSocketFactory getSslSocketFactory() {
		return sslSocketFactory;
	}

	public void setSslSocketFactory(SSLSocketFactory sslSocketFactory) {
		this.sslSocketFactory = sslSocketFactory;
	}

	public SSLParameters getSslParameters() {
		return sslParameters;
	}

	public void setSslParameters(SSLParameters sslParameters) {
		this.sslParameters = sslParameters;
	}

	public HostnameVerifier getHostnameVerifier() {
		return hostnameVerifier;
	}

	public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
		this.hostnameVerifier = hostnameVerifier;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		if (host == null || "".equals(host)) {
			host = Protocol.DEFAULT_HOST;
		}
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if ("".equals(password)) {
			password = null;
		}
		this.password = password;
	}

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		if ("".equals(clientName)) {
			clientName = null;
		}
		this.clientName = clientName;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getSoTimeout() {
		return soTimeout;
	}

	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}

	public Serializer getSerializer() {
		return serializer;
	}

	public void setSerializer(Serializer serializer) {
		this.serializer = serializer;
	}

	/**
	 * @return master
	 */

	public String getMaster() {
		return master;
	}

	/**
	 * @param paramtheparamthe{bare_field_name}
	 *            to set
	 */

	public void setMaster(String master) {
		this.master = master;
	}

	/**
	 * @return nodes
	 */
	public Set<String> getNodes() {
		return nodes;
	}

	/**
	 * @param paramtheparamthe{bare_field_name}
	 *            to set
	 */
	public void setNodes(Set<String> nodes) {
		this.nodes = nodes;
	}

}
