package com.messageserver.messageserver.service.impl;

import java.util.Properties;

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
	private static final Properties SMS_CONFIG = PropertiesUtil.getProperties("application.properties");

	
	private static SmnClient smnClient;
	
	/**
	 * 使用构造函数初始化
	 */
	static {
		Preconditions.checkNotNull(SMS_CONFIG, "短信配置参数不能为空！");
		CloudAccount cloudAccount = new CloudAccount(
				 SMS_CONFIG.getProperty("message.sms.username", ""),
				 SMS_CONFIG.getProperty("message.sms.password", ""),
				 SMS_CONFIG.getProperty("message.sms.domainName", ""),
				 SMS_CONFIG.getProperty("message.sms.areaName", ""));
		 
		 smnClient = cloudAccount.getSmnClient();
    }
	
    @Override
    public Boolean sendMessage(Message message) {
    	 // 构造请求对象
        SmsPublishRequest smsPublishRequest = new SmsPublishRequest();
        
        // 短信签名必填,需要在消息通知服务的自助页面申请签名，申请办理时间约2天
        String signId =  SMS_CONFIG.getProperty("message.sms.signId", "");

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
    
    public static void main(String[] args) {
    	Message message = new Message();
    	
    	//消息类型为短信
    	message.setMessageType(MessageTypeEnum.sms.toString());
    	//消息接收人
    	message.setTo("15311445882");
    	//消息内容
    	message.setContent("这是一个测试短信，请勿回复。");
    	//验证
//    	message.eval();
    	
    	MessageSender sender = new SmsMessageSenderImpl();
		sender.sendMessage(message);
    }
}
