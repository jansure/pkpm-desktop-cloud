package com.cabr.pkpm;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
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
    
    //验证controller是否正常响应并打印返回结果
    @Test
    public void perfectInfoInit() throws Exception {
	   mockMvc.perform(MockMvcRequestBuilders.post("/user/perfectInfoInit").accept(MediaType.APPLICATION_JSON)
			   .contentType(MediaType.APPLICATION_FORM_URLENCODED)
			   .accept(MediaType.APPLICATION_JSON_UTF8)
			   .param("userID", "4"))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andDo(MockMvcResultHandlers.print())
			   .andReturn();
    }
    
    @Test
    public void perfectInfo() throws Exception {
	   mockMvc.perform(MockMvcRequestBuilders.post("/user/perfectInfo").accept(MediaType.APPLICATION_JSON)
			   .contentType(MediaType.APPLICATION_FORM_URLENCODED)
			   .accept(MediaType.APPLICATION_JSON_UTF8)
			   .param("userID", "1")
			   .param("userIdentificationCard", "110")
			   .param("userIdentificationName", "警察")
			   .param("userOrganization", "朝阳区"))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andDo(MockMvcResultHandlers.print())
			   .andReturn();
    }
    
    @Test
    public void changPassword() throws Exception {
	   mockMvc.perform(MockMvcRequestBuilders.post("/user/changPassword")
			   .contentType(MediaType.APPLICATION_FORM_URLENCODED)
			   .accept(MediaType.APPLICATION_JSON_UTF8)
			   .param("userID", "1")
			   .param("oldPassword", "123")
			   .param("newPassword", "111"))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andDo(MockMvcResultHandlers.print())
			   .andReturn();
    }
    
    @Test
    public void changMobileNumber() throws Exception {
	   mockMvc.perform(MockMvcRequestBuilders.post("/user/changMobileNumber").accept(MediaType.APPLICATION_JSON)
			   .contentType(MediaType.APPLICATION_FORM_URLENCODED)
			   .accept(MediaType.APPLICATION_JSON_UTF8)
			   .param("userID", "1")
			   .param("oldMobileNumber", "11122")
			   .param("checkCode", "1111")
			   .param("newMobileNumber", "119")
			   .param("password", "111"))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andDo(MockMvcResultHandlers.print())
			   .andReturn();
    }
    
    @Test
    public void getBackPassword() throws Exception {
	   mockMvc.perform(MockMvcRequestBuilders.post("/user/getBackPassword").accept(MediaType.APPLICATION_JSON)
			   .contentType(MediaType.APPLICATION_FORM_URLENCODED)
			   .accept(MediaType.APPLICATION_JSON_UTF8)
			   .param("userID", "1")
			   .param("mobileNumber", "11122")
			   .param("checkCode", "1111")
			   .param("newPassword", "119"))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andDo(MockMvcResultHandlers.print())
			   .andReturn();
    }
}