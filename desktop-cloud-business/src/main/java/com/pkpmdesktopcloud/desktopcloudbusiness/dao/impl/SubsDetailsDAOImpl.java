package com.pkpmdesktopcloud.desktopcloudbusiness.dao.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pkpmdesktopcloud.desktopcloudbusiness.dao.SubsDetailsDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.mapper.SubsDetailsMapper;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.SubsDetails;

@Repository
public class SubsDetailsDAOImpl implements SubsDetailsDAO{
	
	@Autowired
	private SubsDetailsMapper subsDetailsMapper;
	
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param subsId
	 * @return  
	 * @see com.pkpmdesktopcloud.desktopcloudbusiness.dao.SubsDetailsDAO#findSubsDetailsList(java.lang.Long)  
	 */  
	@Override
	public List<SubsDetails> findSubsDetailsList(Long subsId) {
		SubsDetails subsDetails = new SubsDetails();
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
	public Integer saveSubsDetails(SubsDetails subsDetails) {
		
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
		SubsDetails subs = new SubsDetails();
		subs.setSubsId(subsId);
		List<SubsDetails> list = subsDetailsMapper.findSubsDetailsList(subs);
		
		//批量更新
		if(list != null && list.size() > 0) {
			for(SubsDetails subsDetailsItem : list) {
				
				subs.setId(subsDetailsItem.getId());
				subs.setValidTime(LocalDateTime.now());
				num += subsDetailsMapper.update(subs);
			}
		}
		
		return num;
	}

}
