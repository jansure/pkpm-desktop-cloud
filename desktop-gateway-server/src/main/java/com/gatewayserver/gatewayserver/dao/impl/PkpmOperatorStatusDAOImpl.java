package com.gatewayserver.gatewayserver.dao.impl;

import com.desktop.constant.OperatoreTypeEnum;
import com.gatewayserver.gatewayserver.dao.PkpmOperatorStatusDAO;
import com.gatewayserver.gatewayserver.dao.mapper.PkpmOperatorStatusMapper;
import com.gatewayserver.gatewayserver.domain.PkpmOperatorStatus;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class PkpmOperatorStatusDAOImpl implements PkpmOperatorStatusDAO {
    @Resource
    private PkpmOperatorStatusMapper mapper;

    @Override
    public PkpmOperatorStatus selectByJobId(String jobId) {
        PkpmOperatorStatus criteria = new PkpmOperatorStatus();
        criteria.setJobId(jobId);
        
        //屏蔽默认值
        criteria.setCreateTime(null);
        criteria.setUpdateTime(null);
        criteria.setFinishTime(null);
        
        criteria.setStatus(null);
        criteria.setDesktopId(null);;
        criteria.setComputerName(null);
        
        criteria.setUserId(null);
        criteria.setSubsId(null);
        
        List<PkpmOperatorStatus> list = mapper.select(criteria);
        if (CollectionUtils.isNotEmpty(list))
            return list.get(0);
        return null;
    }

    @Override
    public Integer update(PkpmOperatorStatus pkpmOperatorStatus) {
        return mapper.update(pkpmOperatorStatus);
    }

    @Deprecated
    @Override
    public List<PkpmOperatorStatus> selectNotFinished() {
        PkpmOperatorStatus criteria = new PkpmOperatorStatus();
        // 未完成的
        criteria.setIsFinished(0);
        // 类型：创建桌面
        criteria.setOperatorType(OperatoreTypeEnum.DESKTOP.toString());
        List<PkpmOperatorStatus> list = mapper.select(criteria);
        return list;
    }

    @Override
    public Integer save(PkpmOperatorStatus pkpmOperatorStatus) {

        return mapper.save(pkpmOperatorStatus);

    }


    /* (非 Javadoc)
     *
     *
     * @param jobId
     * @return
     * @see com.gatewayserver.gatewayserver.dao.PkpmOperatorStatusDAO#selectNotFinishedByJobId(java.lang.String)
     */
    @Override
    public PkpmOperatorStatus selectNotFinishedByJobId(String jobId) {
        PkpmOperatorStatus criteria = new PkpmOperatorStatus();
        // 未完成的
        criteria.setIsFinished(0);
        criteria.setJobId(jobId);
        
        //屏蔽默认值
        criteria.setCreateTime(null);
        criteria.setUpdateTime(null);
        criteria.setFinishTime(null);
        
        criteria.setStatus(null);
        criteria.setDesktopId(null);;
        criteria.setComputerName(null);
        
        criteria.setUserId(null);
        criteria.setSubsId(null);

        List<PkpmOperatorStatus> list = mapper.select(criteria);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }

        return null;
    }

    @Override
    public PkpmOperatorStatus selectOperatorStatus(PkpmOperatorStatus pkpmOperatorStatus) {

        PkpmOperatorStatus operatorStatus = mapper.selectOperatorStatus(pkpmOperatorStatus);
        return operatorStatus;
    }
}
