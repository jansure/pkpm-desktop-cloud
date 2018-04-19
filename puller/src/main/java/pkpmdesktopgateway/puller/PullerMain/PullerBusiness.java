package pkpmdesktopgateway.puller.PullerMain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.desktop.constant.JobStatusEnum;
import com.desktop.constant.OperatoreTypeEnum;
import com.desktop.constant.ResponseStatusEnum;
import com.desktop.constant.SubscriptionStatusEnum;
import com.desktop.utils.HttpConfigBuilder;
import com.desktop.utils.JsonUtil;
import com.desktop.utils.page.ResultObject;
import com.pkpm.httpclientutil.HttpClientUtil;
import com.pkpm.httpclientutil.HuaWeiResponse;
import com.pkpm.httpclientutil.MyHttpResponse;
import com.pkpm.httpclientutil.common.HttpConfig;
import com.pkpm.httpclientutil.common.HttpMethods;
import com.pkpm.httpclientutil.common.util.PropertiesUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PullerBusiness {
	
	/**
	 * 设置getwayServer的主机地址
	 */
	private static final String SERVER_HOST = PropertiesUtil.getProperty("puller_config.properties", "server_host");

	/**
	 * 设置pkpmCloud的主机地址
	 */
	private static final String BUSINESS_HOST = PropertiesUtil.getProperty("puller_config.properties", "business_host");

	
	/**
	 * 设置任务更新条数
	 */
	private static final String JOB_SIZE = PropertiesUtil.getProperty("puller_config.properties", "job_size");

	/**
	 * 存放启动参数中设置的监控类型参数
	 */
	private static Set<String> OPERATOR_TYPE_SET = new HashSet<String>();
	
	/**
	 * 存放启动参数中设置的监控区域名称参数
	 */
	private static String AREA_CODE = "";
	
	static {
		String opType = System.getProperty("opType");
		if(StringUtils.isNotEmpty(opType)) {
			
			opType = opType.toUpperCase();
			String[] typeArray = opType.split(",");
			if(typeArray != null && typeArray.length > 0) {
				
				for(String typeStr : typeArray) {
					
					OPERATOR_TYPE_SET.add(typeStr);
				}
			}
		}
		
		String areaCodeStr = System.getProperty("areaCode");
		if(StringUtils.isNotEmpty(areaCodeStr)) {
			AREA_CODE = areaCodeStr.toLowerCase();
		}
		
	}
	
	/**
	 * 
	 * @Title: updateJobStatus  
	 * @Description: 更新任务状态  
	 * @return void    无返回
	 */
	public void updateJobStatus() {
		log.info(AREA_CODE);
		String url = SERVER_HOST + "/puller/getJobTasks?jobSize={jobSize}&areaCode={areaCode}";
		url = url.replace("{jobSize}", JOB_SIZE).replace("{areaCode}", AREA_CODE);
		log.info(url);
		try {

			HttpConfig config = HttpConfigBuilder.buildHttpConfigNoToken(url,  5, "utf-8", 100000);
			String responseStr = HttpClientUtil.mysend(config.method(HttpMethods.GET));
			MyHttpResponse myHttpResponse = JsonUtil.deserialize(responseStr, MyHttpResponse.class);
			Integer statusCode = myHttpResponse.getStatusCode();
			
			if (HttpStatus.OK.value() == statusCode) {//请求正常时处理
				String body = myHttpResponse.getBody();
				
				//解析返回对象
				ResultObject result = JsonUtil.deserialize(body, ResultObject.class);
				Integer code = result.getCode();
				
				//返回正常时处理
				if(code == HttpStatus.OK.value()) {
					
					//获取更新时间间隔配置
					Map<String, Integer> configMap = getPullConfigs();
					if(configMap == null) {//如果数据库没有取到，初始化为默认数据
						configMap = InitConfigMap();
					}
					
					List<String> jobIds = (List<String>)result.getData();
					log.info("jobSize:{}", jobIds.size());
					
					//迭代任务列表，通过ID和间隔配置调用更新方法
					if(jobIds != null && jobIds.size() > 0) {
						for(String jobId : jobIds) {
							updateJobByJobId(jobId, configMap);
						}
					}
					
				}
				
			}else {
				log.error("请求获取任务列表接口失败！status:{}", statusCode);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("更新任务失败！");
			
		}
	}

	/**
	 * 
	 * @Title: updateJobByJobId  
	 * @Description: 通过ID更新具体任务
	 * @param jobId    任务Id
	 * @param configMap   时间间隔配置
	 * @return void    无返回值 
	 * @throws  
	 */  
	private void updateJobByJobId(String jobId, Map<String, Integer> configMap) {
		//获取具体任务
		JobDetail detail = getJobDetailByJobId(jobId);
		if(detail == null) {
			//更新任务表，设置状态为失败
			updateJobTask(jobId, JobStatusEnum.FAILED.toString());
			log.error("任务没有详细信息，设置任务状态为失败。任务ID:{}", jobId);
			return;
		}
		
		//通过时间间隔配置，控制本次是否进行更新
		String operatorType = detail.getOperatorType();
		int seconds = configMap.get(operatorType);
		LocalDateTime updateTime = detail.getUpdateTime();
		
		//通过启动参数，控制是否进行更新
		if(OPERATOR_TYPE_SET.size() > 0 && !OPERATOR_TYPE_SET.contains(operatorType)) {
			log.info("此类型任务配置为不更新，跳过。任务ID:{}，operatorType:{}", jobId, operatorType);
			return;
		}
		
		if(updateTime != null ) {
			//解决更新时间延迟问题
			int schedueSecod = updateTime.getSecond() >= 30 ? 31 : 1;
			updateTime = updateTime.withSecond(schedueSecod);
			if(updateTime.plusSeconds(seconds).isAfter(LocalDateTime.now().plusSeconds(5))) {
				log.info("schedueSecod:{}", updateTime.getSecond());
				return;
			}
			
		}
		
		log.info("operatorType:{}", operatorType);
		//根据任务类型，获取华为接口值
		HuaWeiResponse huaweiResponse = getHuaWeiInfo(jobId, detail.getProjectId(), operatorType);
		if(huaweiResponse != null) {
			
			//将目标计算机名放入Response中，为了对比变更操作状态
			if(StringUtils.isNotEmpty(detail.getComputerName())) {
				huaweiResponse.setExt1(detail.getComputerName());
			}
			
			//根据类型获取最终任务状态
			String status = getFinalStatus(huaweiResponse, operatorType);
			
			//增加查询属性时失败状态后，根据检查最大时间判断最终是否失败
			if(status.equals(JobStatusEnum.FAILED.toString())) {
				Integer checkSeconds = configMap.get(operatorType + "_CHECK");
				if(checkSeconds != null && checkSeconds > 0) {
					if(detail.getCreateTime().plusSeconds(checkSeconds).isAfter(LocalDateTime.now())) {
						status = JobStatusEnum.INITIAL.toString();
					}
				}
			}
					
			//更新任务详情表,同时插入更新任务记录
			updateJobDetail(jobId, status);
			
			//更新任务表为成功或失败，更新后此数据不在更新
			if(status.equals(JobStatusEnum.SUCCESS.toString())
					|| status.equals(JobStatusEnum.FAILED.toString())) {
				updateJobTask(jobId, status);
			}
			
			//创建桌面或删除桌面成功，调用云平台订单更新接口
			if(status.equals(JobStatusEnum.SUCCESS.toString())
					&& (operatorType.equals(OperatoreTypeEnum.DELETE.toString())
							|| operatorType.equals(OperatoreTypeEnum.DESKTOP.toString()))) {
				if(operatorType.equals(OperatoreTypeEnum.DELETE.toString())) {
					detail.setStatus(SubscriptionStatusEnum.INVALID.toString());
				}else {
					detail.setStatus(SubscriptionStatusEnum.VALID.toString());
				}
				
				updateCloudSubscription(detail);
			}else if(status.equals(JobStatusEnum.FAILED.toString())
					&& operatorType.equals(OperatoreTypeEnum.DESKTOP.toString())) {//创建桌面失败，返回失败状态
				
				detail.setStatus(SubscriptionStatusEnum.FAILED.toString());
				updateCloudSubscription(detail);
			}
			
		}
		
		
	}
	
	/**  
	 * @Title: getJobDetailByJobId  
	 * @Description: 获取任务详情
	 * @param @param jobId
	 * @param @return    参数  
	 * @return JobDetail    返回类型  
	 * @throws  
	 */  
	private JobDetail getJobDetailByJobId(String jobId) {
		String url = SERVER_HOST + "/puller/getJobDetail?jobId=" + jobId;
		
		try {

			HttpConfig config = HttpConfigBuilder.buildHttpConfigNoToken(url,  5, "utf-8", 100000);
			String responseStr = HttpClientUtil.mysend(config.method(HttpMethods.GET));
			MyHttpResponse myHttpResponse = JsonUtil.deserialize(responseStr, MyHttpResponse.class);
			Integer statusCode = myHttpResponse.getStatusCode();
			
			if (HttpStatus.OK.value() == statusCode) {//请求正常时处理
				String body = myHttpResponse.getBody();
				
				//解析返回对象
				ResultObject result = JsonUtil.deserialize(body, ResultObject.class);
				Integer code = result.getCode();
				
				//返回正常时处理
				if(code == HttpStatus.OK.value()) {
					String detailStr = (String)result.getData();
					
					JobDetail detail = JsonUtil.deserialize(detailStr, JobDetail.class);
					return detail;
				}else {
					log.error("请求获取任务详情接口失败！jobId:{}", jobId);
				}
				
			}else {
				log.error("请求获取任务详情接口失败！status:{}", statusCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取任务详情失败！");
		}
		
		return null;
	}

	/**
	 * @Title: getHuaWeiInfo  
	 * @Description: 获取华为数据
	 * @param jobId 任务Id
	 * @param projectId 项目Id
	 * @param operatorType 操作类型（DESKTOP:创建桌面,DELETE::删除桌面,CHANGE:修改桌面属性,RESIZE:变更桌面规格,BOOT:启动桌面,REBOOT:重启桌面,CLOSE:关闭桌面）
     * @return HuaWeiResponse    返回类型  
	 * @throws  
	 */  
	    
	private HuaWeiResponse getHuaWeiInfo(String jobId, String projectId, String operatorType) {
		String url = SERVER_HOST + "/puller/getHuaWeiInfo?jobId={jobId}&projectId={projectId}&operatorType={operatorType}";
		url = url.replace("{jobId}", jobId).replace("{projectId}", projectId).replace("{operatorType}", operatorType);
		log.info(url);
		try {
			
			HttpConfig config = HttpConfigBuilder.buildHttpConfigNoToken(url,  5, "utf-8", 100000);
			String responseStr = HttpClientUtil.mysend(config.method(HttpMethods.GET));
			MyHttpResponse myHttpResponse = JsonUtil.deserialize(responseStr, MyHttpResponse.class);
			Integer statusCode = myHttpResponse.getStatusCode();
			
			if (HttpStatus.OK.value() == statusCode) {//请求正常时处理
				String body = myHttpResponse.getBody();
				
				//解析返回对象
				ResultObject result = JsonUtil.deserialize(body, ResultObject.class);
				Integer code = result.getCode();
				
				//返回正常时处理
				if(code == HttpStatus.OK.value()) {
					String detailStr = (String)result.getData();
					HuaWeiResponse response = JsonUtil.deserialize(detailStr, HuaWeiResponse.class);
					return response;
				}else {
					log.error("请求获取华为数据接口失败！jobId:{},projectId:{},operatorType:{}", jobId, projectId, operatorType);
				}
				
			}else {
				log.error("请求获取华为数据接口失败！status:" + statusCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取华为数据失败！");
		}
		
		return null;
	}
	  
	  
	/**  
	 * @Title: InitConfigMap  
	 * @Description: 初始化Pull默认配置数据
	 * @return Map<String,Integer>    配置Map
	 * @throws  
	 */  
	    
	private Map<String, Integer> InitConfigMap() {
		log.info("初始化配置Map。。。");
		
		Map<String, Integer> configMap = new HashMap<String, Integer>();
		//检查频率
		configMap.put("DESKTOP", 300);
		configMap.put("DELETE", 60);
		configMap.put("CHANGE", 60);
		configMap.put("RESIZE", 60);
		configMap.put("BOOT", 30);
		configMap.put("REBOOT", 30);
		configMap.put("CLOSE", 30);
		
		//任务最大检查时间
		configMap.put("BOOT_CHECK", 20);
		configMap.put("REBOOT_CHECK", 25);
		configMap.put("CLOSE_CHECK", 40);
		
		return configMap;
	}


	/**  
	 * @Title: getPullConfigs  
	 * @Description: 获取更新时间间隔配置 
	 * @return Map<String,Long>    返回配置信息
	 * @throws  
	 */  
	    
	private Map<String, Integer> getPullConfigs() {
		String url = SERVER_HOST + "/puller/getConfig";
		
		try {
			
			HttpConfig config = HttpConfigBuilder.buildHttpConfigNoToken(url,  5, "utf-8", 100000);
			String responseStr = HttpClientUtil.mysend(config.method(HttpMethods.GET));
			MyHttpResponse myHttpResponse = JsonUtil.deserialize(responseStr, MyHttpResponse.class);
			Integer statusCode = myHttpResponse.getStatusCode();
			
			if (HttpStatus.OK.value() == statusCode) {//请求正常时处理
				String body = myHttpResponse.getBody();
				
				//解析返回对象
				ResultObject result = JsonUtil.deserialize(body, ResultObject.class);
				Integer code = result.getCode();
				
				//返回正常时处理
				if(code == HttpStatus.OK.value()) {
					Map<String, Integer> configMap = (Map<String, Integer>)result.getData();
					return configMap;
				}
				
			}else {
				log.error("请求获取配置列表接口失败！status:{}", statusCode);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取配置列表失败！");
		}
		return null;
	}



	/**  
	 * @Title: getFinalStatus  
	 * @Description: 根据任务类型、华为返回数据，获取华为接口值
	 * @param huaweiResponse 华为返回数据
	 * @param operatorType 任务类型
	 * @return String    返回状态  
	 * @throws  
	 */  
	private String getFinalStatus(HuaWeiResponse huaweiResponse, String operatorType) {
		
		if(operatorType.equals(OperatoreTypeEnum.DESKTOP.toString()) 
				|| operatorType.equals(OperatoreTypeEnum.RESIZE.toString())) {
			
			String status = huaweiResponse.getStatus();
			if(StringUtils.isNotEmpty(status)) {
				if(status.equals(ResponseStatusEnum.RUNNING.toString())) {
					return JobStatusEnum.CREATE.toString();
				}else if(status.equals(ResponseStatusEnum.SUCCESS.toString())) {
					return JobStatusEnum.SUCCESS.toString();
				}else if(status.equals(ResponseStatusEnum.FAILED.toString())) {
					return JobStatusEnum.FAILED.toString();
				}
			}
		}else if(operatorType.equals(OperatoreTypeEnum.DELETE.toString())) {
			
			String msg = huaweiResponse.getDesktop().getError_msg();
			if(StringUtils.isNotEmpty(msg) && msg.contains("desktop_id")) {
				
				return JobStatusEnum.SUCCESS.toString();
			}else {
				return JobStatusEnum.FAILED.toString();
			}
		}else if(operatorType.equals(OperatoreTypeEnum.CHANGE.toString())) {
			
			String computerName = huaweiResponse.getDesktop().getComputer_name();
			String destComputerName = huaweiResponse.getExt1();
			if(StringUtils.isNotEmpty(computerName) && computerName.equals(destComputerName)) {
				
				return JobStatusEnum.SUCCESS.toString();
			}else {
				return JobStatusEnum.FAILED.toString();
			}
		}else if(operatorType.equals(OperatoreTypeEnum.BOOT.toString()) 
				|| operatorType.equals(OperatoreTypeEnum.REBOOT.toString())){
			String status = huaweiResponse.getDesktop().getStatus();
			if(status.equals(ResponseStatusEnum.ACTIVE.toString())) {
				return JobStatusEnum.SUCCESS.toString();
			}else {
				return JobStatusEnum.FAILED.toString();
			}
			
		}else if(operatorType.equals(OperatoreTypeEnum.CLOSE.toString())){
			String status = huaweiResponse.getDesktop().getStatus();
			if(status.equals(ResponseStatusEnum.SHUTOFF.toString())) {
				return JobStatusEnum.SUCCESS.toString();
			}else {
				return JobStatusEnum.FAILED.toString();
			}
			
		}
		
		return JobStatusEnum.INITIAL.toString();
	}


	/**  
	 * @Title: updateJobTask  
	 * @Description: 更新任务表
	 * @param jobId 任务ID
	 * @param status    任务状态
	 * @throws  
	 */   
	private void updateJobTask(String jobId, String status) {
		
		String url = SERVER_HOST + "/puller/updateJobTask";
		
		//设置请求参数
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("jobId", jobId);
		jsonMap.put("status", status);
		try {

			HttpConfig config = HttpConfigBuilder.buildHttpConfigNoToken(url, jsonMap, 5, "utf-8", 100000);
			String responseStr = HttpClientUtil.mysend(config.method(HttpMethods.POST));
			MyHttpResponse myHttpResponse = JsonUtil.deserialize(responseStr, MyHttpResponse.class);
			Integer statusCode = myHttpResponse.getStatusCode();
			
			if (HttpStatus.OK.value() == statusCode) {//请求正常时处理
				String body = myHttpResponse.getBody();
				
				//解析返回对象
				ResultObject result = JsonUtil.deserialize(body, ResultObject.class);
				Integer code = result.getCode();
				
				//返回正常时处理
				if(code == HttpStatus.OK.value()) {
					
//					log.info("请求更新任务数据接口成功！jobId:{},status:{}",jobId, status);
				}else {
					log.error("请求更新任务数据接口失败！jobId:{},status:{}",jobId, status);
				}
				
			}else {
				log.error("请求更新任务数据接口失败！status:{}" + statusCode);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("更新任务数据失败！");
		}
		
	}
	
	/**  
	 * @Title: updateJobDetail  
	 * @Description: 更新任务详情表
	 * @param jobId 任务ID
	 * @param status    任务状态
	 * @throws  
	 */  
	private void updateJobDetail(String jobId, String status) {
		
		String url = SERVER_HOST + "/puller/updateJobDetail";
		
		//设置请参数
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("jobId", jobId);
		jsonMap.put("status", status);
		
		try {
			
			HttpConfig config = HttpConfigBuilder.buildHttpConfigNoToken(url, jsonMap, 5, "utf-8", 100000);
			String responseStr = HttpClientUtil.mysend(config.method(HttpMethods.POST));
			MyHttpResponse myHttpResponse = JsonUtil.deserialize(responseStr, MyHttpResponse.class);
			Integer statusCode = myHttpResponse.getStatusCode();
			
			if (HttpStatus.OK.value() == statusCode) {//请求正常时处理
				String body = myHttpResponse.getBody();
				
				//解析返回对象
				ResultObject result = JsonUtil.deserialize(body, ResultObject.class);
				Integer code = result.getCode();
				
				//返回正常时处理
				if(code == HttpStatus.OK.value()) {
					
//					log.info("请求更新任务数据接口成功！jobId:{},status:{}",jobId, status);
				}else {
					log.error("请求更新任务数据接口失败！jobId:{},status:{}",jobId, status);
				}
				
			}else {
				log.error("请求更新任务数据接口失败！status:{}" + statusCode);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("更新任务数据失败！");
		}
		
	}
	
	/**  
	 * @Title: updateCloudSubscription  
	 * @Description: 更新云平台订单表
	 * @param detail 任务详情
	 * @throws  
	 */   
	private void updateCloudSubscription(JobDetail detail) {

		String url = BUSINESS_HOST + "/subscription/setSubsStatus";
		
		//设置请参数
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("subsId", detail.getSubsId());
		jsonMap.put("status", detail.getStatus());
		jsonMap.put("projectId", detail.getProjectId());
		String jsonStr = JsonUtil.serialize(jsonMap);
		
		try {

			HttpConfig config = HttpConfigBuilder.buildHttpConfigNoToken(url, jsonStr, 5, "utf-8", 100000);
			String responseStr = HttpClientUtil.mysend(config.method(HttpMethods.POST));
			MyHttpResponse myHttpResponse = JsonUtil.deserialize(responseStr, MyHttpResponse.class);
			Integer statusCode = myHttpResponse.getStatusCode();
			
			if (HttpStatus.OK.value() == statusCode) {//请求正常时处理
				String body = myHttpResponse.getBody();
				
				//解析返回对象
				ResultObject result = JsonUtil.deserialize(body, ResultObject.class);
				Integer code = result.getCode();
				
				//返回正常时处理
				if(code == HttpStatus.OK.value()) {
					
//					log.info("请求更新任务数据接口成功！jobId:{},status:{}",jobId, status);
				}else {
					log.error("请求更新任务数据接口失败！subsId:{},status:{}", detail.getSubsId(), detail.getStatus());
				}
				
			}else {
				log.error("请求更新任务数据接口失败！status:{}" + statusCode);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("更新任务数据失败！");
		}
		
	}
	
	public static void main(String[] args) {
		JobDetail detail = new JobDetail();
		detail.setSubsId(123456l);
		detail.setStatus(SubscriptionStatusEnum.VALID.toString());
		new PullerBusiness().updateCloudSubscription(detail );
	}
	
}
