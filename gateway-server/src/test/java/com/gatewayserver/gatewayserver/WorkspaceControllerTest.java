package com.gatewayserver.gatewayserver;

import com.alibaba.fastjson.JSONObject;
import com.gatewayserver.gatewayserver.domain.CommonRequestBean;
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
public class WorkspaceControllerTest {
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
    public void testCreateToken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/desktop/createToken/d3e6d7eda4094efc92d31aa010945980").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }

    @Test
    public void testCreateDesktop() throws Exception {
        CommonRequestBean info = new CommonRequestBean();
        info.setUserName("xuhe10");
        info.setUserEmail("cherishpf@163.com");
        info.setProjectId("9487c2cb4c4d4c828868098b7a78b497");
        info.setImageId("997488ed-fa23-4671-b88c-d364c0405334");// 默认的镜像Workspace-User-Template
//		info.setImageId("997488ed-fa23-4671-b88c-d364c0405334");// Image does not exist
        info.setDataVolumeSize(100);
        info.setHwProductId("workspace.c2.large.windows");
        info.setGloryProductName("YPF-TEST");
        info.setOuName("pkpm");
        info.setAdId(1);
        info.setUserId(88);
        info.setSubsId(4L);
        info.setOperatorStatusId(14);// AD域创建后调用创建桌面接口时传入的jobId，创建桌面任务生成后会更新此jobId
        String requestJson = JSONObject.toJSONString(info);
        mockMvc.perform(MockMvcRequestBuilders.post("/desktop/createDesktop").contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
//				.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }

    @Test
    public void testQueryJob() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/queryJob/ff80808262863b0701628f555cf600cf").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }

    @Test
    public void testDeleteDesktop() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/deleteDesktop")
                .param("desktopId", "849eec4f-bbaf-49a4-8a36-8d11bc0148a2").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }

}
