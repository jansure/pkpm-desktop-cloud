package com.gatewayserver.gatewayserver.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.desktop.constant.DesktopConstant;
import com.desktop.constant.DesktopServiceEnum;
import com.desktop.constant.VolumeTypeEnum;
import com.gateway.common.domain.CommonRequestBean;
import com.gateway.common.domain.PkpmJobStatus;
import com.gateway.common.domain.PkpmProjectDef;
import com.gateway.common.domain.PkpmToken;
import com.gateway.common.domain.PkpmWorkspaceDef;
import com.gateway.common.domain.PkpmWorkspaceUrl;
import com.gateway.common.dto.Auth;
import com.gateway.common.dto.DataVolume;
import com.gateway.common.dto.Desktop;
import com.gateway.common.dto.Domain;
import com.gateway.common.dto.Identity;
import com.gateway.common.dto.Password;
import com.gateway.common.dto.Project;
import com.gateway.common.dto.RootVolume;
import com.gateway.common.dto.Scope;
import com.gateway.common.dto.User;
import com.gatewayserver.gatewayserver.dao.PkpmJobStatusDAO;
import com.gatewayserver.gatewayserver.dao.PkpmProjectDefDAO;
import com.gatewayserver.gatewayserver.dao.PkpmWorkspaceDefDAO;
import com.gatewayserver.gatewayserver.dao.PkpmWorkspaceUrlDAO;
import com.gatewayserver.gatewayserver.service.DesktopService;
import com.google.common.base.Preconditions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CommonRequestBeanBuilder {
	@Resource
	private PkpmProjectDefDAO pkpmProjectDefDAO;
	@Resource
	private PkpmWorkspaceDefDAO pkpmWorkspaceDefDAO;
	@Resource
	private PkpmWorkspaceUrlDAO pkpmWorkspaceUrlDAO;
	@Resource
	private PkpmJobStatusDAO pkpmJobStatusDAO;
	@Autowired
	private DesktopService workspaceService;

	public CommonRequestBean buildBeanForToken(String projectId) {
		CommonRequestBean commonReq = new CommonRequestBean();
		// 组装成CommonRequestBean
		PkpmProjectDef projectDef = pkpmProjectDefDAO.selectById(projectId);
		if (projectDef != null) {
			String areaName = projectDef.getAreaName();
			// pkpmWorkspaceUrl配置
			PkpmWorkspaceUrl pkpmWorkspaceUrl = pkpmWorkspaceUrlDAO.selectByPriKey(projectId, areaName,
					DesktopServiceEnum.TOKEN.toString());
			commonReq.setPkpmWorkspaceUrl(pkpmWorkspaceUrl);
		}
		PkpmWorkspaceDef workspaceDef = pkpmWorkspaceDefDAO.selectById(projectDef.getWorkspaceId());
		// auth配置
		if (workspaceDef != null) {
			Domain domain = new Domain();
			domain.setName(workspaceDef.getAdminName());
			User user = new User();
			user.setDomain(domain);
			user.setName(workspaceDef.getAdminName());
			user.setPassword(workspaceDef.getAdminPassword());
			Password password = new Password();
			password.setUser(user);
			Identity identity = new Identity();
			identity.setPassword(password);
			identity.setMethods(new ArrayList<>(Arrays.asList("password")));
			Project project = new Project();
			project.setId(projectId);
			Scope scope = new Scope();
			scope.setProject(project);
			Auth auth = new Auth();
			auth.setIdentity(identity);
			auth.setScope(scope);
			commonReq.setAuth(auth);
		}
		return commonReq;
	}

	public CommonRequestBean buildBeanForCreateDesktop(CommonRequestBean commonRequestBean) {
		// 校验commonRequestBean内容
		CommonRequestBeanUtil.checkDesktopInputForCreate(commonRequestBean);
		// 组装成CommonRequestBean
		// pkpmToken配置
		PkpmToken pkpmToken = new PkpmToken();
		pkpmToken.setToken(workspaceService.createToken(commonRequestBean.getProjectId()));
		System.out.println("========"+commonRequestBean.getProjectId());
		commonRequestBean.setPkpmToken(pkpmToken);
		PkpmProjectDef projectDef = pkpmProjectDefDAO.selectById(commonRequestBean.getProjectId());
		if (projectDef != null) {
			String areaName = projectDef.getAreaName();
			// pkpmWorkspaceUrl配置
			PkpmWorkspaceUrl pkpmWorkspaceUrl = pkpmWorkspaceUrlDAO.selectByPriKey(commonRequestBean.getProjectId(),
					areaName, DesktopServiceEnum.CREATE.toString());
			commonRequestBean.setPkpmWorkspaceUrl(pkpmWorkspaceUrl);
		}
		// desktop配置
		RootVolume rootVolume = new RootVolume();
		rootVolume.setType(VolumeTypeEnum.SATA.toString());
		rootVolume.setSize(80);
		Desktop desktop = new Desktop();
		desktop.setUserName(commonRequestBean.getUserName());
		desktop.setUserGroup("USERS");
		desktop.setUserEmail(commonRequestBean.getUserEmail());
		desktop.setProductId(commonRequestBean.getHwProductId());
		// 有私有镜像后需设置ImageId
		desktop.setImageId(commonRequestBean.getImageId());
		desktop.setComputerName(commonRequestBean.getGloryProductName());
		desktop.setRootVolume(rootVolume);
		DataVolume dataVolume = new DataVolume();
		dataVolume.setType(VolumeTypeEnum.SATA.toString());
		dataVolume.setSize(commonRequestBean.getDataVolumeSize());
		desktop.setDataVolumes(new ArrayList<>(Arrays.asList(dataVolume)));
		desktop.setOuName(StringUtils.isBlank(commonRequestBean.getOuName()) ? "pkpm" : commonRequestBean.getOuName());
		commonRequestBean.setDesktops(new ArrayList<>(Arrays.asList(desktop)));

		return commonRequestBean;
	}

	public CommonRequestBean buildBeanForJob(String jobId) {
		CommonRequestBean commonReq = new CommonRequestBean();
		// 组装成CommonRequestBean
		PkpmJobStatus pkpmJob = pkpmJobStatusDAO.selectByJobId(jobId);
		if (pkpmJob != null) {
			// pkpmToken配置
			PkpmToken pkpmToken = new PkpmToken();
			pkpmToken.setToken(workspaceService.createToken(pkpmJob.getProjectId()));
			commonReq.setPkpmToken(pkpmToken);
			PkpmProjectDef projectDef = pkpmProjectDefDAO.selectById(pkpmJob.getProjectId());
			if (projectDef != null) {
				String areaName = projectDef.getAreaName();
				// pkpmWorkspaceUrl配置
				PkpmWorkspaceUrl pkpmWorkspaceUrl = pkpmWorkspaceUrlDAO.selectByPriKey(pkpmJob.getProjectId(), areaName,
						DesktopServiceEnum.LIST_JOB.toString());
				pkpmWorkspaceUrl.setJobId(jobId);
				commonReq.setPkpmWorkspaceUrl(pkpmWorkspaceUrl);
			}
		}
		return commonReq;
	}

	public CommonRequestBean buildBeanForChangeDesktop(CommonRequestBean requestBean) {

		// 组装成CommonRequestBean
		Preconditions.checkArgument(StringUtils.isNotBlank(requestBean.getDesktops().get(0).getComputerName()),
				"computerName不能为空");

		// pkpmToken配置
		PkpmToken pkpmToken = new PkpmToken();
		pkpmToken.setToken(workspaceService.createToken(requestBean.getProjectId()));
		requestBean.setPkpmToken(pkpmToken);

		// pkpmWorkspaceUrl配置
		PkpmProjectDef projectDef = pkpmProjectDefDAO.selectById(requestBean.getProjectId());
		Preconditions.checkNotNull(projectDef, "projectDef不能为空");

		String areaName = projectDef.getAreaName();
		Preconditions.checkNotNull(areaName, "areaName不能为空");

		// pkpmWorkspaceUrl配置
		PkpmWorkspaceUrl pkpmWorkspaceUrl = pkpmWorkspaceUrlDAO.selectByPriKey(requestBean.getProjectId(), areaName,
				DesktopServiceEnum.MODIFY.toString());
		pkpmWorkspaceUrl.setDesktopId(requestBean.getDesktops().get(0).getDesktopId());
		requestBean.setPkpmWorkspaceUrl(pkpmWorkspaceUrl);

		return requestBean;
	}

	public CommonRequestBean buildBeanForDeleteDesktop(CommonRequestBean requestBean) {

		String projectId = requestBean.getProjectId();
		String desktopId = requestBean.getDesktops().get(0).getDesktopId();

		// 组装成CommonRequestBean
		// pkpmToken配置
		PkpmToken pkpmToken = new PkpmToken();
		pkpmToken.setToken(workspaceService.createToken(projectId));
		requestBean.setPkpmToken(pkpmToken);

		// pkpmWorkspaceUrl配置
		PkpmProjectDef projectDef = pkpmProjectDefDAO.selectById(projectId);
		Preconditions.checkNotNull(projectDef, "projectDef不能为空");

		String areaName = projectDef.getAreaName();
		Preconditions.checkNotNull(areaName, "areaName不能为空");

		// pkpmWorkspaceUrl配置
		PkpmWorkspaceUrl pkpmWorkspaceUrl = pkpmWorkspaceUrlDAO.selectByPriKey(projectId, areaName,
				DesktopServiceEnum.DELETE.toString());
		pkpmWorkspaceUrl.setDesktopId(desktopId);
		requestBean.setPkpmWorkspaceUrl(pkpmWorkspaceUrl);

		return requestBean;
	}

	/**
	 * @Description: 变更桌面规格
	 * @Author xuhe
	 * @Date 2018/4/4
	 */
	public CommonRequestBean buildBeanforChangeDesktopSpecs(CommonRequestBean requestBean) {

		// 获取Token
		String projectId = requestBean.getProjectId();
		PkpmToken token = new PkpmToken();
		token.setToken(workspaceService.createToken(projectId));
		requestBean.setPkpmToken(token);

		// 组装重置桌面Url
		PkpmProjectDef projectDef = pkpmProjectDefDAO.selectById(projectId);
		if (projectDef != null) {
			String areaName = projectDef.getAreaName();
			Preconditions.checkNotNull(StringUtils.isNotBlank(areaName), "areaName不能为空");
			//读取URL拼装信息，拼装URL
			PkpmWorkspaceUrl pkpmWorkspaceUrl = pkpmWorkspaceUrlDAO.selectByPriKey(projectId, areaName, DesktopServiceEnum.CHANGE_SPECS.toString());
			pkpmWorkspaceUrl.setDesktopId(requestBean.getDesktops().get(0).getDesktopId());
			pkpmWorkspaceUrl.setUrl(pkpmWorkspaceUrl.getUrl()
					.replaceAll("\\{projectId\\}", pkpmWorkspaceUrl.getProjectId())
					.replaceAll("\\{areaName\\}", pkpmWorkspaceUrl.getAreaName())
					.replaceAll("\\{desktopId\\}", pkpmWorkspaceUrl.getDesktopId()));
			log.info(pkpmWorkspaceUrl.getUrl());
			requestBean.setPkpmWorkspaceUrl(pkpmWorkspaceUrl);
		}
		return requestBean;

	}

	public CommonRequestBean buildQueryUserLoginRecord(CommonRequestBean commonRequestBean) {

		CommonRequestBean commonReq = new CommonRequestBean();
		PkpmToken pkpmToken = new PkpmToken();
		String projectId = commonRequestBean.getProjectId();
		pkpmToken.setToken(workspaceService.createToken(projectId));
		commonReq.setPkpmToken(pkpmToken);

        PkpmProjectDef projectDef = pkpmProjectDefDAO.selectById(projectId);
        Preconditions.checkNotNull(projectDef, "projectDef对象不能为空");
        if (projectDef != null) {
            String areaName = projectDef.getAreaName();
            Preconditions.checkNotNull(StringUtils.isNotBlank(areaName), "areaName不能为空");
            PkpmWorkspaceUrl pkpmWorkspaceUrl = pkpmWorkspaceUrlDAO.selectByPriKey(projectId, areaName, DesktopServiceEnum.LOGIN_RECORD.toString());
            commonReq.setPkpmWorkspaceUrl(pkpmWorkspaceUrl);
        }
        return commonReq;
    }

    public CommonRequestBean buildQueryDesktopUserList(CommonRequestBean info) {
        CommonRequestBean commonReq = new CommonRequestBean();
        PkpmToken pkpmToken = new PkpmToken();
        String projectId = info.getProjectId();
        pkpmToken.setToken(workspaceService.createToken(projectId));
        commonReq.setPkpmToken(pkpmToken);

        PkpmProjectDef projectDef = pkpmProjectDefDAO.selectById(projectId);
        Preconditions.checkNotNull(projectDef, "projectDef对象不能为空");
        if (projectDef != null) {
            String areaName = projectDef.getAreaName();
            Preconditions.checkNotNull(StringUtils.isNotBlank(areaName), "areaName不能为空");
            //读取URL拼装信息，拼装URL
            PkpmWorkspaceUrl pkpmWorkspaceUrl = pkpmWorkspaceUrlDAO.selectByPriKey(projectId, areaName, DesktopServiceEnum.USER_LIST.toString());
            commonReq.setPkpmWorkspaceUrl(pkpmWorkspaceUrl);
            pkpmWorkspaceUrl.getUrl()
					.replaceAll("\\{areaName\\}", pkpmWorkspaceUrl.getAreaName())
					.replaceAll("\\{projectId\\}", pkpmWorkspaceUrl.getProjectId())
					.replaceAll("\\{desktopId\\}", pkpmWorkspaceUrl.getDesktopId());
        }
        return commonReq;
    }


    public CommonRequestBean buildQueryProductPackage(CommonRequestBean commonRequestBean) {

        CommonRequestBean commonReq = new CommonRequestBean();
        PkpmToken pkpmToken = new PkpmToken();
        String projectId = commonRequestBean.getProjectId();
        pkpmToken.setToken(workspaceService.createToken(projectId));
        commonReq.setPkpmToken(pkpmToken);

        PkpmProjectDef projectDef = pkpmProjectDefDAO.selectById(projectId);
        Preconditions.checkNotNull(projectDef, "projectDef对象不能为空");
        if (projectDef != null) {
            String areaName = projectDef.getAreaName();
            Preconditions.checkNotNull(StringUtils.isNotBlank(areaName), "areaName不能为空");
            PkpmWorkspaceUrl pkpmWorkspaceUrl = pkpmWorkspaceUrlDAO.selectByPriKey(projectId, areaName, DesktopServiceEnum.PRODUCTS.toString());
            commonReq.setPkpmWorkspaceUrl(pkpmWorkspaceUrl);
        }
        return commonReq;
    }

    public CommonRequestBean buildBeanForOperateDesktop(CommonRequestBean commonRequestBean) {

        List<Desktop> desktopList = commonRequestBean.getDesktops();
        String projectId = commonRequestBean.getProjectId();
        Desktop desktop = desktopList.get(0);
        String desktopId = desktop.getDesktopId();
        Integer adId = commonRequestBean.getAdId();
        Long subsId = commonRequestBean.getSubsId();
        Integer userId = commonRequestBean.getUserId();

        CommonRequestBean commonReq = new CommonRequestBean();
        PkpmToken pkpmToken = new PkpmToken();
        pkpmToken.setToken(workspaceService.createToken(projectId));
        commonReq.setPkpmToken(pkpmToken);

        PkpmProjectDef projectDef = pkpmProjectDefDAO.selectById(projectId);
        Preconditions.checkNotNull(projectDef, "projectDef对象不能为空");
        
        String areaName = projectDef.getAreaName();
        Preconditions.checkNotNull(StringUtils.isNotBlank(areaName), "areaName对象不能为空");
        // pkpmWorkspaceUrl配置
        PkpmWorkspaceUrl pkpmWorkspaceUrl = pkpmWorkspaceUrlDAO.selectByPriKey(projectId, areaName, DesktopServiceEnum.REBOOT_CLOSE.toString());
        pkpmWorkspaceUrl.setDesktopId(desktopId);
        commonReq.setPkpmWorkspaceUrl(pkpmWorkspaceUrl);

        commonReq.setAdId(adId);
        commonReq.setSubsId(subsId);
        commonReq.setUserId(userId);
        commonReq.setDesktops(desktopList);
        return commonReq;
    }


    public CommonRequestBean buildBeanforQueryDesktopDetail(CommonRequestBean requestBean) {

        CommonRequestBean commonReq = new CommonRequestBean();
        
        PkpmToken pkpmToken = new PkpmToken();
        String projectId = requestBean.getProjectId();
        pkpmToken.setToken(workspaceService.createToken(projectId));
        commonReq.setPkpmToken(pkpmToken);

        PkpmProjectDef projectDef = pkpmProjectDefDAO.selectById(projectId);
        Preconditions.checkNotNull(projectDef,"projectDef是空的");
        String areaName = projectDef.getAreaName();
        Preconditions.checkNotNull(StringUtils.isNotBlank(areaName), "areaName不能为空");
        PkpmWorkspaceUrl pkpmWorkspaceUrl = pkpmWorkspaceUrlDAO.selectByPriKey(projectId, areaName, DesktopServiceEnum.DETAIL.toString());
        //pkpmWorkspaceUrl.setDesktopId(requestBean.getDesktops().get(0).getDesktopId());
		pkpmWorkspaceUrl.setDesktopId(requestBean.getDesktopId());
        commonReq.setPkpmWorkspaceUrl(pkpmWorkspaceUrl);
        
        return commonReq;
    }

    public CommonRequestBean buildBeanForQueryqueryDesktopListOrDetail(CommonRequestBean requestBean) {
        PkpmWorkspaceUrl pkpmWorkspaceUrl = null;
        CommonRequestBean commonReq = new CommonRequestBean();

        PkpmToken pkpmToken = new PkpmToken();
        String projectId = requestBean.getProjectId();
        pkpmToken.setToken(workspaceService.createToken(projectId));
        commonReq.setPkpmToken(pkpmToken);

        PkpmProjectDef projectDef = pkpmProjectDefDAO.selectById(projectId);
        Preconditions.checkNotNull(projectDef, "projectDef对象不能为空");
        
        String desktopType = requestBean.getQueryDesktopType();
        String areaName = projectDef.getAreaName();
        Preconditions.checkNotNull(StringUtils.isNotBlank(areaName), "areaName不能为空");
        if (DesktopConstant.QUERY_DESKTOP_SIMPLE.equals(desktopType)) {
            pkpmWorkspaceUrl = pkpmWorkspaceUrlDAO.selectByPriKey(projectId, areaName, DesktopServiceEnum.LIST_DESKTOPS.toString());
        } else if (DesktopConstant.QUERY_DESKTOP_DETAIL.equals(desktopType)) {
            pkpmWorkspaceUrl = pkpmWorkspaceUrlDAO.selectByPriKey(projectId, areaName, DesktopServiceEnum.LIST_DESKTOP_DETAIL.toString());
        }
        commonReq.setPkpmWorkspaceUrl(pkpmWorkspaceUrl);

        return commonReq;
    }
    
    public CommonRequestBean buildUpdateStrategy(String projectId) {

        CommonRequestBean commonReq = new CommonRequestBean();
        PkpmToken pkpmToken = new PkpmToken();
        pkpmToken.setToken(workspaceService.createToken(projectId));
        commonReq.setPkpmToken(pkpmToken);
        
        PkpmProjectDef projectDef = pkpmProjectDefDAO.selectById(projectId);
        Preconditions.checkNotNull(projectDef, "projectDef对象不能为空");
        String areaName = projectDef.getAreaName();
        Preconditions.checkNotNull(StringUtils.isNotBlank(areaName), "areaName不能为空");
        PkpmWorkspaceUrl pkpmWorkspaceUrl = pkpmWorkspaceUrlDAO.selectByPriKey(projectId, areaName, DesktopServiceEnum.POLICES.toString(), HttpMethod.PUT.toString());
        commonReq.setPkpmWorkspaceUrl(pkpmWorkspaceUrl);
        
        return commonReq;
    }
    
    public CommonRequestBean buildBeanForQueryStrategy(CommonRequestBean requestBean) {
    	
        CommonRequestBean commonReq = new CommonRequestBean();
        
        PkpmToken pkpmToken = new PkpmToken();
        String projectId = requestBean.getProjectId();
        pkpmToken.setToken(workspaceService.createToken(projectId));
        commonReq.setPkpmToken(pkpmToken);

        PkpmProjectDef projectDef = pkpmProjectDefDAO.selectById(projectId);
        Preconditions.checkNotNull(projectDef, "projectDef不能为空");
        String areaName = projectDef.getAreaName();
        Preconditions.checkNotNull(StringUtils.isNotBlank(areaName), "areaName不能为空");
        PkpmWorkspaceUrl pkpmWorkspaceUrl = pkpmWorkspaceUrlDAO.selectByPriKey(projectId, areaName, DesktopServiceEnum.POLICES.toString(), HttpMethod.GET.toString());
        commonReq.setPkpmWorkspaceUrl(pkpmWorkspaceUrl);
        
        return commonReq;
    }
}
