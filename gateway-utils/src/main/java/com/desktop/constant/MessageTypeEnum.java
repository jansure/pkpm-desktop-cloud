package com.desktop.constant;

import com.desktop.utils.exception.Exceptions;
import org.apache.commons.codec.binary.StringUtils;

import java.util.Arrays;

/**
 * MessageTypeEnum
 *
 * @author jansure.zhang
 * @since 5/05/2018
 */
public enum MessageTypeEnum {
    /**
     * 系统通知
     */
    notify,
    /**
     * 站内信
     */
    message,
    /**
     * 邮件
     */
    email,
    /**
     * 短信
     */
    sms,
    /**
     * 微信公众号推送
     */
    wechat;

    public static MessageTypeEnum eval(String code) {
        return Arrays.stream(values())
                .filter(value -> StringUtils.equals(value.name(), code))
                .findFirst()
                .orElseThrow(() -> Exceptions.newBusinessException(String.format("错误的消息类型:%s", code)));
    }
}
