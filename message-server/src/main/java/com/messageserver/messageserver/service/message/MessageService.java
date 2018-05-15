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

}
