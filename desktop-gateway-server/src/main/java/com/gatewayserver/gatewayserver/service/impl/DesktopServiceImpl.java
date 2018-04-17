package com.gatewayserver.gatewayserver.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.gatewayserver.gatewayserver.dto.desktop.DesktopSpecResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.desktop.constant.DesktopServiceEnum;
import com.desktop.constant.JobStatusEnum;
import com.desktop.constant.OperatoreTypeEnum;
import com.desktop.constant.ResponseStatusEnum;
import com.desktop.utils.HttpConfigBuilder;
import com.desktop.utils.JsonUtil;
import com.desktop.utils.StringUtil;
import com.desktop.utils.exception.Exceptions;
import com.desktop.utils.page.BeanUtil;
import com.desktop.utils.page.ResultObject;
import com.gatewayserver.gatewayserver.dao.PkpmJobStatusDAO;
import com.gatewayserver.gatewayserver.dao.PkpmOperatorStatusDAO;
import com.gatewayserver.gatewayserver.dao.PkpmProjectDefDAO;
import com.gatewayserver.gatewayserver.dao.PkpmTokenDAO;
import com.gatewayserver.gatewayserver.dao.mapper.PkpmOperatorStatusMapper;
import com.gatewayserver.gatewayserver.domain.CommonRequestBean;
import com.gatewayserver.gatewayserver.domain.PkpmJobStatus;
import com.gatewayserver.gatewayserver.domain.PkpmOperatorStatus;
import com.gatewayserver.gatewayserver.domain.PkpmProjectDef;
import com.gatewayserver.gatewayserver.domain.PkpmToken;
import com.gatewayserver.gatewayserver.dto.Desktop;
import com.gatewayserver.gatewayserver.dto.DesktopCreation;
import com.gatewayserver.gatewayserver.dto.DesktopRequest;
import com.gatewayserver.gatewayserver.dto.JobBean;
import com.gatewayserver.gatewayserver.service.AdService;
import com.gatewayserver.gatewayserver.service.DesktopService;
import com.gatewayserver.gatewayserver.utils.CommonRequestBeanBuilder;
import com.gatewayserver.gatewayserver.utils.CommonRequestBeanUtil;
import com.gatewayserver.gatewayserver.utils.PkpmOperatorStatusBeanUtil;
import com.google.common.base.Preconditions;
import com.pkpm.httpclientutil.HttpClientUtil;
import com.pkpm.httpclientutil.MyHttpResponse;
import com.pkpm.httpclientutil.builder.HCB;
import com.pkpm.httpclientutil.common.HttpConfig;
import com.pkpm.httpclientutil.common.HttpHeader;
import com.pkpm.httpclientutil.common.HttpMethods;
import com.pkpm.httpclientutil.common.SSLs.SSLProtocolVersion;
import com.pkpm.httpclientutil.exception.HttpProcessException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yangpengfei
 * @date 2018/03/14
 */
@Service
@Slf4j
@Transactional
public class DesktopServiceImpl implements DesktopService {
	@Resource
	private PkpmTokenDAO pkpmTokenDAO;
	@Resource
	private PkpmJobStatusDAO pkpmJobStatusDAO;
	@Resource
	private PkpmProjectDefDAO pkpmProjectDefDAO;
	@Resource
	private CommonRequestBeanBuilder commonRequestBeanBuilder;
	@Resource
	private PkpmOperatorStatusDAO pkpmOperatorStatusDAO;
	@Resource
	private AdService adService;
	@Resource
	private PkpmOperatorStatusMapper pkpmOperatorStatusMapper;

	@Override
	public String createToken(String projectId) {
		try {
			Preconditions.checkArgument(null != projectId, "projectID不能为空");
			PkpmProjectDef projectDef = pkpmProjectDefDAO.selectById(projectId);
			Preconditions.checkNotNull(projectDef, "未查询到相关的projectDef！");

			String areaName = projectDef.getAreaName();
			// 根据projectId查询pkpm_token表中的token
			PkpmToken pkpmTokenNow = new PkpmToken();
			pkpmTokenNow.setAreaName(areaName);
			pkpmTokenNow.setProjectId(projectId);
			pkpmTokenNow.setIsValid(1);
			pkpmTokenNow = pkpmTokenDAO.select(pkpmTokenNow);
			if (pkpmTokenNow != null) {
				// 若当前时间小于过期时间，直接返回token信息
				LocalDateTime now = LocalDateTime.now();
				LocalDateTime expire = pkpmTokenNow.getExpireTime();
				if (now.isBefore(expire)) {
					return pkpmTokenNow.getToken();
				} else {
					// 若当前时间超过过期时间，将token置为无效
					pkpmTokenNow.setIsValid(0);
					Integer n = pkpmTokenDAO.update(pkpmTokenNow);
					log.info("更新pkpmToken记录：" + n);
				}
			}

			// 若未查询到Token记录，或Token已过期时，生成新的token
			PkpmToken pkpmToken = new PkpmToken();
			// 若未查询到Token记录，调用华为接口生成token，并更新数据库
			CommonRequestBean requestBean = commonRequestBeanBuilder.buildBeanForToken(projectId);
			// 校验参数
			CommonRequestBeanUtil.checkCommonRequestBeanForToken(requestBean);
			// POST获取token
			String token = "";
			String url = requestBean.getPkpmWorkspaceUrl().getUrl().replaceAll("\\{areaName\\}",
					requestBean.getPkpmWorkspaceUrl().getAreaName());
			String auth = JsonUtil.serialize(requestBean.getAuth());
			String strJson = "{\"auth\":" + auth + "}";

			String strTokenResponse = HttpClientUtil.mysend(
					HttpConfigBuilder.buildHttpConfig(url, strJson, token, 5, null, 10000).method(HttpMethods.POST));
			MyHttpResponse myHttpResponse = JsonUtil.deserialize(strTokenResponse, MyHttpResponse.class);
			Map<String, Object> respHeaders = myHttpResponse.getHeaders();

			if (respHeaders.get("X-Subject-Token") != null) {
				// 从响应header中取token值
				token = respHeaders.get("X-Subject-Token").toString();
				// 过期时间设置为8小时，华为返回的token过期时间为24小时
				LocalDateTime expireTime = LocalDateTime.now().plusHours(8);
				// fixme 直接更新，返回0则插入
				// 更新pkpmToken表
				pkpmToken.setProjectId(projectId);
				pkpmToken.setAreaName(areaName);
				pkpmToken.setCreateTime(LocalDateTime.now());
				pkpmToken.setExpireTime(expireTime);
				pkpmToken.setToken(token);
				pkpmToken.setIsValid(1);
				Integer n = pkpmTokenDAO.update(pkpmToken);
				log.info("token已更新：{}条", n.toString());
				if (n == 0) {
					pkpmTokenDAO.insert(pkpmToken);
					log.info("token已插入数据库：{}", pkpmToken.toString());
				}
				return token;
			}
		} catch (HttpProcessException httpProcessException) {
			log.error(httpProcessException.getMessage());
		} catch (Exception exception) {
			log.error(exception.getMessage());
		}
		throw Exceptions.newBusinessException("获取token失败！");
	}

	public ResultObject createAdAndDesktop(CommonRequestBean commonRequestBean) {
		adService.addAdUser(commonRequestBean);
		DesktopCreation desktopCreation = createDesktop(commonRequestBean);
		Preconditions.checkNotNull(desktopCreation);
		Preconditions.checkNotNull(desktopCreation.getJobId());
		Preconditions.checkArgument(desktopCreation.getJobId().length() > 0);
		log.info("createDesktop job_id = " + desktopCreation.getJobId());
		return ResultObject.success(desktopCreation.getJobId(), "正在创建桌面......");

	}

	@Override
	public DesktopCreation createDesktop(CommonRequestBean commonRequestBean) {
		Preconditions.checkArgument(null != commonRequestBean, "请求对象commonRequestBean不能为空");
		try {
			// 构造requestBean参数
			commonRequestBean = commonRequestBeanBuilder.buildBeanForCreateDesktop(commonRequestBean);
			// 校验requestBean参数
			CommonRequestBeanUtil.checkCommonRequestBeanForCreateDesk(commonRequestBean);
			String token = commonRequestBean.getPkpmToken().getToken();
			String url = commonRequestBean.getPkpmWorkspaceUrl().getUrl()
					.replaceAll("\\{areaName\\}", commonRequestBean.getPkpmWorkspaceUrl().getAreaName())
					.replaceAll("\\{projectId\\}", commonRequestBean.getPkpmWorkspaceUrl().getProjectId());
			String desktops = JsonUtil.serialize(commonRequestBean.getDesktops());
			String strJson = "{\"desktops\":" + desktops + "}";
			
			HttpConfig buildHttpConfig = HttpConfigBuilder.buildHttpConfig(url, strJson, token, 5, null, 10000);
			String strJobResponse = HttpClientUtil.mysend(
					HttpConfigBuilder.buildHttpConfig(url, strJson, token, 5, null, 10000).method(HttpMethods.POST));
			MyHttpResponse myHttpResponse = JsonUtil.deserialize(strJobResponse, MyHttpResponse.class);
			Integer statusCode = myHttpResponse.getStatusCode();
			String body = myHttpResponse.getBody();

			// 如果响应成功，将job插入数据库，并返回job_id；若失败，返回异常信息
			if (statusCode == HttpStatus.OK.value()) {
				JobBean jobBean = JsonUtil.deserialize(body, JobBean.class);
				
				// 更新到PkpmOperatorStatus
				PkpmOperatorStatus pkpmOperatorStatus = new PkpmOperatorStatus();
				BeanUtil.copyPropertiesIgnoreNull(commonRequestBean, pkpmOperatorStatus);
				pkpmOperatorStatus.setId(commonRequestBean.getOperatorStatusId());
				pkpmOperatorStatus.setJobId(jobBean.getJobId());
				pkpmOperatorStatus.setComputerName(commonRequestBean.getDesktops().get(0).getComputerName());
				pkpmOperatorStatus.setOperatorType(OperatoreTypeEnum.DESKTOP.toString());
				PkpmOperatorStatusBeanUtil.checkNotNull(pkpmOperatorStatus);
				pkpmOperatorStatusDAO.update(pkpmOperatorStatus);
				
				// 保存到PkpmJobStatus
				PkpmJobStatus pkpmJob = new PkpmJobStatus();
				pkpmJob.setJobId(jobBean.getJobId());
				pkpmJob.setCreateTime(LocalDateTime.now());
				pkpmJob.setProjectId(commonRequestBean.getPkpmWorkspaceUrl().getProjectId());
				PkpmProjectDef projectDef = pkpmProjectDefDAO
						.selectById(commonRequestBean.getPkpmWorkspaceUrl().getProjectId());
				pkpmJob.setWorkspaceId(projectDef.getWorkspaceId());
				pkpmJob.setStatus(JobStatusEnum.INITIAL.toString());
				pkpmJob.setOperatorType(OperatoreTypeEnum.DESKTOP.toString());
				pkpmJob.setAreaCode(commonRequestBean.getAreaCode());
				pkpmJobStatusDAO.insert(pkpmJob);
				
				DesktopCreation desktopCreation = new DesktopCreation();
				desktopCreation.setJobId(jobBean.getJobId());
				desktopCreation.setComputerName(commonRequestBean.getDesktops().get(0).getComputerName());
				log.info("创建桌面成功，返回desktopCreation===========" + desktopCreation.toString());
				return desktopCreation;
			} else {
				log.error(body);
			}
		} catch (HttpProcessException httpProcessException) {
			log.error(httpProcessException.getMessage());
		} catch (Exception exception) {
			log.error(exception.getMessage());
		}
		throw Exceptions.newBusinessException("创建桌面失败！");
	}

	@Override
	public JobBean queryJob(String jobId) {
		Preconditions.checkArgument(null != jobId, "jobId不能为空");
		try {
			CommonRequestBean requestBean = commonRequestBeanBuilder.buildBeanForJob(jobId);
			CommonRequestBeanUtil.checkCommonRequestBeanForJob(requestBean);
			String token = requestBean.getPkpmToken().getToken();
			String url = requestBean.getPkpmWorkspaceUrl().getUrl()
					.replaceAll("\\{areaName\\}", requestBean.getPkpmWorkspaceUrl().getAreaName())
					.replaceAll("\\{projectId\\}", requestBean.getPkpmWorkspaceUrl().getProjectId())
					.replaceAll("\\{jobId\\}", requestBean.getPkpmWorkspaceUrl().getJobId());

			String job = HttpClientUtil.get(HttpConfigBuilder.buildHttpConfig(url, token, 5, "utf-8", 10000));
			// 响应体若不包含job_id，则抛出异常
			if (job.indexOf("job_id") != -1) {
				JobBean jobBean = JsonUtil.deserialize(job, JobBean.class);
				return jobBean;
			} else {
				log.error(job);
			}
		} catch (HttpProcessException httpProcessException) {
			log.error(httpProcessException.getMessage());
		} catch (Exception exception) {
			log.error(exception.getMessage());
		}
		throw Exceptions.newBusinessException("查询创建桌面任务执行情况失败！");
	}

	@Override
	public JobBean getLoginInfoById(Integer userId) {
		// fixme
		return null;
	}

	@Override
	public String deleteDesktop(CommonRequestBean requestBean) {

		String strMyHttpResponse = "";
		Integer statusCode = null;

		try {
			// 校验参数
			CommonRequestBeanUtil.checkCommonRequestBean(requestBean);
			CommonRequestBeanUtil.checkCommonRequestBeanForDelChgDesk(requestBean);

			commonRequestBeanBuilder.buildBeanForDeleteDesktop(requestBean);
			
			String token = requestBean.getPkpmToken().getToken();
			String url = requestBean.getPkpmWorkspaceUrl().getUrl()
					.replaceAll("\\{areaName\\}", requestBean.getPkpmWorkspaceUrl().getAreaName())
					.replaceAll("\\{projectId\\}", requestBean.getPkpmWorkspaceUrl().getProjectId())
					.replaceAll("\\{desktopId\\}", requestBean.getPkpmWorkspaceUrl().getDesktopId());
			String strJson = "{\"delete_users\":" + requestBean.getDeleteUsers() + "}";

			HttpConfig config = HttpConfigBuilder.buildHttpConfig(url, strJson, token, 5, "utf-8", 10000);
			strMyHttpResponse = HttpClientUtil.mysend(config.method(HttpMethods.DELETE));
			MyHttpResponse myHttpResponse = JsonUtil.deserialize(strMyHttpResponse, MyHttpResponse.class);
			statusCode = myHttpResponse.getStatusCode();

			if (statusCode == HttpStatus.NO_CONTENT.value()) {

				// 随机生成jobId
				String jobId = StringUtil.getUUID();

				// 保存到PkpmJobStatus
				PkpmJobStatus pkpmJob = new PkpmJobStatus();
				pkpmJob.setJobId(jobId);
				pkpmJob.setCreateTime(LocalDateTime.now());
				pkpmJob.setProjectId(requestBean.getProjectId());
				PkpmProjectDef projectDef = pkpmProjectDefDAO.selectById(requestBean.getProjectId());
				pkpmJob.setWorkspaceId(projectDef.getWorkspaceId());
				pkpmJob.setStatus(JobStatusEnum.INITIAL.toString());
				pkpmJob.setOperatorType(OperatoreTypeEnum.DELETE.toString());
				pkpmJobStatusDAO.insert(pkpmJob);

				// 保存到PkpmOperatorStatus
				PkpmOperatorStatus pkpmOperator = new PkpmOperatorStatus();
				pkpmOperator.setJobId(jobId);
				pkpmOperator.setProjectId(requestBean.getProjectId());
				pkpmOperator.setUserId(requestBean.getUserId());
				pkpmOperator.setSubsId(requestBean.getSubsId());
				pkpmOperator.setAdId(requestBean.getAdId());
				pkpmOperator.setUserName(requestBean.getUserName());
				pkpmOperator.setDesktopId(requestBean.getDesktopId());
				pkpmOperator.setComputerName(requestBean.getDesktops().get(0).getComputerName());
				pkpmOperator.setOperatorType(OperatoreTypeEnum.DELETE.toString());
				pkpmOperator.setStatus(JobStatusEnum.CREATE.toString());
				pkpmOperator.setCreateTime(LocalDateTime.now());
				pkpmOperator.setIsFinished(0);
				pkpmOperator.setUpdateTime(LocalDateTime.now());
				pkpmOperator.setFinishTime(LocalDateTime.now());
				pkpmOperatorStatusDAO.save(pkpmOperator);

				adService.deleteComputer(requestBean.getDesktops().get(0).getComputerName(),
						requestBean.getAdId());

					return "正在删除桌面,请稍等!";

			}

		} catch (HttpProcessException httpProcessException) {
			log.error(httpProcessException.getMessage());
		} catch (Exception exception) {
			log.error(exception.getMessage());
		}
		throw Exceptions.newBusinessException("未能删除桌面!");
	}
	
	 /**
     * 查询桌面详情
     */
    @SuppressWarnings("unused")
    @Override
    public DesktopRequest queryDesktopDetail(CommonRequestBean requestBean) {

        Integer statusCode = null;
        try {
        	
        	CommonRequestBean requestbean = commonRequestBeanBuilder.buildBeanforQueryDesktopDetail(requestBean);
        	String token = requestbean.getPkpmToken().getToken();
        	String url = requestbean.getPkpmWorkspaceUrl().getUrl()
				        			.replaceAll("\\{areaName\\}", requestbean.getPkpmWorkspaceUrl().getAreaName())
				        			.replaceAll("\\{projectId\\}", requestbean.getPkpmWorkspaceUrl().getProjectId())
				        			.replaceAll("\\{desktopId\\}", requestbean.getPkpmWorkspaceUrl().getDesktopId());
				        	
            HttpConfig config = HttpConfigBuilder.buildHttpConfig(url, token, 5, "utf-8", 10000);
            String strMyHttpResponse = HttpClientUtil.mysend(config.method(HttpMethods.GET));
            MyHttpResponse myHttpResponse = JsonUtil.deserialize(strMyHttpResponse, MyHttpResponse.class);
            statusCode = myHttpResponse.getStatusCode();
            
            if (HttpStatus.OK.value() == statusCode ) {
                String body = myHttpResponse.getBody();
                DesktopRequest desktop = JsonUtil.deserialize(body, DesktopRequest.class);
                return desktop;
            }

        } catch (HttpProcessException e) {
        	log.info(e.getMessage());
        } catch (Exception ee) {
        	log.info(ee.getMessage());
        }
        throw Exceptions.newBusinessException("未能成功查询桌面详情任务,请从新查询");
    }

	@Override
	public String changeDesktop(CommonRequestBean requestBean) {

		Integer statusCode = null;

		try {
			
			// 校验参数
			CommonRequestBeanUtil.checkCommonRequestBean(requestBean);
			CommonRequestBeanUtil.checkCommonRequestBeanForDelChgDesk(requestBean);

			commonRequestBeanBuilder.buildBeanForChangeDesktop(requestBean);
			
			String token = requestBean.getPkpmToken().getToken();
			String url = requestBean.getPkpmWorkspaceUrl().getUrl()
					.replaceAll("\\{areaName\\}", requestBean.getPkpmWorkspaceUrl().getAreaName())
					.replaceAll("\\{projectId\\}", requestBean.getPkpmWorkspaceUrl().getProjectId())
					.replaceAll("\\{desktopId\\}", requestBean.getPkpmWorkspaceUrl().getDesktopId());
			String strJson = "{\"desktop\":" + "{\"computer_name\":\""
					+ requestBean.getDesktops().get(0).getComputerName() + "\"}}";

			// 查询桌面详情
			DesktopRequest desktop = queryDesktopDetail(requestBean);
			String status = desktop.getDesktop().getStatus();
			if (!ResponseStatusEnum.ACTIVE.toString().equals(status)) {
				throw Exceptions.newBusinessException("桌面状态不为ACTIVE!");
			}

			HttpConfig config = HttpConfigBuilder.buildHttpConfig(url, strJson, token, 5, "utf-8", 10000);
			String strMyHttpResponse = HttpClientUtil.mysend(config.method(HttpMethods.PUT));
			MyHttpResponse myHttpResponse = JsonUtil.deserialize(strMyHttpResponse, MyHttpResponse.class);
			statusCode = myHttpResponse.getStatusCode();

			if (statusCode == HttpStatus.NO_CONTENT.value()) {

				// 随机生成jobId
				String jobId = StringUtil.getUUID();

				// 保存到PkpmJobStatus
				PkpmJobStatus pkpmJob = new PkpmJobStatus();
				pkpmJob.setJobId(jobId);
				pkpmJob.setCreateTime(LocalDateTime.now());
				pkpmJob.setProjectId(requestBean.getProjectId());
				PkpmProjectDef projectDef = pkpmProjectDefDAO.selectById(requestBean.getProjectId());
				pkpmJob.setWorkspaceId(projectDef.getWorkspaceId());
				pkpmJob.setStatus(JobStatusEnum.INITIAL.toString());
				pkpmJob.setOperatorType(OperatoreTypeEnum.CHANGE.toString());
				pkpmJobStatusDAO.insert(pkpmJob);

				// 保存到PkpmOperatorStatus
				PkpmOperatorStatus pkpmOperator = new PkpmOperatorStatus();
				pkpmOperator.setJobId(jobId);
				pkpmOperator.setProjectId(requestBean.getProjectId());
				pkpmOperator.setUserId(requestBean.getUserId());
				pkpmOperator.setSubsId(requestBean.getSubsId());
				pkpmOperator.setAdId(requestBean.getAdId());
				pkpmOperator.setUserName(requestBean.getUserName());
				pkpmOperator.setDesktopId(requestBean.getDesktopId());
				pkpmOperator.setComputerName(requestBean.getDesktops().get(0).getComputerName());
				pkpmOperator.setOperatorType(OperatoreTypeEnum.CHANGE.toString());
				pkpmOperator.setStatus(JobStatusEnum.CREATE.toString());
				pkpmOperator.setCreateTime(LocalDateTime.now());
				pkpmOperator.setIsFinished(0);
				pkpmOperator.setUpdateTime(LocalDateTime.now());
				pkpmOperator.setFinishTime(LocalDateTime.now());
				pkpmOperatorStatusMapper.saveOperatorStatus(pkpmOperator);
				return "桌面属性正在修改,请稍等!";
			}

		} catch (HttpProcessException httpProcessException) {
			log.error(httpProcessException.getMessage());
		} catch (Exception exception) {
			log.error(exception.getMessage());
		}
		throw Exceptions.newBusinessException("未能修改桌面!");
	}


	/**
	 * @apiNote  变更桌面规格，如2核4G切换为4核8G
	 * @author xuhe
	 * @param  requestBean
	 * @return DesktopSpecResponse(包含jobId)
	 */
	public DesktopSpecResponse changeDesktopSpec(CommonRequestBean requestBean) {

		CommonRequestBeanUtil.checkCommonRequestBeanForChgDeskSpec(requestBean);
		commonRequestBeanBuilder.buildBeanforChangeDesktopSpecs(requestBean);

		//初始化数据库插入数据
		PkpmOperatorStatus operatorStatus = new PkpmOperatorStatus();
		BeanUtil.copyPropertiesIgnoreNull(requestBean, operatorStatus);
		operatorStatus.setStatus(JobStatusEnum.INITIAL.toString());
		operatorStatus.setOperatorType(OperatoreTypeEnum.RESIZE.toString());
		PkpmOperatorStatusBeanUtil.checkNotNull(operatorStatus);

		//像数据库插入一条记录
		int result = pkpmOperatorStatusDAO.save(operatorStatus);
		log.info("变更桌面插入记录 --id={}",result);

		try {
			String token = requestBean.getPkpmToken().getToken();
			String url = requestBean.getPkpmWorkspaceUrl().getUrl();
			Header[] headers = HttpHeader.custom().contentType("application/json").other("X-Auth-Token", token).build();
			HCB hcb = HCB.custom().timeout(10000) // 超时，设置为1000时会报错
					.sslpv(SSLProtocolVersion.TLSv1_2) // 可设置ssl版本号，默认SSLv3，用于ssl，也可以调用sslpv("TLSv1.2")
					.ssl() // https，支持自定义ssl证书路径和密码
					.retry(5);
			HttpClient client = hcb.build();
			String strJson = String.format("{\"product_id\": \"%s\"}", requestBean.getHwProductId());
			HttpConfig config = HttpConfig.custom().headers(headers, true) // 设置headers，不需要时则无需设置
					.client(client).url(url).json(strJson)// 设置请求的url
					.encoding("utf-8"); // 设置请求和返回编码，默认就是Charset.defaultCharset()
			String strMyHttpResponse = HttpClientUtil.mysend(config.method(HttpMethods.POST));
			MyHttpResponse myHttpResponse = JsonUtil.deserialize(strMyHttpResponse, MyHttpResponse.class);
			int statusCode = myHttpResponse.getStatusCode();
			//返回值为200,返回结果
			if (statusCode==HttpStatus.OK.value()) {
				// 重置成功，返回jobId
				DesktopSpecResponse response =JsonUtil.deserialize(myHttpResponse.getBody(), DesktopSpecResponse.class);
				return response;
			}
			throw Exceptions.newBusinessException(myHttpResponse.getBody());
		} catch (HttpProcessException e) {
			log.error(e.getMessage());
		} catch (Exception e){
			log.error(e.getMessage());
		}
		throw Exceptions.newBusinessException("重置桌面规格失败");
	}

	@SuppressWarnings("unused")
    @Override
    public String operateDesktop(CommonRequestBean commonRequestBean) {

        Integer statusCode = null;
        String message = null;
        String jsonStr = "";
        CommonRequestBean requestBean = commonRequestBeanBuilder.buildBeanForOperateDesktop(commonRequestBean);
        
        CommonRequestBeanUtil.checkCommonRequestBeanForDesktop(requestBean);
        String type = requestBean.getDesktops().get(0).getDesktopOperatorType();
        Map<String, String> map = new HashMap<String, String>();
        if (DesktopServiceEnum.CLOSE.toString().equals(type)) {
            map.put("os-stop", null);
            message = "关机";
            jsonStr = JsonUtil.serialize(map);
        } else if (DesktopServiceEnum.REBOOT.toString().equals(type)) {
            map.put("type", "SOFT");
            Map<String, Map<String, String>> rebootMap = new HashMap<String, Map<String, String>>();
            rebootMap.put("reboot", map);
            message = "重新开机";
            jsonStr = JsonUtil.serialize(rebootMap);
        } else if (DesktopServiceEnum.BOOT.toString().equals(type)) {
            map.put("os-start", null);
            message = "开机";
            jsonStr = JsonUtil.serialize(map);
        }

        try {
        	
        	String token = requestBean.getPkpmToken().getToken();
        	String url = requestBean.getPkpmWorkspaceUrl().getUrl()
				        			.replaceAll("\\{areaName\\}", requestBean.getPkpmWorkspaceUrl().getAreaName())
				        			.replaceAll("\\{projectId\\}", requestBean.getPkpmWorkspaceUrl().getProjectId())
				        			.replaceAll("\\{desktopId\\}", requestBean.getPkpmWorkspaceUrl().getDesktopId());
        	
            HttpConfig httpConfig = HttpConfigBuilder.buildHttpConfig(url, jsonStr, token, 5, "utf-8", 10000);
            String responseStr = HttpClientUtil.mysend(httpConfig.method(HttpMethods.POST));
            MyHttpResponse myHttpResponse = JsonUtil.deserialize(responseStr, MyHttpResponse.class);
            statusCode = myHttpResponse.getStatusCode();

            if ( HttpStatus.ACCEPTED.value() == statusCode ) {

                PkpmOperatorStatus pkpmOperatorStatus = new PkpmOperatorStatus();
                BeanUtils.copyProperties(commonRequestBean, pkpmOperatorStatus);
                String jobId = StringUtil.getUUID();
                pkpmOperatorStatus.setJobId(jobId);
                pkpmOperatorStatus.setDesktopId(commonRequestBean.getDesktops().get(0).getDesktopId());
                pkpmOperatorStatus.setOperatorType(type);
                pkpmOperatorStatus.setComputerName("");
                pkpmOperatorStatus.setStatus(JobStatusEnum.INITIAL.toString());
                pkpmOperatorStatus.setCreateTime(LocalDateTime.now());
                pkpmOperatorStatus.setUpdateTime(LocalDateTime.now());
                pkpmOperatorStatus.setFinishTime(LocalDateTime.now());
                pkpmOperatorStatus.setIsFinished(0);
                pkpmOperatorStatusDAO.save(pkpmOperatorStatus);

                PkpmJobStatus pkpmJobStatus = new PkpmJobStatus();
                PkpmProjectDef projectDef = pkpmProjectDefDAO.selectById(requestBean.getProjectId());
                pkpmJobStatus.setWorkspaceId(projectDef.getWorkspaceId());
                pkpmJobStatus.setProjectId(commonRequestBean.getProjectId());
                pkpmJobStatus.setJobId(jobId);
                pkpmJobStatus.setCreateTime(LocalDateTime.now());
                pkpmJobStatus.setStatus(JobStatusEnum.INITIAL.toString());
                pkpmJobStatus.setOperatorType(type);
                pkpmJobStatusDAO.insert(pkpmJobStatus);

                return message + "中,请稍等!";
            }
        } catch (HttpProcessException httpProcessException) {
            log.error(httpProcessException.getMessage());
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
        
        throw Exceptions.newBusinessException(message + "失败,请重新尝试!");
    }

    @SuppressWarnings("unused")
    @Override
    public DesktopRequest queryDesktopListOrDetail(CommonRequestBean requestBean) {

        Integer statusCode = null;
        String message = null;
        String jsonStr = "";
        CommonRequestBean requestbean = commonRequestBeanBuilder.buildBeanForQueryqueryDesktopListOrDetail(requestBean);

        String token = requestbean.getPkpmToken().getToken();
        String url = requestbean.getPkpmWorkspaceUrl().getUrl()
				                .replaceAll("\\{projectId\\}", requestbean.getPkpmWorkspaceUrl().getProjectId())
				                .replaceAll("\\{areaName\\}", requestbean.getPkpmWorkspaceUrl().getAreaName());

        List<NameValuePair> params = new ArrayList<>();
        String limit = requestBean.getLimit();
        List<Desktop> desktops = requestBean.getDesktops();

        if (StringUtils.isNotBlank(limit)) {
            params.add(new BasicNameValuePair("limit", limit));
        }

        if (desktops != null) {
            Desktop desktop = desktops.get(0);
            String status = desktop.getStatus();
            String desktopIp = desktop.getDesktopIp();
            String userName = desktop.getUserName();
            String computerName = desktop.getComputerName();
            String marker = desktop.getMarker();

            if (StringUtils.isBlank(status)) {
                params.add(new BasicNameValuePair("status", status));
            }
            if (StringUtils.isBlank(desktopIp)) {
                params.add(new BasicNameValuePair("desktop_ip", desktopIp));
            }
            if (StringUtils.isBlank(userName)) {
                params.add(new BasicNameValuePair("user_name", userName));
            }
            if (StringUtils.isBlank(computerName)) {
                params.add(new BasicNameValuePair("computer_name", computerName));
            }
            if (StringUtils.isBlank(marker)) {
                params.add(new BasicNameValuePair("marker", marker));
            }
        }

        if (params.size() > 0) {
            try {
                url = url + "?" + EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            HttpConfig config = HttpConfigBuilder.buildHttpConfig(url, token, 5, "utf-8", 10000);
            String strMyHttpResponse = HttpClientUtil.mysend(config.method(HttpMethods.GET));
            MyHttpResponse myHttpResponse = JsonUtil.deserialize(strMyHttpResponse, MyHttpResponse.class);
            statusCode = myHttpResponse.getStatusCode();
            
            message = url.contains("detail") ? "桌面详情列表" : "桌面列表";
            
            if ( HttpStatus.OK.value() == statusCode ) {
                String body = myHttpResponse.getBody();
                DesktopRequest desktopRequest = JsonUtil.deserialize(body, DesktopRequest.class);
                return desktopRequest;
            }
        } catch (HttpProcessException e) {
            log.info(e.getMessage());
        } catch (Exception ee) {
            log.info(ee.getMessage());
        }
        throw Exceptions.newBusinessException("查询" + message + "失败,请从新查询");
    }

	@Override
	public void updateOperatorStatus(String jobId, String userName, int adId, long seconds) {
		// 根据jobId查询pkpm_operator_status(仅查询未完成的创建桌面类的任务)
		PkpmOperatorStatus operStatus = pkpmOperatorStatusDAO.selectByJobId(jobId);
		// 仅更新超过预设的扫描时间的任务
		if (null != operStatus && (operStatus.getUpdateTime().plusSeconds(seconds)).isBefore(LocalDateTime.now())) {
			// 若status为初始化或创建过程中状态，说明已进入创建桌面环节，调用异步任务查询接口
			if (JobStatusEnum.eval(operStatus.getStatus()).equals(JobStatusEnum.INITIAL)
					|| JobStatusEnum.eval(operStatus.getStatus()).equals(JobStatusEnum.CREATE)) {
				// 调用异步任务查询接口
				JobBean jobBean = queryJob(jobId);
				PkpmJobStatus jobStatus = pkpmJobStatusDAO.selectByJobId(jobId);
				switch (jobBean.getStatus()) {
				case "SUCCESS":
					// 桌面创建成功
					operStatus.setStatus(JobStatusEnum.SUCCESS.toString());
					operStatus.setDesktopId(jobBean.getSubJobs().get(0).getEntities().getDesktopId());
					operStatus.setUpdateTime(LocalDateTime.now());
					operStatus.setFinishTime(LocalDateTime.now());
					operStatus.setIsFinished(1);
					pkpmOperatorStatusDAO.update(operStatus);
					jobStatus.setStatus(JobStatusEnum.SUCCESS.toString());
					jobStatus.setExt1(jobBean.getSubJobs().get(0).getEntities().getDesktopId());
					pkpmJobStatusDAO.update(jobStatus);
					break;
				case "FAILED":
					// 桌面创建失败
					operStatus.setStatus(JobStatusEnum.FAILED.toString());
					operStatus.setUpdateTime(LocalDateTime.now());
					operStatus.setFinishTime(LocalDateTime.now());
					operStatus.setIsFinished(1);
					pkpmOperatorStatusDAO.update(operStatus);
					jobStatus.setStatus(JobStatusEnum.FAILED.toString());
					pkpmJobStatusDAO.update(jobStatus);
					break;
				default:
					// 仍在创建中，更新update_time
					operStatus.setStatus(JobStatusEnum.CREATE.toString());
					operStatus.setUpdateTime(LocalDateTime.now());
					operStatus.setIsFinished(0);
					pkpmOperatorStatusDAO.update(operStatus);
					break;
				}
			} else if (adService.checkUser(userName, adId)) {
				// 若status为AD类状态，调用查询AD域用户接口，若用户存在
				// 更新状态，AD域用户创建成功
				operStatus.setStatus(JobStatusEnum.AD_SUCCESS.toString());
				operStatus.setUpdateTime(LocalDateTime.now());
				pkpmOperatorStatusDAO.update(operStatus);
				// 调用异步任务查询接口
				JobBean jobBean = queryJob(jobId);
				PkpmJobStatus jobStatus = pkpmJobStatusDAO.selectByJobId(jobId);
				switch (jobBean.getStatus()) {
				case "SUCCESS":
					// 桌面创建成功
					operStatus.setStatus(JobStatusEnum.SUCCESS.toString());
					operStatus.setDesktopId(jobBean.getSubJobs().get(0).getEntities().getDesktopId());
					operStatus.setUpdateTime(LocalDateTime.now());
					operStatus.setFinishTime(LocalDateTime.now());
					operStatus.setIsFinished(1);
					pkpmOperatorStatusDAO.update(operStatus);
					jobStatus.setStatus(JobStatusEnum.SUCCESS.toString());
					jobStatus.setExt1(jobBean.getSubJobs().get(0).getEntities().getDesktopId());
					pkpmJobStatusDAO.update(jobStatus);
					break;
				case "FAILED":
					// 桌面创建失败
					operStatus.setStatus(JobStatusEnum.FAILED.toString());
					operStatus.setUpdateTime(LocalDateTime.now());
					operStatus.setFinishTime(LocalDateTime.now());
					operStatus.setIsFinished(1);
					pkpmOperatorStatusDAO.update(operStatus);
					jobStatus.setStatus(JobStatusEnum.FAILED.toString());
					pkpmJobStatusDAO.update(jobStatus);
					break;
				default:
					// 桌面创建进行中
					operStatus.setStatus(JobStatusEnum.CREATE.toString());
					operStatus.setUpdateTime(LocalDateTime.now());
					operStatus.setIsFinished(0);
					pkpmOperatorStatusDAO.update(operStatus);
					break;
				}
			} else {
				// 若status为AD类状态，调用查询AD域用户接口，若用户不存在
				// 更新状态，AD域用户创建失败
				operStatus.setStatus(JobStatusEnum.AD_FAILED.toString());
				operStatus.setUpdateTime(LocalDateTime.now());
				operStatus.setFinishTime(LocalDateTime.now());
				operStatus.setIsFinished(1);
				pkpmOperatorStatusDAO.update(operStatus);
			}
		} else {
			log.info("没有需要更新状态的任务！");
		}
	}
}