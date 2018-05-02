package com.pkpmdesktopcloud.desktopcloudbusiness.dao.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pkpmdesktopcloud.desktopcloudbusiness.dao.PkpmCloudSubsDetailsDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.mapper.PkpmCloudSubsDetailsMapper;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudSubsDetails;

@Repository
public class PkpmCloudSubsDetailsDAOImpl implements PkpmCloudSubsDetailsDAO{
	
	@Autowired
	private PkpmCloudSubsDetailsMapper subsDetailsMapper;
	
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param subsId
	 * @return  
	 * @see com.pkpmdesktopcloud.desktopcloudbusiness.dao.SubsDetailsDAO#findSubsDetailsList(java.lang.Long)  
	 */  
	@Override
	public List<PkpmCloudSubsDetails> findSubsDetailsList(Long subsId) {
		PkpmCloudSubsDetails subsDetails = new PkpmCloudSubsDetails();
		subsDetails.setSubsId(subsId);
		
		return subsDetailsMapper.findSubsDetailsList(subsDetails );
		
	}

	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param subsDetails
	 * @return  
	 * @see com.pkpmdesktopcloud.desktopcloudbusiness.dao.SubsDetailsDAO#saveSubsDetails(com.pkpmdesktopcloud.desktopcloudbusiness.domain.SubsDetails)  
	 */  
	@Override
	public Integer saveSubsDetails(PkpmCloudSubsDetails subsDetails) {
		
		subsDetails.setId(null);
		return subsDetailsMapper.insert(subsDetails);
	}

	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param workId
	 * @return  
	 * @see com.pkpmdesktopcloud.desktopcloudbusiness.dao.SubsDetailsDAO#updateSubsDetailsStatus(java.lang.Long)  
	 */  
	@Override
	public Integer updateSubsDetailsStatus(Long subsId) {
		int num = 0;
		
		//先获取相关信息
		PkpmCloudSubsDetails subs = new PkpmCloudSubsDetails();
		subs.setSubsId(subsId);
		List<PkpmCloudSubsDetails> list = subsDetailsMapper.findSubsDetailsList(subs);
		
		//批量更新
		if(list != null && list.size() > 0) {
			for(PkpmCloudSubsDetails subsDetailsItem : list) {
				
				subs.setId(subsDetailsItem.getId());
				subs.setValidTime(LocalDateTime.now());
				num += subsDetailsMapper.update(subs);
			}
		}
		
		return num;
	}

}
