package com.gatewayserver.gatewayserver.utils;

import com.gateway.common.domain.CommonRequestBean;
import com.gateway.common.dto.Desktop;
import com.gateway.common.dto.strategy.Policies;
import com.google.common.base.Preconditions;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class CommonRequestBeanUtil {
    /**
     * 对DesktopInput的校验
     *
     * @param commonRequestBean
     */
    public static void checkDesktopInputForCreate(CommonRequestBean commonRequestBean) {
        Preconditions.checkArgument(null != commonRequestBean, "请求对象不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(commonRequestBean.getProjectId()), "请求对象的ProjectId不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(commonRequestBean.getHwProductId()), "请求对象的HwProductId不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(commonRequestBean.getGloryProductName()), "请求对象的GloryProductName不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(commonRequestBean.getImageId()), "请求对象的ImageId不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(commonRequestBean.getUserName()), "请求对象的UserName不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(commonRequestBean.getUserEmail()), "请求对象的UserEmail不能为空");
        Preconditions.checkArgument(null != commonRequestBean.getDataVolumeSize(), "请求对象的DataVolumeSize不能为空");
        Preconditions.checkArgument(commonRequestBean.getDataVolumeSize() >= 100, "请求对象的DataVolumeSize不能小于100");
        Preconditions.checkArgument(null != commonRequestBean.getUserId(), "请求对象的UserId不能为空");
        Preconditions.checkArgument(null != commonRequestBean.getSubsId(), "请求对象的SubsId不能为空");
        Preconditions.checkArgument(null != commonRequestBean.getAdId(), "请求对象的AdId不能为空");
        Preconditions.checkArgument(null != commonRequestBean.getOperatorStatusId(), "请求对象的OperatorStatusId不能为空");
    }

    /**
     * 对PkpmWorkspaceUrl的校验
     *
     * @param requestBean
     */
    public static void checkCommonRequestBeanForUrl(CommonRequestBean requestBean) {
        Preconditions.checkArgument(null != requestBean, "请求对象不能为空");
        Preconditions.checkArgument(null != requestBean.getPkpmWorkspaceUrl(), "请求对象的workspace配置对象不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(requestBean.getPkpmWorkspaceUrl().getProjectId()), "请求对象的projectId不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(requestBean.getPkpmWorkspaceUrl().getAreaName()), "请求对象的areaName不能为空");
    }

    /**
     * 获取Token接口的参数校验
     *
     * @param requestBean
     */
    public static void checkCommonRequestBeanForToken(CommonRequestBean requestBean) {
        Preconditions.checkArgument(null != requestBean, "请求对象不能为空");
        checkCommonRequestBeanForUrl(requestBean);
        Preconditions.checkArgument(null != requestBean.getAuth(), "请求对象的Auth配置对象不能为空");
        Preconditions.checkArgument(null != requestBean.getAuth().getIdentity(), "请求对象Auth的Identity对象不能为空");
        Preconditions.checkArgument(null != requestBean.getAuth().getScope(), "请求对象Auth的Scope对象不能为空");
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(requestBean.getAuth().getIdentity().getMethods()), "请求对象的Methods不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(requestBean.getAuth().getIdentity().getMethods().get(0)), "请求对象的Methods不能为空");
        Preconditions.checkArgument("password".equals(requestBean.getAuth().getIdentity().getMethods().get(0)), "请求对象的method错误");
        String userName = requestBean.getAuth().getIdentity().getPassword().getUser().getName();
        String domainName = requestBean.getAuth().getIdentity().getPassword().getUser().getDomain().getName();
        Preconditions.checkArgument(StringUtils.isNotBlank(userName), "请求对象的Identity的用户名不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(domainName), "请求对象的Identity的Domain名不能为空");
        Preconditions.checkArgument(userName.equals(domainName), "请求对象的Identity的用户名或Domain名错误");
        Preconditions.checkArgument(StringUtils.isNotBlank(requestBean.getAuth().getIdentity().getPassword().getUser().getPassword()), "请求对象的Identity的密码不能为空");
        String projectId = requestBean.getPkpmWorkspaceUrl().getProjectId();
        String authProjectId = requestBean.getAuth().getScope().getProject().getId();
        Preconditions.checkArgument(StringUtils.isNotBlank(authProjectId), "请求对象的authProjectId不能为空");
        Preconditions.checkArgument(projectId.equals(authProjectId), "请求对象的projectId或authProjectId错误");
    }

    /**
     * 创建桌面接口的参数校验
     *
     * @param requestBean
     */
    public static void checkCommonRequestBeanForCreateDesk(CommonRequestBean requestBean) {
        Preconditions.checkArgument(null != requestBean, "请求对象不能为空");
        checkCommonRequestBeanForUrl(requestBean);
        Preconditions.checkArgument(null != requestBean.getPkpmToken(), "请求对象的token配置对象不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(requestBean.getPkpmToken().getToken()), "请求对象的token不能为空");

        Preconditions.checkArgument(CollectionUtils.isNotEmpty(requestBean.getDesktops()), "请求对象的Desktops不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(requestBean.getDesktops().get(0).getUserName()), "请求对象的Desktops的用户名不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(requestBean.getDesktops().get(0).getUserEmail()), "请求对象的Desktops的用户邮箱不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(requestBean.getDesktops().get(0).getProductId()), "请求对象的Desktops的产品套餐id不能为空");
        Preconditions.checkArgument(null != requestBean.getDesktops().get(0).getRootVolume(), "请求对象的Desktops的rootVolume对象不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(requestBean.getDesktops().get(0).getComputerName()), "请求对象的Desktops的计算机名称不能为空");
    }

    /**
     * 异步任务查询接口的参数校验
     *
     * @param requestBean
     */
    public static void checkCommonRequestBeanForJob(CommonRequestBean requestBean) {
        Preconditions.checkArgument(null != requestBean, "请求对象不能为空");
        checkCommonRequestBeanForUrl(requestBean);
        Preconditions.checkArgument(null != requestBean.getPkpmToken(), "请求对象的token配置对象不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(requestBean.getPkpmToken().getToken()), "请求对象的token不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(requestBean.getPkpmWorkspaceUrl().getJobId()), "请求对象的JobId不能为空");
    }

    /**
     * 更改桌面配置的参数校验
     *
     * @param commonRequestBean
     */
    public static void checkCommonRequestBeanForChgDeskSpec(CommonRequestBean commonRequestBean) {
        checkCommonRequestBean(commonRequestBean);
        Preconditions.checkNotNull(commonRequestBean.getDesktops().get(0).getProductId(),
                "Desktop字段内的product_id不能为空");

    }


    /**
     * 删除桌面接口、修改桌面属性接口的参数校验
     *
     * @param commonRequestBean
     */
    public static void checkCommonRequestBeanForDelChgDesk(CommonRequestBean commonRequestBean) {
        Preconditions.checkArgument(null != commonRequestBean, "commonRequestBean不能为空");
        Desktop desktop = commonRequestBean.getDesktops().get(0);
        String computerName = desktop.getComputerName();
        Preconditions.checkArgument(StringUtils.isNotBlank(computerName), "computerName不能为空");
    }


    /**
     * 二次校验  重启、启动、关闭桌面
     *
     * @param requestBean
     */
    public static void checkCommonRequestBeanForDesktop(CommonRequestBean requestBean) {
        Preconditions.checkArgument(null != requestBean, "请求对象不能为空");
        checkCommonRequestBeanForUrl(requestBean);
        Preconditions.checkArgument(null != requestBean.getPkpmToken(), "请求对象的token配置对象不能为空");
    }


    public static void checkUpdateStrategy(CommonRequestBean commonRequestBean) {

        Preconditions.checkArgument(null != commonRequestBean, "commonRequestBean不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(commonRequestBean.getProjectId()), "commonRequestBean.projectId不能为空");
        Policies policies = commonRequestBean.getPolicies();
        Preconditions.checkArgument(null != policies, "policies对象不能为空");

    }

    /**
     * 校验 重启、启动、关闭桌面的参数
     *
     * @param commonRequestBean
     */
    @SuppressWarnings("unused")
    public static void checkOperateDesktop(CommonRequestBean commonRequestBean) {

        Preconditions.checkArgument(null != commonRequestBean, "commonRequestBean不能为空");
        Desktop desktop = commonRequestBean.getDesktops().get(0);
        String type = desktop.getDesktopOperatorType();
        Preconditions.checkArgument(StringUtils.isNotBlank(type), "operatetype不能为空");

    }

    public static void checkCommonRequestBean(CommonRequestBean commonRequestBean) {
        Preconditions.checkArgument(null != commonRequestBean, "commonRequestBean不能为空");
        String userName = commonRequestBean.getUserName();
        Desktop desktop = commonRequestBean.getDesktops().get(0);
        String projectId = commonRequestBean.getProjectId();
        String desktopId = desktop.getDesktopId();

        String type = desktop.getDesktopOperatorType();
        Preconditions.checkArgument(StringUtils.isNotBlank(projectId), "projectId不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(desktopId), "desktopId不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(userName), "userName不能为空");
        Integer userId = commonRequestBean.getUserId();
        Long subsId = commonRequestBean.getSubsId();
        Integer adId = commonRequestBean.getAdId();
        Preconditions.checkArgument(null != userId, "userId不能为空");
        Preconditions.checkArgument(null != subsId, "subsId不能为空");
        Preconditions.checkArgument(null != adId, "adId不能为空");
    }

    public static void checkStatusCommonRequestBean(CommonRequestBean commonRequestBean) {
        Preconditions.checkArgument(null != commonRequestBean, "请传入正确的参数");
        String projectId = commonRequestBean.getProjectId();
        Long subsId = commonRequestBean.getSubsId();
        Integer userId = commonRequestBean.getUserId();
        Integer adId = commonRequestBean.getAdId();
        Preconditions.checkArgument(StringUtils.isNotBlank(projectId), "projectId不能为空");
        Preconditions.checkArgument(null != subsId, "subsId不能为空");
        Preconditions.checkArgument(null != userId, "userId不能为空");
        Preconditions.checkArgument(null != adId, "adId不能为空");

    }

    @SuppressWarnings("unused")
    public static void checkCommonQueryDesktopDetail(CommonRequestBean commonRequestBean) {

        checkCommonRequestProjectId(commonRequestBean);
        String desktopId = commonRequestBean.getDesktops().get(0).getDesktopId();
        Preconditions.checkArgument(StringUtils.isNotBlank(desktopId), "请传入deskopId参数");

    }

    public static void checkCommonRequestProjectId(CommonRequestBean commonRequestBean) {
        Preconditions.checkArgument(null != commonRequestBean, "请传入正确的参数");
        String projectId = commonRequestBean.getProjectId();
        Preconditions.checkArgument(StringUtils.isNotBlank(projectId), "请传入projectId参数");

    }

    public static void checkDesktopListOrDetailType(CommonRequestBean commonRequestBean) {
        Preconditions.checkArgument(null != commonRequestBean, "请传入正确的参数");
        String querydesktopType = commonRequestBean.getQueryDesktopType();
        Preconditions.checkArgument(StringUtils.isNotBlank(querydesktopType), "请传入querydesktopType参数");
    }

}
