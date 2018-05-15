package com.messageserver.messageserver.service.impl;

import org.springframework.stereotype.Service;

import com.desktop.utils.StringUtil;
import com.messageserver.messageserver.service.message.Message;
import com.messageserver.messageserver.service.message.MessageService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: MessageServiceImpl  
 * @Description: 消息服务实现类 
 * @author wangxiulong  
 * @date 2018年5月15日  
 *
 */
@Slf4j
@Service
public class MessageServiceImpl implements MessageService {
	
	
	
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param message
	 * @return  
	 * @see com.messageserver.messageserver.service.message.MessageService#sendMessage(com.messageserver.messageserver.service.message.Message)  
	 */  
	@Override
	public String sendMessage(Message message) {
		
		// 随机生成jobId
		String jobId = StringUtil.getUUID();
		
		return jobId;
	}

	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param jobId
	 * @return  
	 * @see com.messageserver.messageserver.service.message.MessageService#findMessage(java.lang.String)  
	 */  
	@Override
	public Message findMessage(String jobId) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
