package com.messageserver.messageserver.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.desktop.constant.MessageStatusEnum;
import com.desktop.utils.StringUtil;
import com.google.common.base.Preconditions;
import com.messageserver.messageserver.service.message.Message;
import com.messageserver.messageserver.service.message.MessageService;
import com.pkpmdesktopcloud.redis.RedisCache;

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
	
	/**
	 * Redis中所有消息分组key
	 */
	private final String MESSAGE_SERVICE_ALL_KEY = "messageServiceAllKey";
	
	/**
	 * Redis中未发送消息分组key
	 */
	private final String MESSAGE_SERVICE_UNDO_KEY = "messageServiceUndoKey";
	
	/**
	 * 未发送消息JobId集合key
	 */
	private final String UNDO_MESSAGE_JOB_ID_SET = "undoMessageJobIdSet";
	
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param message
	 * @return  
	 * @see com.messageserver.messageserver.service.message.MessageService#sendMessage(com.messageserver.messageserver.service.message.Message)  
	 */  
	@Override
	public String sendMessage(Message message) {
		
		//验证
		Preconditions.checkNotNull(message, "发送的消息不能为空");
		message.eval();
		
		//初始化状态为发送中
		message.setStatus(MessageStatusEnum.CREATE.toString());
		
		// 随机生成jobId
		String jobId = StringUtil.getUUID();
		
		//放到全部的分组Redis中
		RedisCache cacheAll = new RedisCache(MESSAGE_SERVICE_ALL_KEY);
		cacheAll.putObject(jobId, message);
		
		//放到未发送的分组Redis中
		RedisCache cacheUndo = new RedisCache(MESSAGE_SERVICE_UNDO_KEY);
		Set<String> undoIdSet = (Set<String>) cacheUndo.getObject(UNDO_MESSAGE_JOB_ID_SET);
		if(undoIdSet == null) {
			undoIdSet = new HashSet<String>();
		}
		undoIdSet.add(jobId);
		cacheUndo.putObject(UNDO_MESSAGE_JOB_ID_SET, undoIdSet);
		
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
		//从全部的分组Redis中获取
		RedisCache cacheAll = new RedisCache(MESSAGE_SERVICE_ALL_KEY);
		
		return (Message)cacheAll.getObject(jobId);
	}
	
}
