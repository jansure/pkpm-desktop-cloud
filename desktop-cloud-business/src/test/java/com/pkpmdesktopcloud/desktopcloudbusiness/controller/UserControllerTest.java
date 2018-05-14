package com.pkpmdesktopcloud.desktopcloudbusiness.controller;


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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
	
	
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;
	
    // 初始化执行
    @Before
    public void setUp() throws Exception {
    	// 项目拦截器有效  初始化MockMvc对象
    	mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
//    @Test
//    public void perfectInfoInit() throws Exception {
//	   mockMvc.perform(MockMvcRequestBuilders.post("/user/perfectInfoInit").accept(MediaType.APPLICATION_JSON)
//			   .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//			   .accept(MediaType.APPLICATION_JSON_UTF8)
//			   .param("userID", "4"))
//			   .andExpect(MockMvcResultMatchers.status().isOk())
//			   .andDo(MockMvcResultHandlers.print())
//			   .andReturn();
//    }

    
    @Test
	public void sendMessageTest() throws Exception{
    	
    	mockMvc.perform(post("/user/sendMessage").contentType(MediaType.APPLICATION_FORM_URLENCODED)
     		   .accept(MediaType.APPLICATION_JSON_UTF8).param("userMobileNumber", "15517156082")
     		   .param("userLoginPassword", "123"))
		    	.andExpect(status().isOk())  
		        .andReturn(); 
	}
    
   
  	 
}
