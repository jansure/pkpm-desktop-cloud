package com.gatewayserver.gatewayserver.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.gatewayserver.gatewayserver.dao.PkpmJobStatusDAO;
import com.gatewayserver.gatewayserver.dao.mapper.PkpmJobStatusMapper;
import com.gatewayserver.gatewayserver.domain.PkpmJobStatus;
import com.github.pagehelper.PageHelper;

/**
 * @Description:
 * @Author: yangpengfei
 * @Date: 2018/3/27
 */
@Repository
public class PkpmJobStatusDAOImpl implements PkpmJobStatusDAO {

    @Resource
    private PkpmJobStatusMapper mapper;

    @Override
    public PkpmJobStatus selectByJobId(String jobId) {
        PkpmJobStatus pkpmJob = new PkpmJobStatus();
        pkpmJob.setJobId(jobId);
        List<PkpmJobStatus> list = mapper.select(pkpmJob);
        if (CollectionUtils.isNotEmpty(list))
            return list.get(0);
        return null;
    }

    @Override
    public Integer update(PkpmJobStatus pkpmJob) {
        return mapper.update(pkpmJob);
    }

    @Override
    public Integer insert(PkpmJobStatus pkpmJob) {
        return mapper.insert(pkpmJob);
    }


    /* (非 Javadoc)
     *
     *
     * @param pkpmJob
     * @return
     * @see com.gatewayserver.gatewayserver.dao.PkpmJobStatusDAO#select(com.gatewayserver.gatewayserver.domain.PkpmJobStatus)
     */

    @Override
    public List<PkpmJobStatus> select(PkpmJobStatus pkpmJob) {
        return mapper.select(pkpmJob);
    }

	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param pkpmJob
	 * @param pageNo
	 * @param pageSize
	 * @return  
	 * @see com.gatewayserver.gatewayserver.dao.PkpmJobStatusDAO#selectByPage(com.gatewayserver.gatewayserver.domain.PkpmJobStatus, int, int)  
	 */  
	@Override
	public List<PkpmJobStatus> selectByPage(PkpmJobStatus pkpmJob, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		return mapper.select(pkpmJob);
	}
}
