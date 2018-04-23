package com.gatewayserver.gatewayserver.controller;

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
 * 测试类GatewayServerParamController
 * @author yangpengfei
 * @date 2018年4月19日
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GatewayServerParamControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    /**
     * 初始化执行
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
    public void testGetAdAndProject() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/params/getAdAndProject").accept(MediaType.APPLICATION_JSON)
        		.param("areaCode", "cn-north-1")
        		.param("ouName", "远大北京公司/销售部"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }
}
