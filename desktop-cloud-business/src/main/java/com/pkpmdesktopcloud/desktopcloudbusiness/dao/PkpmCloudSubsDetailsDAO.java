package com.pkpmdesktopcloud.desktopcloudbusiness.dao;

import java.util.List;

import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudSubsDetails;

public interface PkpmCloudSubsDetailsDAO {
	/**
	 * 根据订单id获取订单明细（订购时间，失效时间）
	 * @param productId 商品id
	 * @return
	 * 
	 */
	List<PkpmCloudSubsDetails> findSubsDetailsList(Long subsId);

	//Integer saveSubsDetailsVO(List<SubsDetailsVO> list);

	Integer saveSubsDetails(PkpmCloudSubsDetails subsDetails);
	
	Integer updateSubsDetailsStatus(Long subsId);

}
