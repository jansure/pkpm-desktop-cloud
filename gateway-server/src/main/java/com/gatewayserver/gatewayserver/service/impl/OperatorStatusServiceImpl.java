package com.gatewayserver.gatewayserver.service.impl;

import com.desktop.constant.JobStatusEnum;
import com.desktop.constant.OperatoreTypeEnum;
import com.gateway.common.domain.PkpmOperatorStatus;
import com.gatewayserver.gatewayserver.dao.PkpmOperatorStatusDAO;
import com.gatewayserver.gatewayserver.service.IOperatorStatusService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OperatorStatusServiceImpl  implements IOperatorStatusService {

    @Resource
    private PkpmOperatorStatusDAO  pkpmOperatorStatusDAO;

    @Override
    public String queryComputerName(String desktopId) {

        PkpmOperatorStatus pkpmOperatorStatus = new PkpmOperatorStatus();
        pkpmOperatorStatus.setDesktopId(desktopId);
        pkpmOperatorStatus.setOperatorType(OperatoreTypeEnum.DESKTOP.toString());
        pkpmOperatorStatus.setStatus(JobStatusEnum.SUCCESS.toString());

        PkpmOperatorStatus operatorStatus = pkpmOperatorStatusDAO.selectOperatorStatus(pkpmOperatorStatus);
        if(operatorStatus != null){
            return operatorStatus.getComputerName();
        }
        return null;
    }
}
