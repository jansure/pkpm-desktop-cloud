package com.gatewayserver.gatewayserver.service.impl;

import com.desktop.constant.DesktopStatusConstant;
import com.desktop.constant.JobStatusEnum;
import com.desktop.utils.StringUtil;
import com.desktop.utils.page.ResultObject;
import com.gatewayserver.gatewayserver.dao.PkpmOperatorStatusDAO;
import com.gatewayserver.gatewayserver.domain.CommonRequestBean;
import com.gatewayserver.gatewayserver.domain.PkpmOperatorStatus;
import com.gatewayserver.gatewayserver.service.StatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@Slf4j
public class StatusServiceImpl implements StatusService {

    @Resource
    private PkpmOperatorStatusDAO pkpmOperatorStatusDAO;

    @Override
    public ResultObject queryDesktopStatus(CommonRequestBean commonRequestBean) {
        PkpmOperatorStatus operatorStatus = new PkpmOperatorStatus();
        BeanUtils.copyProperties(commonRequestBean, operatorStatus);

        PkpmOperatorStatus pkpmOperatorStatus = pkpmOperatorStatusDAO.selectOperatorStatus(operatorStatus);
        if (pkpmOperatorStatus != null) {
            String status = pkpmOperatorStatus.getStatus();
            LocalDateTime createTime = pkpmOperatorStatus.getCreateTime();
            Double initial = DesktopStatusConstant.initial;
            if (JobStatusEnum.INITIAL.toString().equals(status)) {
                return ResultObject.success(StringUtil.double2percentage(initial));
            } else if (JobStatusEnum.CREATE.toString().equals(status)) {
                long now = System.currentTimeMillis();
                long create = Timestamp.valueOf(createTime).getTime();
                long diff = (now - create) / 1000 / 60;
                int temp = (int) diff / DesktopStatusConstant.frequency;
                int num = temp <= 6 ? temp : 6;
                return ResultObject.success(StringUtil.double2percentage(initial + num * DesktopStatusConstant.range));
            } else if (JobStatusEnum.SUCCESS.toString().equals(status)) {
                return ResultObject.success("100%");
            }
        }
        return ResultObject.failure("创建桌面失败!");

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
