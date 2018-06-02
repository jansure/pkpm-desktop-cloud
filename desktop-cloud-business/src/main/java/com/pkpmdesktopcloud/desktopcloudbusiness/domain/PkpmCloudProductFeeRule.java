package com.pkpmdesktopcloud.desktopcloudbusiness.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
public class PkpmCloudProductFeeRule {
    /** 产品计费规则ID*/
    private Integer ruleId;

    /** 产品�*/
    private Integer ruleType;

    /** 产品计费名称*/
    private String ruleName;

    /** 产品计费规则描述*/
    private String ruleDesc;

    /** 产品计费元单价*/
    private Integer ruleRate;

}