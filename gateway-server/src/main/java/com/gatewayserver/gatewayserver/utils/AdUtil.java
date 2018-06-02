package com.gatewayserver.gatewayserver.utils;

import com.gateway.common.domain.CommonRequestBean;
import com.gateway.common.domain.PkpmAdDef;
import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;

/**
 * @Description
 * @Author xuhe
 * @Date
 */
public class AdUtil {

    public static String getBaseDN(PkpmAdDef adDef) {
        return String.format("DC=%s,DC=%s", adDef.getAdDc(), adDef.getAdRoot());
    }

    public static String getOuDN(PkpmAdDef adDef) {
        String ouDn = adDef.getAdOu();
        String baseDN = getBaseDN(adDef);
        ouDn = ouDn.replace("/", ",OU=");
        return String.format("OU=%s,%s",ouDn,baseDN);
    }

    public static String getAdminDN(PkpmAdDef adDef) {
        return String.format("CN=%s,CN=Users,DC=%s,DC=%s", adDef.getAdAdminAccount(), adDef.getAdDc(), adDef.getAdRoot());

    }

    public static void checkAdDef(PkpmAdDef adDef) {
        Preconditions.checkArgument(null != adDef, "AD域定义不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(adDef.getAdAdminAccount()), "AD域管理员不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(adDef.getAdAdminPassword()), "AD域管理员密码不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(adDef.getAdRoot()), "AD域根不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(adDef.getAdIpAddress()), "AD域所在主机不能为空");
        Preconditions.checkArgument(null != adDef.getAdPort() && adDef.getAdPort() > 0, "AD域端口必须大于0");
        Preconditions.checkArgument(StringUtils.isNotBlank(adDef.getAdGroup()), "AD域所属群组不能为空");
    }

    public static void checkAdUser(CommonRequestBean requestBean) {
        Preconditions.checkArgument(null != requestBean, "AD用户空指针异常");
        Preconditions.checkArgument(StringUtils.isNotBlank(requestBean.getUserName()), "AD用户名不能为空");
        Preconditions.checkArgument(null != requestBean.getAdId() && requestBean.getAdId() > 0, "AdId必须大于0");
    }

    public static void checkAdId(Integer adId){
        Preconditions.checkArgument(adId!=null&&adId>=0,"adId应大于0");
    }
}
