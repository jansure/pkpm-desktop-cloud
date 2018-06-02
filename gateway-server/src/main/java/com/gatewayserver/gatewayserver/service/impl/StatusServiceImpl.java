package com.gatewayserver.gatewayserver.service.impl;

import com.desktop.constant.DesktopStatusConstant;
import com.desktop.constant.JobStatusEnum;
import com.desktop.utils.JsonUtil;
import com.desktop.utils.StringUtil;
import com.desktop.utils.exception.Exceptions;
import com.desktop.utils.page.ResultObject;
import com.gateway.common.domain.CommonRequestBean;
import com.gateway.common.domain.PkpmOperatorStatus;
import com.gateway.common.domain.PkpmProjectDef;
import com.gateway.common.dto.StatusObject;
import com.gatewayserver.gatewayserver.dao.PkpmOperatorStatusDAO;
import com.gatewayserver.gatewayserver.dao.PkpmProjectDefDAO;
import com.gatewayserver.gatewayserver.service.StatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@Slf4j
@Transactional
public class StatusServiceImpl implements StatusService {

    @Resource
    private PkpmOperatorStatusDAO pkpmOperatorStatusDAO;
    @Resource
    private PkpmProjectDefDAO pkpmProjectDefDAO;
    
    @Override
    public String queryDesktopStatus(CommonRequestBean commonRequestBean) {
    	PkpmOperatorStatus operatorStatus = new PkpmOperatorStatus();
        
        BeanUtils.copyProperties(commonRequestBean, operatorStatus);
        StatusObject statusObject = new StatusObject();
        
        PkpmOperatorStatus pkpmOperatorStatus = pkpmOperatorStatusDAO.selectOperatorStatus(operatorStatus);
        if (pkpmOperatorStatus != null) {
            String status = pkpmOperatorStatus.getStatus();
            LocalDateTime createTime = pkpmOperatorStatus.getCreateTime();
            Integer initial = DesktopStatusConstant.INITIAL;
            if (JobStatusEnum.INITIAL.toString().equals(status)) {
            	
            	statusObject.setProgress(Integer.toString(initial));
            	
            } else if (JobStatusEnum.CREATE.toString().equals(status)) {
                long now = System.currentTimeMillis();
                long create = Timestamp.valueOf(createTime).getTime();
                long diff = (now - create) / 1000 / 60;
                int temp = (int) diff / DesktopStatusConstant.FREQUENCY;
                int num = temp <= 6 ? temp : 6;
                
                Integer  a = initial + num * DesktopStatusConstant.RANGE;
                statusObject.setProgress(Integer.toString(a));
            
            } else if (JobStatusEnum.SUCCESS.toString().equals(status)) {
            	
            	String projectId = commonRequestBean.getProjectId();
            	PkpmProjectDef pkpmProjectDef = pkpmProjectDefDAO.selectById(projectId);
            	String destinationIp = pkpmProjectDef.getDestinationIp();
            	
            	statusObject.setProgress(Integer.toString(100));
            	statusObject.setDestinationIp(destinationIp);
            }
            
            String strStatusObject = JsonUtil.serialize(statusObject);
        	return strStatusObject;
        }
        throw Exceptions.newBusinessException("创建桌面失败!");

    }

    @SuppressWarnings("unused")
    @Override
    public ResultObject queryOperateStatus(CommonRequestBean commonRequestBean) {
        PkpmOperatorStatus operatorStatus = new PkpmOperatorStatus();
        BeanUtils.copyProperties(commonRequestBean, operatorStatus);

        PkpmOperatorStatus pkpmOperatorStatus = pkpmOperatorStatusDAO.selectOperatorStatus(operatorStatus);
        if (pkpmOperatorStatus != null) {
            String status = pkpmOperatorStatus.getStatus();
            LocalDateTime createTime = pkpmOperatorStatus.getCreateTime();
            Double initial = 0.3;
            if (JobStatusEnum.INITIAL.toString().toString().equals(status)) {
                return ResultObject.success(StringUtil.double2percentage(initial));
            } else if (JobStatusEnum.SUCCESS.toString().equals(status)) {
                return ResultObject.success("100%");
            }
        }
        return ResultObject.failure("操作失败,请重新尝试！");

    }


}
