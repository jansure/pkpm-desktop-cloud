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
public class SubsDetailsControllerTest {
	
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
    public void getList() throws Exception {
	   mockMvc.perform(MockMvcRequestBuilders.get("/subsdetails/getList/1/2/2").accept(MediaType.APPLICATION_JSON))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andDo(MockMvcResultHandlers.print())
			   .andReturn();
    }

}
