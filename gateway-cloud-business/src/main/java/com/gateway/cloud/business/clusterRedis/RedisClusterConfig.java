package com.gateway.cloud.business.clusterRedis;
/*package com.cabr.pkpm.clusterRedis;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisClusterConfig {
	@Value("${spring.redis.cluster.nodes}")
	private String clusterNodes;
	@Bean
	public JedisCluster getJedisCluser(){
	    JedisPoolConfig poolConfig = new JedisPoolConfig();
	    poolConfig.setMaxIdle(20); 
	    poolConfig.setMaxTotal(1000);
	    poolConfig.setMaxWaitMillis(3000);//设置超时时间  
	    poolConfig.setTestOnBorrow(true);
	    
		String[] cluster = clusterNodes.split(",");
		Set<HostAndPort> nodes =new HashSet<HostAndPort>();
		for(String node : cluster){
			String[] host = node.split(":");
			nodes.add(new HostAndPort(host[0], Integer.parseInt(host[1])));
		}
		
		JedisCluster jCluster = new JedisCluster(nodes,poolConfig);
		return jCluster;
		
	}
}
*/