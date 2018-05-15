package com.desktop.constant;

import com.desktop.utils.exception.Exceptions;
import org.apache.commons.codec.binary.StringUtils;

import java.util.Arrays;

/**
 * 
 * @ClassName: MessageStatusEnum  
 * @Description: 消息状态 
 * @author wangxiulong  
 * @date 2018年5月15日  
 *
 */
public enum MessageStatusEnum {
	/**
     * 发送中
     */
    CREATE,
    /**
     * 发送成功
     */
    SUCCESS,
    /**
     * 发送失败
     */
    FAILED;

    public static MessageStatusEnum eval(String code) {
        return Arrays.stream(values())
                .filter(value -> StringUtils.equals(value.name(), code))
                .findFirst()
                .orElseThrow(() -> Exceptions.newBusinessException(String.format("错误的消息类型:%s", code)));
    }
}
