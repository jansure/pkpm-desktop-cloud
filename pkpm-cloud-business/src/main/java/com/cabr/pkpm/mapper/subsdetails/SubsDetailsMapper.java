package com.cabr.pkpm.mapper.subsdetails;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cabr.pkpm.entity.subsdetails.SubsDetails;
import com.cabr.pkpm.entity.subsdetails.SubsDetailsVO;

@Mapper
public interface SubsDetailsMapper {
	/**
	 * 根据订单id获取订单明细（订购时间，失效时间）
	 * @param productId 商品id
	 * @return
	 * 
	 */
	List<SubsDetails> findSubsDetailsList(Long subsId);

	//Integer saveSubsDetailsVO(List<SubsDetailsVO> list);

	Integer saveSubsDetails(SubsDetails subsDetails);
	
	Integer updateSubsDetailsStatus(Long workId);

}
