package com.desktop.constant;

import com.desktop.utils.exception.Exceptions;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

/**
 * 枚举DesktopService方法
 *
 * @author yangpengfei
 * @version 1.0
 */
public enum DesktopServiceEnum {
    /**
     * 调用华为云接口方法:获取token
     */
    TOKEN,
    /**
     * 调用华为云接口方法:创建桌面
     */
    CREATE,
    /**
     * 调用华为云接口方法:查询任务
     */
    LIST_JOB,
    /**
     * 调用华为云接口方法:删除桌面
     */
    DELETE,
    /**
     * 调用华为云接口方法:重启桌面
     */

    REBOOT,
    /**
     * 启动桌面
     */
    BOOT,
    /**
     * 启动桌面
     */
    START_UP,
    /**
     * 调用华为云接口方法:关闭桌面
     */
    CLOSE,
    /**
     * 调用华为云接口方法:查询桌面列表
     */
    LIST_DESKTOPS,
    /**
     * 调用华为云接口方法:查询桌面详情
     */
    LIST_DESKTOP_DETAIL,
    /**
     * 调用华为云接口方法:查询桌面详情列表
     */
    LIST_DESKTOP_DETAILS,
    /**
     * 调用华为云接口方法:修改桌面属性
     */
    MODIFY,
    /**
     * 调用华为云接口方法:变更桌面规格
     */
    CHANGE_SPECS,
    /**
     * 重新启动、开启、关闭桌面
     */
    REBOOT_CLOSE,
    /**
     * 查询桌面详情
     */
    DETAIL,
    /**
     * 策略
     */
    POLICES,
    /**
     * 查询桌面详情
     */
    PRODUCTS,
    /**
     *
     */
    USER_LIST,
    /**
     *
     */
    LOGIN_RECORD;

    public static DesktopServiceEnum eval(String code) {
        return Arrays.stream(values())
                .filter(value -> StringUtils.equals(value.name(), code))
                .findFirst()
                .orElseThrow(() -> Exceptions.newBusinessException(String.format("错误的Desktop或者AD状态:%s", code)));
    }
}