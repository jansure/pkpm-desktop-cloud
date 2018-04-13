package com.desktop.constant;

import com.desktop.utils.exception.Exceptions;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

/**
 * @author wangxiulong
 * @ClassName: ResponseStatusEnum
 * @Description: 华为接口返回状态枚举类
 * @date 2018年4月10日
 */
public enum ResponseStatusEnum {
    /**
     * 初始化
     */
    INIT,
    /**
     * 运行中
     */
    RUNNING,
    /**
     * 成功
     */
    SUCCESS,
    /**
     * 失败
     */
    FAILED,
    /**
     * 开机
     */
    ACTIVE,
    /**
     * 关机
     */
    SHUTOFF;

    public static ResponseStatusEnum eval(String code) {
        return Arrays.stream(values())
                .filter(value -> StringUtils.equals(value.name(), code))
                .findFirst()
                .orElseThrow(() -> Exceptions.newBusinessException(String.format("错误的华为返回状态:%s", code)));
    }
}
