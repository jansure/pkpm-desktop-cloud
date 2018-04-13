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

/**
 * ProductController测试类 使用MockMvc测试ProductController中的方法
 * 
 * @author yangpengfei
 * @date 2017/12/22
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductControllerTest {
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	/**
	 * 初始化执行
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		// 项目拦截器有效 初始化MockMvc对象
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	/**
	 * 验证controller是否正常响应并打印返回结果
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetNavigation() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/product/subProducts").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}
	
	/**
	 * 测试从URL下载文件
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetHelp() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/product/downloads")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.param("filename", "法律条款说明test1.txt")) 
				//PKPM帮助文档.doc
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}

	/**
	 * 测试从url文件返回json
	 * @throws Exception
	 */
	@Test
	public void testGetLegalTerms() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/product/legalTerms")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("filename", "法律条款说明test1.txt"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}

	/**
	 * 测试返回默认的产品套餐配置列表
	 * @throws Exception
	 */
	@Test
	public void testGetProductTypeList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/product/product-buy").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}
	
	/**
	 * 测试根据产品套餐类型id获取自动配置的components项
	 * @throws Exception
	 */
	@Test
	public void testGetComponentByProductType() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/product/subComponents").accept(MediaType.APPLICATION_JSON)
				.param("productType", "200"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}
	
	/**
	 * 根据用户手机号及订单号发短信通知用户云桌面开户信息
	 * @throws Exception
	 */
	@Test
	public void testSendClientMessage() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/product/sendClientMessage").accept(MediaType.APPLICATION_JSON)
				.param("userMobileNumber", "18410429882")
				.param("subsId", "3"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}
	
	/**
	 * 测试获取使用教程说明接口
	 * @throws Exception
	 */
	@Test
	public void testGetManual() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/product/manual"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}
}
