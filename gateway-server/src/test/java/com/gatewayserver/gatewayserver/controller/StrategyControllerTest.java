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

import com.alibaba.fastjson.JSONObject;
import com.gateway.common.domain.CommonRequestBean;
import com.gateway.common.dto.strategy.FileRedirection;
import com.gateway.common.dto.strategy.Options;
import com.gateway.common.dto.strategy.Policies;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyControllerTest {
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
     * {"projectId":"80367f2e346747139970a46e3e50c055",
		}
     * @throws Exception 
     */
    
    @Test
    public void testQueryStrategy() throws Exception{
    	
    	CommonRequestBean commonRequestBean = new CommonRequestBean();
    	commonRequestBean.setProjectId("80367f2e346747139970a46e3e50c055");
    	String requestJson = JSONObject.toJSONString(commonRequestBean);
    	
    	mockMvc.perform(MockMvcRequestBuilders.post("/strategy/queryStrategy")
     			.contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
 		    	.andExpect(MockMvcResultMatchers.status().isOk())
 		    	.andDo(MockMvcResultHandlers.print()).andReturn();
    }
    
    /**
     * {
		  "projectId":"9487c2cb4c4d4c828868098b7a78b497",
		  "policies": {
		    "usb_port_redirection": {
		      "enable": false,
		      "options": {
		        "usb_image_enable": false,
		        "usb_video_enable": true,
		        "usb_printer_enable": false,
		        "usb_storage_enable": true,
		        "usb_smart_card_enable": false
		      }
		    },
		    "printer_redirection": {
		      "enable": true,
		      "options": {
		        "sync_client_default_printer_enable": false,
		        "universal_printer_driver": "Universal Printing PCL 6"
		      }
		    },
		    "file_redirection": {
		      "redirection_mode": "READ_AND_WRITE",
		      "options": {
		        "fixed_drive_enable": true,
		        "removable_drive_enable": false,
		        "cd_rom_drive_enable": true,
		        "network_drive_enable": true
		      }
		    },
		    "clipboard_redirection": "TWO_WAY_ENABLED",
		    "hdp_plus": {
		      "hdp_plus_enable": false,
		      "display_level": "QUALITY_FIRST",
		      "options": {
		        "bandwidth": 24315,
		        "frame_rate": 18,
		        "video_frame_rate": 20,
		        "smoothing_factor": 58,
		        "lossy_compression_quality": 88
		      }
		    }
		  }
		}
     * @throws Exception 
     */
    @Test
    public  void testUpdateStrategy() throws Exception{
    	
    	FileRedirection fileRedirection = new FileRedirection();
    	fileRedirection.setRedirectionMode("READ_ONLY");  //DISABLED：表示禁用。 READ_ONLY：表示只读。 READ_AND_WRITE：表示读写。
    	Policies policies = new Policies();
    	policies.setFileRedirection(fileRedirection);
    	CommonRequestBean commonRequestBean = new CommonRequestBean();
    	commonRequestBean.setPolicies(policies);
    	commonRequestBean.setProjectId("80367f2e346747139970a46e3e50c055");
    	
    	Options options = new Options();
    	options.setFixedDriveEnable(true);
    	options.setRemovableDrivenEable(false);
    	options.setCdRomDriveEnable(true);
    	options.setNetworkDriveEnable(true);
    	
    	String requestJson = JSONObject.toJSONString(commonRequestBean);
    	System.out.println(requestJson);
    	
    	mockMvc.perform(MockMvcRequestBuilders.post("/strategy/updateStrategy")
     			.contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
 		    	.andExpect(MockMvcResultMatchers.status().isOk())
 		    	.andDo(MockMvcResultHandlers.print()).andReturn();
    }
}
