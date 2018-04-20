/**
 * 
 */
package com.gatewayserver.gatewayserver.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.desktop.constant.AdConstant;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.desktop.utils.exception.Exceptions;
import com.gateway.common.domain.PkpmAdDef;
import com.gateway.common.domain.PkpmProjectDef;
import com.gatewayserver.gatewayserver.dao.PkpmAdDefDAO;
import com.gatewayserver.gatewayserver.dao.PkpmProjectDefDAO;
import com.gatewayserver.gatewayserver.service.AdService;
import com.gatewayserver.gatewayserver.service.GatewayServerParam;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description 供云平台使用Service实现类
 * @author yangpengfei
 * @time 2018年4月12日 下午3:15:32
 */
@Service
@Slf4j
@Transactional
public class GatewayServerParamImpl implements GatewayServerParam {

	@Resource
	private PkpmProjectDefDAO pkpmProjectDefDAO;
	@Resource
	private PkpmAdDefDAO pkpmAdDefDAO;
	@Resource
	private AdService adService;

	@Override
	public List<PkpmProjectDef> getProjectDefs(String adIpAddress, String areaCode) {
		try {
			Preconditions.checkNotNull(areaCode, "adIpAddress不能为空");
			Preconditions.checkNotNull(areaCode, "areaCode不能为空");

			PkpmProjectDef pkpmProjectDef = new PkpmProjectDef();
			pkpmProjectDef.setAdIpAddress(adIpAddress);
			pkpmProjectDef.setAreaCode(areaCode);
			List<PkpmProjectDef> pkpmProjectList = pkpmProjectDefDAO.select(pkpmProjectDef);

			// 结果不空，返回项目列表
			if (CollectionUtils.isNotEmpty(pkpmProjectList)) {
				return pkpmProjectList;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		throw Exceptions.newBusinessException("该可用区不存在可分配的项目！");
	}

	@Override
	public List<PkpmAdDef> getAdDefs(String adIpAddress, String areaCode, String adOu, Integer isValid) {
		try {
			Preconditions.checkNotNull(areaCode, "adIpAddress不能为空");
			Preconditions.checkNotNull(areaCode, "areaCode不能为空");
			Preconditions.checkNotNull(areaCode, "adOu不能为空");
			Preconditions.checkNotNull(areaCode, "isValid不能为空");

			PkpmAdDef pkpmAdDef = new PkpmAdDef();
			pkpmAdDef.setAdIpAddress(adIpAddress);
			pkpmAdDef.setAreaCode(areaCode);
			pkpmAdDef.setAdOu(adOu);
			pkpmAdDef.setIsValid(isValid);
			List<PkpmAdDef> pkpmAdList = pkpmAdDefDAO.select(pkpmAdDef);

			// 结果不空，返回列表
			if (CollectionUtils.isNotEmpty(pkpmAdList)) {
				return pkpmAdList;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		throw Exceptions.newBusinessException("没有可分配的AD域！");
	}

	@Override
	public Map<String, String> getAdAndProject(String areaCode, String ouName) {
		try {
			Preconditions.checkNotNull(areaCode, "areaCode不能为空");
			ouName = (StringUtils.isBlank(ouName)) ? "pkpm" : ouName;
			// 查询ad_ip_address
			PkpmAdDef pkpmAdDef = new PkpmAdDef();
			pkpmAdDef.setAreaCode(areaCode);
			pkpmAdDef.setAdOu(ouName);
			pkpmAdDef.setIsValid(1);
			List<PkpmAdDef> pkpmAdList = pkpmAdDefDAO.select(pkpmAdDef);
			// 结果不空，返回adIpAddress列表
			if (CollectionUtils.isNotEmpty(pkpmAdList)) {
				// 查出已有的adIpAddress，并去重
				List<String> adIpAddressList = pkpmAdList.stream().map(PkpmAdDef::getAdIpAddress).distinct().collect(Collectors.toList());
				for (String adIpAddress : adIpAddressList) {
					// 查询此AD域已有用户数
					int count = adService.getUserCountByAdIpAddress(adIpAddress);
					// 若用户数未超过限制，返回该AD Host下的一个adId、一个projectId，放入map
					if (count < AdConstant.AD_MAX_COUNT) {
						Map<String, String> map = new HashMap<String, String>();

						// 根据adIpAddress查到AdId放入map
						List<PkpmAdDef> pkpmAdDefList = this.getAdDefs(adIpAddress, areaCode, ouName, 1);
						// 随机选择一个ad
						Random r = new Random();
						Integer adId = pkpmAdDefList.get(r.nextInt(pkpmAdDefList.size())).getAdId();
						map.put("adId", adId.toString());

						// 根据adIpAddress查到ProjectId放入map
						List<PkpmProjectDef> pkpmProjectDefList = this.getProjectDefs(adIpAddress, areaCode);
						// 随机选择一个project
						String projectId = pkpmProjectDefList.get(r.nextInt(pkpmProjectDefList.size())).getProjectId();
						map.put("projectId", projectId);

						return map;
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		throw Exceptions.newBusinessException("该区域没有可用的AD域！");
	}

}
