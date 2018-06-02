package com.gatewayserver.gatewayserver.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.desktop.constant.DesktopConstant;
import com.desktop.constant.DesktopQueryConstant;
import com.desktop.constant.OperatoreTypeEnum;
import com.desktop.utils.page.ResultObject;
import com.gateway.common.domain.CommonRequestBean;
import com.gateway.common.dto.Desktop;
import com.gatewayserver.gatewayserver.utils.CommonRequestBeanUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DesktopControllerTest {
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
        info.setProjectId("80367f2e346747139970a46e3e50c055");
        info.setImageId("997488ed-fa23-4671-b88c-d364c0405334");// 默认的镜像Workspace-User-Template
//		info.setImageId("997488ed-fa23-4671-b88c-d364c0405334");// Image does not exist
        info.setDataVolumeSize(100);
        info.setHwProductId("workspace.c2.large.windows");
        info.setGloryProductName("YPF-TEST1234567890");
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
        mockMvc.perform(MockMvcRequestBuilders.get("/desktop/queryJob/ff80808262ddf7940162fa5132ba02a9").accept(MediaType.APPLICATION_JSON))
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
    
    
    @Test
    public void testQueryDesktopDetail() throws Exception {
    	/* CommonRequestBean commonRequestBean = new CommonRequestBean();
    	 commonRequestBean.setProjectId("80367f2e346747139970a46e3e50c055");
    	 Desktop desktop = new Desktop();
    	 desktop.setDesktopId("01daa5da-5c77-4445-8ddf-6902df953a50");
    	 List<Desktop> desktopList = new ArrayList<>();
    	 desktopList.add(desktop);
    	 commonRequestBean.setDesktops(desktopList);
    	 String requestJson = JSONObject.toJSONString(commonRequestBean);
    	 System.out.println(requestJson);*/
    	 
    	 Map<String,String> desktopMap = new HashMap<String,String>();
    	 desktopMap.put("desktop_id", "01daa5da-5c77-4445-8ddf-6902df953a50");
    	 List<Map<String,String>> desktopMapList = new ArrayList<>();
    	 desktopMapList.add(desktopMap);
    	 
    	 Map<String,Object> desktopsMap = new HashMap<>();
    	 desktopsMap.put("desktops", desktopMapList);
    	 desktopsMap.put("projectId", "80367f2e346747139970a46e3e50c055");
    	 
    	 String requestJson = JSONObject.toJSONString(desktopsMap);
    	 
    	 mockMvc.perform(MockMvcRequestBuilders.post("/desktop/queryDesktopDetail")
    			.contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
		    	.andExpect(MockMvcResultMatchers.status().isOk())
		    	.andDo(MockMvcResultHandlers.print()).andReturn();
    }
    
    /**
     * 操作桌面：重启、启动、关闭桌面
     * @throws Exception
     */
    @Test
    public void testOperateDesktop() throws Exception {
    	 Map<String,String> desktopMap = new HashMap<String,String>();
    	 desktopMap.put("desktop_id", "01daa5da-5c77-4445-8ddf-6902df953a50");
    	 desktopMap.put("desktopOperatorType", OperatoreTypeEnum.BOOT.toString());
    	 List<Map<String,String>> desktopMapList = new ArrayList<>();
    	 desktopMapList.add(desktopMap);
    	 
    	 Map<String,Object> desktopsMap = new HashMap<>();
    	 desktopsMap.put("desktops", desktopMapList);
    	 desktopsMap.put("projectId", "80367f2e346747139970a46e3e50c055");
    	 desktopsMap.put("userId", 82);
    	 desktopsMap.put("userName", "kbp123");
    	 desktopsMap.put("subsId", "152461804275100");
    	 desktopsMap.put("adId", 4);
    	 String requestJson = JSONObject.toJSONString(desktopsMap);
    	 
    	 mockMvc.perform(MockMvcRequestBuilders.post("/desktop/operateDesktop")
     			.contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
 		    	.andExpect(MockMvcResultMatchers.status().isOk())
 		    	.andDo(MockMvcResultHandlers.print()).andReturn();
    }
    /**
     * 
	{

		"projectId":"80367f2e346747139970a46e3e50c055",
		 "queryDesktopType": "detail" ,
		
	}
     */
    /**
     * 查询桌面列表  、桌面详情列表
     * @throws Exception 
     */
    @Test
    public void testQueryDesktopListOrDetail() throws Exception{
    	
    	CommonRequestBean commonRequestBean = new CommonRequestBean();
    	commonRequestBean.setProjectId("80367f2e346747139970a46e3e50c055");
    	commonRequestBean.setQueryDesktopType(DesktopConstant.QUERY_DESKTOP_SIMPLE);
    	commonRequestBean.setLimit("3");
    	String requestJson = JSONObject.toJSONString(commonRequestBean);
    	
    	mockMvc.perform(MockMvcRequestBuilders.post("/desktop/listOrDetailList")
     			.contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
 		    	.andExpect(MockMvcResultMatchers.status().isOk())
 		    	.andDo(MockMvcResultHandlers.print()).andReturn();
    	 
    }
    
    
    public void testUpdateStrategy(){
    	CommonRequestBean commonRequestBean = new CommonRequestBean();
    }

}
