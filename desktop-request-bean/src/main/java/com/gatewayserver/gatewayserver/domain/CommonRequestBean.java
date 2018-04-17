package com.gatewayserver.gatewayserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gatewayserver.gatewayserver.dto.Auth;
import com.gatewayserver.gatewayserver.dto.Desktop;
import com.gatewayserver.gatewayserver.dto.User;
import com.gatewayserver.gatewayserver.dto.record.Record;
import com.gatewayserver.gatewayserver.dto.restart.Reboot;
import com.gatewayserver.gatewayserver.dto.strategy.Policies;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonRequestBean implements Serializable {
    private String areaCode;

    //*******前端传递*********
    private Long subsId;
    private Integer userId;
    private String userName;
    private String projectId;
    private Integer adId;
    private String desktopId;

    // **前端传递**创建桌面所需参数
    private String imageId;
    private String productId;
    private String hwProductId;   //workspace.c2.large.windows
    // 产品名称/云桌面计算机名
    private String gloryProductName;
    private String ouName;        //默认pkpm
    private Integer dataVolumeSize;
    // 接收ad域创建后传过来的operatorStatusId
    private Integer operatorStatusId;

    //可选参数，用于更新用户信息
    private String userLoginPassword;
    private String userMobileNumber;
    private String userEmail;

    //目前一个request包含一个desktop，与华为接口对List要求兼容
    //桌面操作选取索引为0元素；
    private List<Desktop> desktops;
    //可选参数，更改策略时使用
    private Policies policies;
    //可选参数，注销用户时，要不要删除云桌面用户，值为true或者false；
    private String deleteUsers;

    //后台组装，处理后赋值*********
    private Auth auth;
    //后台获取projectId对应的Token
    private PkpmToken pkpmToken;
    //后台拼装的Url
    private PkpmWorkspaceUrl pkpmWorkspaceUrl;
    private String limit;
    private Reboot reboot;


    //操作类型
    private String desktopOperatorType;
    private String queryDesktopType;

    //可用分区
    private String availabilityZone;
    //查询桌面用户列表 可选参数()

    private User user;
    private LoginRecords loginRecords;
    private AdDomain adDomain;

    private Record record;

}
