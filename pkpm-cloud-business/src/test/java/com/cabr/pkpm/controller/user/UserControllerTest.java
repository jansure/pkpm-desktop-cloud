package com.cabr.pkpm.controller.user;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
public class UserControllerTest {
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
    @Rollback(true)
	public void registTest() throws Exception{
    	
    	String monitorTelephone = RandomStringUtils.randomNumeric(4);
    	stringRedisTemplate.opsForValue().set(monitorTelephone, "6666");
    	mockMvc.perform(post("/user/regist")
    		   .contentType(MediaType.APPLICATION_FORM_URLENCODED)
    		   .accept(MediaType.APPLICATION_JSON_UTF8).param("userName", "danyuanceshi"+monitorTelephone)
    		   .param("checkCode", "6666").param("userMobileNumber", monitorTelephone).param("userLoginPassword", "123"))
    		   .andExpect(jsonPath("$.status", is(1)))
    		   .andExpect(status().isOk())
    		   .andReturn().getResponse();
    	
    	stringRedisTemplate.delete(monitorTelephone);
    }
    
    @Test
	public void loginTest() throws Exception{
    	
    	mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_FORM_URLENCODED)
     		   .accept(MediaType.APPLICATION_JSON_UTF8).param("userNameOrTelephoneOrUserEmail", "kbp123")
     		   .param("userLoginPassword", "123"))
    		   .andExpect(jsonPath("$.status", is(1)))
    		   .andExpect(status().isOk())
    		   .andReturn().getResponse();
	}
    
    @Test	
    public void sendMessageTest() throws Exception{
    	
    	mockMvc.perform(post("/user/sendMessage").contentType(MediaType.APPLICATION_FORM_URLENCODED)
      		   .accept(MediaType.APPLICATION_JSON_UTF8).param("userMobileNumber", "15517156082"))
     		   .andExpect(jsonPath("$.status", is(1)))
     		   .andExpect(status().isOk())
     		   .andReturn().getResponse();
    }
    
    @Test
  	public void findByEmailOrUserMobileNumberTest() throws Exception{
    	mockMvc.perform(post("/user/findByEmailOrUserMobileNumber").contentType(MediaType.APPLICATION_FORM_URLENCODED)
       		   .accept(MediaType.APPLICATION_JSON_UTF8).param("userEmailOrMobileNumber", "kbp123"))
      		   .andExpect(jsonPath("$.status", is(0)))
      		   .andExpect(status().isOk())
      		   .andReturn().getResponse();
  	}
    
    @Test
    public void getUserNameBySessionTest() throws Exception{
    	mockMvc.perform(get("/user/getUserName").session((MockHttpSession)getLoginSession()).
    			contentType(MediaType.APPLICATION_FORM_URLENCODED)
    			.accept(MediaType.APPLICATION_JSON_UTF8))
    	.andExpect(jsonPath("$.status", is(1)))
    	.andExpect(status().isOk())
    	.andReturn().getResponse();
    }
    
    
    private HttpSession getLoginSession() throws Exception{  
        MvcResult result = this.mockMvc  
                                .perform(post("/user/login")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                     	      	.accept(MediaType.APPLICATION_JSON_UTF8).param("userNameOrTelephoneOrUserEmail", "kbp123")
                    	      	.param("userLoginPassword", "123"))  
                                .andExpect(status().isOk())  
                                .andReturn();  
        return result.getRequest().getSession();  
    }  
    
  	 
}
