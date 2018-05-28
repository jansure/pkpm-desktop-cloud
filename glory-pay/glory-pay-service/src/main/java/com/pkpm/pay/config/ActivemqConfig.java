package com.pkpm.pay.config;

import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;


@Configurable
@PropertySource("classpath:mq_config.properties")
public class ActivemqConfig {
	@Value("${mq.brokerURL}")
	private String brokerURL;
	
	@Value("${mq.userName}")
	private String username;
	
	@Value("${mq.password}")
	private String password;
	
	@Value("${mq.pool.maxConnections}")
	private Integer maxConnections;
	
	@Value("${tradeQueueName.notify}")
	private String notify;
	
	@Value("${orderQueryQueueName.query}")
	private String orderQuery;
	
	
	
	@Bean
	public ActiveMQConnectionFactory getTargetConnectionFactory() {
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
		activeMQConnectionFactory.setBrokerURL(brokerURL);
		activeMQConnectionFactory.setUserName(username);
		activeMQConnectionFactory.setPassword(password);
		return activeMQConnectionFactory;
	}
	
	@Bean
	public PooledConnectionFactory getPooledConnectionFactory() {
		PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
		pooledConnectionFactory.setConnectionFactory(getTargetConnectionFactory());
		pooledConnectionFactory.setMaxConnections(10);
		return pooledConnectionFactory;
	}
	
	@Bean
	public SingleConnectionFactory getSingleConnectionFactory() {
		SingleConnectionFactory singleConnectionFactory = new SingleConnectionFactory(getPooledConnectionFactory());
		return singleConnectionFactory;
	}
	
	@Bean
	public JmsTemplate getJmsTemplate() {
		JmsTemplate JmsTemplate = new JmsTemplate(getSingleConnectionFactory());
		JmsTemplate.setDefaultDestinationName(notify);
		return JmsTemplate;
		
	}
	
}
