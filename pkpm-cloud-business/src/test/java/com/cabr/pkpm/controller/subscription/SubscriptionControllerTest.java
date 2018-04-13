package com.cabr.pkpm.controller.subscription;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration

public class SubscriptionControllerTest {
	@Autowired
	private WebApplicationContext context;
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext wac;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	private MockHttpSession session;  
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.session = new MockHttpSession(); 
	}
	private  RequestBuilder request = null;
	
    @Test
	public void immediatelyUseTest() throws Exception{
    	List<Map<String,String>> productNameVOList=new ArrayList<>();
    	Map<String,String> productNameVOMap1 = new HashMap<>();
    	Map<String,String> productNameVOMap2 = new HashMap<>();
    	productNameVOMap1.put("productId", "10086");
    	productNameVOMap1.put("productName", "pkpm-xxx");
    	productNameVOMap2.put("productId", "10087");
    	productNameVOMap2.put("productName", "pkpm-yyy");
    	productNameVOList.add(productNameVOMap1);
    	productNameVOList.add(productNameVOMap2);
    	
    	String productMapStr = JSON.toJSONString(productNameVOList);
    	System.out.println(productMapStr);
    	System.out.println("====================================");
          System.out.println(this.mockMvc  
          .perform((post("/subscription/immediatelyUse")).session((MockHttpSession)getLoginSession())  
                  .contentType(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_JSON)  
                  .param("productId", "10001")
      			.param("productName", "基础班套餐")
      			.param("regionId", "01")
      			.param("regionName", "华北")
      			.param("hostConfigId", "256cpu+16核")
      			.param("hostConfigName", "10001")
      			.param("cloudStorageTimeId", "6")
      			.param("cloudStorageTimeName", "6") 
      		.param("productNameVOList", productMapStr) 
                  ).andExpect(status().isOk()));
          System.out.println(1111);
	}
	
    
    private HttpSession getLoginSession() throws Exception{  
        MvcResult result = this.mockMvc  
                                .perform(post("/user/login").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                     	      		   .accept(MediaType.APPLICATION_JSON_UTF8).param("userNameOrTelephoneOrUserEmail", "kbp123")
                    	      		   .param("userLoginPassword", "123"))  
                                .andExpect(status().isOk())  
                                .andReturn();  
        return result.getRequest().getSession();  
    }  
    
}
