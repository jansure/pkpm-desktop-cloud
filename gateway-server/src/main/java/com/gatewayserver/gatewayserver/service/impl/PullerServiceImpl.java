package com.gatewayserver.gatewayserver.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.desktop.constant.DesktopServiceEnum;
import com.desktop.constant.JobStatusEnum;
import com.desktop.constant.OperatoreTypeEnum;
import com.desktop.utils.HttpConfigBuilder;
import com.desktop.utils.JsonUtil;
import com.desktop.utils.MyBeanUtil;
import com.desktop.utils.exception.Exceptions;
import com.desktop.utils.page.PageUtils;
import com.gateway.common.domain.PkpmJobStatus;
import com.gateway.common.domain.PkpmOperatorStatus;
import com.gateway.common.domain.PkpmOperatorStatusHistory;
import com.gateway.common.domain.PkpmProjectDef;
import com.gateway.common.domain.PkpmPullerConfig;
import com.gateway.common.domain.PkpmWorkspaceUrl;
import com.gatewayserver.gatewayserver.dao.PkpmJobStatusDAO;
import com.gatewayserver.gatewayserver.dao.PkpmOperatorStatusDAO;
import com.gatewayserver.gatewayserver.dao.PkpmOperatorStatusHistoryDAO;
import com.gatewayserver.gatewayserver.dao.PkpmProjectDefDAO;
import com.gatewayserver.gatewayserver.dao.PkpmPullerConfigDAO;
import com.gatewayserver.gatewayserver.dao.PkpmWorkspaceUrlDAO;
import com.gatewayserver.gatewayserver.service.DesktopService;
import com.gatewayserver.gatewayserver.service.PullerService;
import com.gatewayserver.gatewayserver.utils.CommonRequestBeanBuilder;
import com.google.common.base.Preconditions;
import com.pkpm.httpclientutil.HttpClientUtil;
import com.pkpm.httpclientutil.HuaWeiResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wangxiulong
 * @ClassName: PullerServiceImpl
 * @Description: 更新状态Service实现类
 * @date 2018年4月8日
 */
@Service
@Slf4j
@Transactional
public class PullerServiceImpl implements PullerService {
	
    @Resource
    private PkpmJobStatusDAO pkpmJobStatusDAO;

    @Resource
    private PkpmOperatorStatusDAO pkpmOperatorStatusDAO;

    @Resource
    private PkpmOperatorStatusHistoryDAO pkpmOperatorStatusHistoryDAO;

    @Resource
    private PkpmPullerConfigDAO pkpmPullerConfigDAO;

    @Resource
    private PkpmProjectDefDAO pkpmProjectDefDAO;

    @Resource
    private PkpmWorkspaceUrlDAO pkpmWorkspaceUrlDAO;

    @Resource
    private DesktopService desktopService;

    @Resource
    private CommonRequestBeanBuilder commonRequestBeanBuilder;

    /* (非 Javadoc)
     *
     *
     * @return
     * @see com.gatewayserver.gatewayserver.service.IPullerService#getJobTasks()
     */
    @Override
    public List<String> getJobTasks(int jobSize, String areaCode) {

    	if(jobSize < 1 || jobSize > 30) {//范围控制
    		jobSize = 20;
    	}
        //获取所有的未更新的任务
        PkpmJobStatus pkpmJob = new PkpmJobStatus();
        
    	//fixme for test,modify
        if(StringUtils.isNotEmpty(areaCode)) {
        	pkpmJob.setAreaCode(areaCode);
		}
    	
        pkpmJob.setStatus(JobStatusEnum.INITIAL.toString());
        pkpmJob.setPage(PageUtils.getBeginPos(1, jobSize));
        pkpmJob.setPageSize(jobSize);
        try {
        	
        	// 加入分页
        	List<PkpmJobStatus> jobList = pkpmJobStatusDAO.selectByPage(pkpmJob);
            List<String> jobIds = jobList.stream().map(PkpmJobStatus::getJobId).collect(Collectors.toList());
            if(jobIds != null && jobIds.size() > 0) {
            	return jobIds;
            }

        }catch(Exception ex) {
        	log.error(ex.getMessage());
        	ex.printStackTrace();
        	throw Exceptions.newBusinessException("获取任务出错");
        }

        throw Exceptions.newBusinessException("没有要获取状态的任务");

    }


    /* (非 Javadoc)
     *
     *
     * @param jobId
     * @return
     * @see com.gatewayserver.gatewayserver.service.IPullerService#getJobDetail(java.lang.String)
     */
    @Override
    public PkpmOperatorStatus getJobDetail(String jobId) {
    	try {
    		
	        PkpmOperatorStatus operStatus = pkpmOperatorStatusDAO.selectNotFinishedByJobId(jobId);
	        return operStatus;
    	}catch(Exception ex) {
    		
        	log.error(ex.getMessage());
        	throw Exceptions.newBusinessException("获取任务详情出错");
        }
    }


    /* (非 Javadoc)
     *
     *
     * @return
     * @see com.gatewayserver.gatewayserver.service.IPullerService#getConfig()
     */
    @Override
    public List<PkpmPullerConfig> getConfig() {
    	try {
    		
    		return pkpmPullerConfigDAO.selectAll();
    	}catch(Exception ex) {
    		
        	log.error(ex.getMessage());
        	throw Exceptions.newBusinessException("获取任务扫描时间配置信息出错！");
        }
        
    }


    /* (非 Javadoc)
     *
     *
     * @param jobId
     * @param status
     * @return
     * @see com.gatewayserver.gatewayserver.service.IPullerService#updateJobTask(java.lang.String, java.lang.String)
     */
    @Override
    public int updateJobTask(String jobId, String status) {

        PkpmJobStatus pkpmJob = new PkpmJobStatus();
        pkpmJob.setJobId(jobId);
        pkpmJob.setStatus(status);

        try {
    		
        	return pkpmJobStatusDAO.update(pkpmJob);
    	}catch(Exception ex) {
    		
        	log.error(ex.getMessage());
        	throw Exceptions.newBusinessException("更新任务数据出错！");
        }
        
    }



    /* (非 Javadoc)
     *
     *
     * @param jobId
     * @param projectId
     * @param operatorType
     * @return
     * @see com.gatewayserver.gatewayserver.service.IPullerService#getHuaWeiInfo(java.lang.String, java.lang.String, java.lang.String)
     */

    @Override
    public HuaWeiResponse getHuaWeiInfo(String jobId, String projectId, String operatorType) {

        //获取token
        String token = null;
        String url = null;

        try {
            token = desktopService.createToken(projectId);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw Exceptions.newBusinessException("获取Token失败！");
        }
        
        //根据类型调用不同接口
        //操作类型（DESKTOP:创建桌面,DELETE::删除桌面,CHANGE:修改桌面属性,RESIZE:变更桌面规格,BOOT:启动桌面,REBOOT:重启桌面,CLOSE:关闭桌面）
        if (operatorType.equals(OperatoreTypeEnum.DESKTOP.toString())
                || operatorType.equals(OperatoreTypeEnum.RESIZE.toString())) {//调用异步查询接口
            Map<String, String> urlMap = getDesktopUrl(projectId);

            //处理URL参数
            if (urlMap != null) {

                String areaName = urlMap.get("areaName");
                url = urlMap.get("url");
                url = url.replaceAll("\\{areaName\\}", areaName)
                        .replaceAll("\\{projectId\\}", projectId)
                        .replaceAll("\\{jobId\\}", jobId);
            }

        } else if (operatorType.equals(OperatoreTypeEnum.DELETE.toString())
                || operatorType.equals(OperatoreTypeEnum.CHANGE.toString())
                || operatorType.equals(OperatoreTypeEnum.BOOT.toString())
                || operatorType.equals(OperatoreTypeEnum.REBOOT.toString())
                || operatorType.equals(OperatoreTypeEnum.CLOSE.toString())) {//调用桌面属性查询接口

            Map<String, String> detailMap = getDesktopDetail(projectId, jobId);
            //处理URL参数
            if (detailMap != null) {

                String areaName = detailMap.get("areaName");
                String desktopId = detailMap.get("desktopId");
                url = detailMap.get("url");
                url = url.replaceAll("\\{areaName\\}", areaName)
                        .replaceAll("\\{projectId\\}", projectId)
                        .replaceAll("\\{desktopId\\}", desktopId);
            }

        }

        Preconditions.checkNotNull(url, "没有获取URL信息！");
           
        return  getHuWeiInfoByUrl(url, token);

    }

    /**
     * @param url
     * @param token 认证信息
     * @return JobBean    返回状态数据
     * @throws
     * @Title: getHuWeiInfoByUrl
     * @Description: 通过URL获取华为数据
     */
    private HuaWeiResponse getHuWeiInfoByUrl(String url, String token) {

        try {
            String response = HttpClientUtil.get(HttpConfigBuilder.buildHttpConfig(url, token, 5, "utf-8", 10000));
            log.info("response:{}", response);

            return JsonUtil.deserialize(response, HuaWeiResponse.class);

        } catch (Exception exception) {
            log.error("获取为返回结果错误！");
            throw Exceptions.newBusinessException(exception.getMessage());
        }

    }


    /**
     * @param projectId 项目ID
     * @return String    返回url和areaName
     * @throws
     * @Title: getDesktopUrl
     * @Description: 获取创建桌面查询URL
     */
    private Map<String, String> getDesktopUrl(String projectId) {

    	try {
    		PkpmProjectDef projectDef = pkpmProjectDefDAO.selectById(projectId);
            if (projectDef != null) {

                String areaName = projectDef.getAreaName();
                // pkpmWorkspaceUrl详情信息
                PkpmWorkspaceUrl pkpmWorkspaceUrl = pkpmWorkspaceUrlDAO.selectByPriKey(projectId, areaName, DesktopServiceEnum.LIST_JOB.toString());
                if (pkpmWorkspaceUrl != null) {
                	
                	Map<String, String> resultMap = new HashMap<String, String>();
                    resultMap.put("url", pkpmWorkspaceUrl.getUrl());
                    resultMap.put("areaName", areaName);
                    return resultMap;
                } else {
                    log.error("异步Url获取失败！项目ID：{}", projectId);
                }

            } else {
                log.error("项目信息获取失败！项目ID：{}", projectId);
            }
    	}catch(Exception ex) {
    		 log.error(ex.getMessage());
    	}
    	
        
        throw Exceptions.newBusinessException("获取异步查询URL失败！");
    }

    /**
     * @param projectId
     * @param jobId
     * @return Map<String   ,   String>  返回url、areaName和desktopId
     * @throws
     * @Title: getDesktopDetail
     * @Description: 获取桌面详情
     */
    private Map<String, String> getDesktopDetail(String projectId, String jobId) {

    	try {
	    	PkpmProjectDef projectDef = pkpmProjectDefDAO.selectById(projectId);
	        if (projectDef != null) {
	
	            String areaName = projectDef.getAreaName();
	            // pkpmWorkspaceUrl详情信息
	            PkpmWorkspaceUrl pkpmWorkspaceUrl = pkpmWorkspaceUrlDAO.selectByPriKey(projectId, areaName, DesktopServiceEnum.DETAIL.toString());
	            PkpmOperatorStatus operatorStatus = pkpmOperatorStatusDAO.selectNotFinishedByJobId(jobId);
	            if (pkpmWorkspaceUrl != null && operatorStatus != null) {
	            	
	            	Map<String, String> resultMap = new HashMap<String, String>();
	                resultMap.put("desktopId", operatorStatus.getDesktopId());
	                resultMap.put("url", pkpmWorkspaceUrl.getUrl());
	                resultMap.put("areaName", areaName);
	                return resultMap;
	            } else {
	                log.error("Url和desktopId获取失败！项目ID：{},任务Id:{}", projectId, jobId);
	            }
	
	        } else {
	            log.error("项目信息获取失败！项目ID：{}", projectId);
	        }
    	}catch(Exception ex) {
    		log.error(ex.getMessage());
    	}
       
        throw Exceptions.newBusinessException("获取桌面属性URL失败！");
    }

    /* (非 Javadoc)
     *
     *
     * @param jobId
     * @param status
     * @return
     * @see com.gatewayserver.gatewayserver.service.PullerService#updateJobDetail(java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public int updateJobDetail(String jobId, String status) {
        int num = 0;
        
        try {
	        PkpmOperatorStatus oldOperatorStatus = pkpmOperatorStatusDAO.selectByJobId(jobId);
	        Preconditions.checkNotNull(oldOperatorStatus, "没有获取到任务详情信息！");
	
	        //判断是否结束
	        boolean isFinished = false;
	        if (status.equals(JobStatusEnum.SUCCESS.toString())
	                || status.equals(JobStatusEnum.FAILED.toString())) {
	            isFinished = true;
	        }
	
	        //更新任务详情
	        PkpmOperatorStatus pkpmOperatorStatus = new PkpmOperatorStatus();
	        
	        //设置对象属性为空
//	        pkpmOperatorStatus = new MyBeanUtil<PkpmOperatorStatus>().setPropertyNull(pkpmOperatorStatus);
	        
	        pkpmOperatorStatus.setId(oldOperatorStatus.getId());
	        pkpmOperatorStatus.setStatus(status);
	        pkpmOperatorStatus.setUpdateTime(LocalDateTime.now());
	
	        if (isFinished) {
	            pkpmOperatorStatus.setIsFinished(1);//设置已完成
	            pkpmOperatorStatus.setFinishTime(LocalDateTime.now());
	        }
	        num = pkpmOperatorStatusDAO.update(pkpmOperatorStatus);
	
	        //保存更新日志
	        PkpmOperatorStatusHistory operatorStatusHistory = new PkpmOperatorStatusHistory();
	        BeanUtils.copyProperties(oldOperatorStatus, operatorStatusHistory);
	
	        operatorStatusHistory.setId(null);
	        operatorStatusHistory.setStatus(pkpmOperatorStatus.getStatus());
	        operatorStatusHistory.setCreateTime(pkpmOperatorStatus.getUpdateTime());
	        operatorStatusHistory.setUpdateTime(pkpmOperatorStatus.getUpdateTime());
	        operatorStatusHistory.setFinishTime(pkpmOperatorStatus.getUpdateTime());
	
	        if (isFinished) {
	            operatorStatusHistory.setIsFinished(1);
	        }
	        pkpmOperatorStatusHistoryDAO.save(operatorStatusHistory);
        }catch(Exception ex) {
        	log.error("更新任务详情、日志失败！");
            throw Exceptions.newBusinessException(ex.getMessage());
        }
        return num;
    }

}