package com.messageserver.messageserver.service.impl;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.desktop.constant.MessageTypeEnum;
import com.google.common.base.Preconditions;
import com.messageserver.messageserver.service.Message;
import com.messageserver.messageserver.service.MessageSender;
import com.pkpm.httpclientutil.common.util.PropertiesUtil;
import com.smn.account.CloudAccount;
import com.smn.client.SmnClient;
import com.smn.common.HttpResponse;
import com.smn.model.request.sms.SmsPublishRequest;

import lombok.extern.slf4j.Slf4j;


/**
 * EmailMessageSenderImpl
 *
 * author jansure.zhang
 * @since 5/5/2018
 */

@Slf4j
@Service("smsMessageSender")
public class SmsMessageSenderImpl implements MessageSender{
	
	/**
	 * 获取短信配置参数
	 */
//	private static final Properties SMS_CONFIG = PropertiesUtil.getProperties("config.properties");
 
	@Value("message.sms.username")
	private String username;
	
	@Value("message.sms.password")
	private String password;
	
	@Value("message.sms.domainName")
	private String domainName;
	
	@Value("message.sms.areaName")
	private String areaName;
	
	@Value("message.sms.signId")
	private String signId;
	
	private SmnClient smnClient;
	
	
	/**
	 * 初始化
	 */
	@Bean
	public int init() {
		 
		CloudAccount cloudAccount = new CloudAccount(
				 username,
				 password,
				 domainName,
				 areaName);
		 
		 smnClient = cloudAccount.getSmnClient();
		 
		 return 200;
	}
	
	
    @Override
    public Boolean sendMessage(Message message) {
    	init();
    	
    	 // 构造请求对象
        SmsPublishRequest smsPublishRequest = new SmsPublishRequest();
        
        // 设置手机号码
        smsPublishRequest.setEndpoint(message.getTo());
        // 设置短信内容，短信内容中不要出现【】或者[]
        smsPublishRequest.setMessage(message.getContent());
        // 设置短信签名
        smsPublishRequest.setSignId(signId);

        // 发送短信
        HttpResponse res = smnClient.smsPublish(smsPublishRequest);
        
        System.out.println(res);
        return true;
    }
    
   
}
