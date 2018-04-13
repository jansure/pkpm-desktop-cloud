package com.desktop.constant;

import com.desktop.utils.exception.Exceptions;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

/**
 * 
 * @ClassName: SubscriptionStatusEnum  
 * @Description: 订单状态枚举类  
 * @author wangxiulong  
 * @date 2018年4月13日  
 *
 */
public enum SubscriptionStatusEnum {
    /**
     *  无效
     */
	INVALID,
    /**
     * 有效
     */
	VALID;

    public static SubscriptionStatusEnum eval(String code) {
        return Arrays.stream(values())
                .filter(value -> StringUtils.equals(value.name(), code))
                .findFirst()
                .orElseThrow(() -> Exceptions.newBusinessException(String.format("错误的订单状态:%s", code)));
    }
}
