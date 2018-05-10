package com.desktop.constant;

import com.desktop.utils.exception.Exceptions;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

/**
 * @author wangxiulong
 * @ClassName: JobStatusEnum
 * @Description: Job状态枚举类
 * @date 2018年4月9日
 */
public enum JobStatusEnum {
    /**
     * Desktop初始化
     */
    INITIAL,
    /**
     * Desktop创建过程
     */
    CREATE,
    /**
     * Desktop创建成功
     */
    SUCCESS,
    /**
     * Desktop创建失败
     */
    FAILED,
    /**
     * AD创建
     */
    AD_CREATE,
    /**
     * AD创建成功
     */
    AD_SUCCESS,
    /**
     * AD创建失败
     */
    AD_FAILED;

    public static JobStatusEnum eval(String code) {
        return Arrays.stream(values())
                .filter(value -> StringUtils.equals(value.name(), code))
                .findFirst()
                .orElseThrow(() -> Exceptions.newBusinessException(String.format("错误的任务操作状态:%s", code)));
    }
}
