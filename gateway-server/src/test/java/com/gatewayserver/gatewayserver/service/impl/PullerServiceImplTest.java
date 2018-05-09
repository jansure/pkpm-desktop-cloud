  
/**    
 * @Title: PullerServiceImplTest.java  
 * @Package com.gatewayserver.gatewayserver.service.impl  
 * @Description: TODO(用一句话描述该文件做什么)  
 * @author wangxiulong  
 * @date 2018年4月23日  
 * @version V1.0    
 */  
    
package com.gatewayserver.gatewayserver.service.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.desktop.constant.JobStatusEnum;
import com.desktop.constant.OperatoreTypeEnum;
import com.gateway.common.domain.PkpmOperatorStatus;
import com.gateway.common.domain.PkpmPullerConfig;
import com.gatewayserver.gatewayserver.service.PullerService;
import com.pkpm.httpclientutil.HuaWeiResponse;

import lombok.extern.slf4j.Slf4j;

/**  
 * @ClassName: PullerServiceImplTest  
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author wangxiulong  
 * @date 2018年4月23日  
 *    
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PullerServiceImplTest {

	@Autowired
    private PullerService pullerService;
	
	@Test
	public void getJobTasksTest() {
		int jobSize = 2;
		String areaCode = "";
		List<String> jobList = pullerService.getJobTasks(jobSize, areaCode);
		log.info("jobList:{}", jobList);
	}
	
	@Test
	public void getJobDetailTest() {
		String jobId = "ff80808262ddf7940162f070a4d701df";
		PkpmOperatorStatus detail = pullerService.getJobDetail(jobId );
		log.info("detail:{}", detail);
	}
	
	@Test
	public void getConfigTest() {
		List<PkpmPullerConfig> configList = pullerService.getConfig();
		log.info("configList:{}", configList);
	}
	
	@Test
	public void getHuaWeiInfoTest() {
		String jobId = "ff80808262ddf7940162f070a4d701df";
		String projectId = "80367f2e346747139970a46e3e50c055";
		String operatorType = OperatoreTypeEnum.DESKTOP.toString();
		
		HuaWeiResponse response = pullerService.getHuaWeiInfo(jobId, projectId, operatorType);
		log.info("response:{}", response);
	}
	
	@Test
	public void updateJobTaskTest() {
		String jobId = "ff80808262ddf7940162f070a4d701df";
		String status = JobStatusEnum.SUCCESS.toString();
		
		int num = pullerService.updateJobDetail(jobId, status);
		log.info("num:{}", num);
	}
	
	@Test
	public void updateJobDetailTest() {
		String jobId = "ff80808262ddf7940162f070a4d701df";
		String status = JobStatusEnum.SUCCESS.toString();
		
		int num = pullerService.updateJobTask(jobId, status);
		log.info("num:{}", num);
	}

}
