package com.pkpmcloud.fileserver.config;

import com.pkpmcloud.fileserver.pool.ConnectionPool;
import com.pkpmcloud.fileserver.pool.PooledConnectionFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class FastdfsConfig {
	
	@Value("${fileupload.FastDFS.soTimeout}")
	public Integer soTimeout;
	@Value("${fileupload.FastDFS.connectTimeout}")
	public Integer connectTimeout;
	@Value("${fileupload.FastDFS.maxTotal}")
	public Integer maxTotal;
	@Value("${fileupload.FastDFS.maxTotalPerKey}")
	public Integer maxTotalPerKey;
	@Value("${fileupload.FastDFS.maxIdlePerKey}")
	public Integer maxIdlePerKey;
	@Value("${fileupload.FastDFS.trackers}")
	public String trackers;
	
//	@Autowired
//	MyPoolConfig genericKeyedObjectPoolConfig;
	
	 /**
	  * FastDFS连接池配置
	  * @return ConnectionPool
	  */
//	@Bean
	public ConnectionPool connectionPool(){
		//ConnectionPool connectionPool = new ConnectionPool(factory);
		PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(soTimeout, connectTimeout);
		GenericKeyedObjectPoolConfig  genericKeyedObjectPoolConfig = new GenericKeyedObjectPoolConfig();
		
		genericKeyedObjectPoolConfig.setMaxTotal(maxTotal);
		genericKeyedObjectPoolConfig.setMaxTotalPerKey(maxTotalPerKey);
		genericKeyedObjectPoolConfig.setMaxIdlePerKey(maxIdlePerKey);
		ConnectionPool connectionPool = new ConnectionPool(pooledConnectionFactory, genericKeyedObjectPoolConfig);
		return connectionPool;
	}
	
//	/**
//	 *  FastDfs命令执行器
//	 * @return DefaultCommandExecutor
//	 */
//	@Bean 
//	public DefaultCommandExecutor defaultCommandExecutor(){
//		DefaultCommandExecutor defaultCommandExecutor = new DefaultCommandExecutor(trackers, connectionPool());
//		return defaultCommandExecutor;
//	}
//	/**
//	 * FastDFS Tracker Client
//	 * @return DefaultTrackerClient
//	 */
//	@Bean
//	public DefaultTrackerClient defaultTrackerClient(){
//		DefaultTrackerClient defaultTrackerClient = new DefaultTrackerClient(defaultCommandExecutor());
//		return defaultTrackerClient;
//	}
//	
//	@Bean
//	public DefaultStorageClient defaultStorageClient(){
//		DefaultStorageClient defaultStorageClient = new DefaultStorageClient(defaultCommandExecutor(), defaultTrackerClient());
//		return defaultStorageClient;
//	}
	
}
