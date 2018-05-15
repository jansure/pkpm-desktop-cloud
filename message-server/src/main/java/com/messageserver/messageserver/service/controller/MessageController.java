package com.messageserver.messageserver.service.controller;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.desktop.utils.page.ResultObject;
import com.google.common.base.Preconditions;
import com.messageserver.messageserver.service.message.Message;
import com.messageserver.messageserver.service.message.MessageService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(value = "/message")
public class MessageController {
	
	@Resource
	private MessageService messageService;
	
	/**
	 * 发送消息
	 */
    @PostMapping(value = "/sendMessage")
    public ResultObject sendMessage(Message message) {
    	
    	// 发送消息
		String jobId = messageService.sendMessage(message);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("jobId", jobId);
        return ResultObject.success(map);
    }
    
	/**
	 * 查询消息
	 */
    @RequestMapping(value = "/findMessage", method = RequestMethod.GET)
	public ResultObject findMessage(String jobId) {
		
		Preconditions.checkArgument(StringUtils.isNotEmpty(jobId), "jobId不能为空");
		
		//查询消息
		Message message = messageService.findMessage(jobId);
		
		if(message == null) {
			return ResultObject.failure("无此消息");
		}
		
		return ResultObject.success(message);
	}

}
