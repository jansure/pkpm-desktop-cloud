package com.desktop.utils.exception;

import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常工具类
 *
 * @author zhangshuai
 * @since 13/03/2018
 */
@Slf4j
public class Exceptions {
    public static BusinessException newBusinessException(String message) {
        log.error(message);
        return new BusinessException(message);
    }

    public static IllegalArgumentException newIllegalArgumentException(String message) {
        log.error(message);
        return new IllegalArgumentException(message);
    }

    public static IllegalStateException newIllegalStateException(String message) {
        log.error(message);
        return new IllegalStateException(message);
    }

    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
