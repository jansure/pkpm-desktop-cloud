package com.messageserver.messageserver.service.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.desktop.constant.MessageStatusEnum;
import com.desktop.constant.MessageTypeEnum;
import com.desktop.utils.StringUtil;
import com.google.common.base.Preconditions;
import com.messageserver.messageserver.service.message.Message;
import com.messageserver.messageserver.service.message.MessageSender;
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
	
	//每次任务执行的条数
	private int taskJobs = 3;
	
	@Autowired  
    @Qualifier("mailMessageSender")  
    private MessageSender mailMessageSender; 
	
	@Autowired  
    @Qualifier("smsMessageSender")  
    private MessageSender smsMessageSender; 
	 
	
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


	  
	/* (非 Javadoc)  
	 *   
	 *     
	 * @see com.messageserver.messageserver.service.message.MessageService#messageTask()  
	 */  
	@Override
	public void messageTask() {
		
		//获取未发送信息
		RedisCache cacheUndo = new RedisCache(MESSAGE_SERVICE_UNDO_KEY);
		Set<String> undoIdSet = (Set<String>) cacheUndo.getObject(UNDO_MESSAGE_JOB_ID_SET);
		if(undoIdSet == null || undoIdSet.size() == 0) {
			log.info("没有需要发送的信息");
			return;
		}
		
		RedisCache cacheAll = new RedisCache(MESSAGE_SERVICE_ALL_KEY);
		
		//控制最大条数不能超过集合数目
//		taskJobs = undoIdSet.size() >= taskJobs ? taskJobs : undoIdSet.size();
		
		int index = 0;
		Iterator<String> iter = undoIdSet.iterator();
		while (iter.hasNext()) {
		    String jobId = iter.next();
		    
		    //获取消息
		    cacheAll.getObject(jobId);
		    Message message = (Message)cacheAll.getObject(jobId);
		    if(message != null) {
		    	
		    	//真正发送消息
			    message = realSendMessage(message);
			    
			    //更新消息状态到缓存
			    cacheAll.putObject(jobId, message);
		    }
		    
		    //从未发送集合中删除
		    iter.remove();
		    
		    index ++;
		    if(index == taskJobs) {
		    	break;
		    }
		}
		
		//更新未发送消息集合到缓存
		cacheUndo.putObject(UNDO_MESSAGE_JOB_ID_SET, undoIdSet);
		
	}


	  
	/**  
	 * @Title: realSendMessage  
	 * @Description: 真正发送消息
	 * @param message    消息  
	 */  
	private Message realSendMessage(Message message) {
		
		//保存发送结果
		boolean isSent = false;
		
		//根据类型调用不同实现类
		switch (MessageTypeEnum.eval(message.getMessageType())) {
		
	        case email:
	        	isSent = mailMessageSender.sendMessage(message);
	            break;
	        case sms:
	        	isSent = smsMessageSender.sendMessage(message);
	            break;
	        case message:
	            break;
	        default:
	            log.error("错误消息类型");
	    }
		
		if(isSent) {
			message.setStatus(MessageStatusEnum.SUCCESS.toString());
		} else {
			message.setStatus(MessageStatusEnum.FAILED.toString());
		}
		
		return message;
	}
	
}
