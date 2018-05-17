package com.messageserver.messageserver.service.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.messageserver.messageserver.service.message.Message;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageControllerTest {
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;
	
    // 初始化执行
    @Before
    public void init() throws Exception {
    	// 项目拦截器有效  初始化MockMvc对象
    	mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
	
    @Test
    public void sendMessage() throws Exception {
    	
    	mockMvc.perform(post("/message/sendMessage").contentType(MediaType.APPLICATION_FORM_URLENCODED)
      		   .accept(MediaType.APPLICATION_JSON_UTF8)
      		   .param("messageType", "sms")
      		   .param("to", "15311445882").param("content", "这是测试短信")
      		   )
// 		    	.andExpect(status().isOk())  
 		    	.andDo(MockMvcResultHandlers.print())
 		        .andReturn(); 
    }
    
    @Test
	public void findMessage() throws Exception{
		
    	mockMvc.perform(MockMvcRequestBuilders.get("/message/findMessage?jobId=e3ef320cde844df5a23c4b07f66ce5dd").accept(MediaType.APPLICATION_JSON_UTF8)
       		   )
//  		    	.andExpect(status().isOk())  
  		    	.andDo(MockMvcResultHandlers.print())
  		        .andReturn();
    	
		
	}

}
