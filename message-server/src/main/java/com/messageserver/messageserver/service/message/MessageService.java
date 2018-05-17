package com.messageserver.messageserver.service.message;

/**
 * 
 * @ClassName: MessageService  
 * @Description: 消息服务类
 * @author wangxiulong  
 * @date 2018年5月15日  
 *
 */
public interface MessageService {

	/**
     * 发送消息
     */
    String sendMessage(Message message);
    
    /**
     * 查询消息
     */
    Message findMessage(String jobId);

	  
	/**  
	 * @Title: messageTask  
	 * @Description: 消息定时任务，实现消息发送并更新状态 
	 * @throws  
	 */  
	void messageTask();

}
