package com.pkpmdesktopcloud.desktopcloudbusiness.dao;

import java.util.List;

import com.pkpmdesktopcloud.desktopcloudbusiness.domain.SubsDetails;

public interface SubsDetailsDAO {
	/**
	 * 根据订单id获取订单明细（订购时间，失效时间）
	 * @param subsId
	 * @return
	 * 
	 */
	List<SubsDetails> findSubsDetailsList(Long subsId);

	//Integer saveSubsDetailsVO(List<SubsDetailsVO> list);

	Integer saveSubsDetails(SubsDetails subsDetails);
	
	Integer updateSubsDetailsStatus(Long subsId);

}
