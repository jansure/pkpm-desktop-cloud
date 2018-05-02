package com.pkpmdesktopcloud.desktopcloudbusiness.domain;

import java.util.Date;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
public class PkpmCloudProductRate {
    /** 产品费用定价编号id*/
    private Integer productRateId;

    /** 产品计费定价名称*/
    private String productRateName;

    /** 产品定价生效时间*/
    private Date productRateValidtime;

    /** 产品定价失效时间*/
    private Date productRateInvalidtime;

    /** 产品定价价格*/
    private Integer productRateFee;

}