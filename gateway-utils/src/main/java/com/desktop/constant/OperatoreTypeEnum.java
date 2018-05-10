package com.desktop.constant;

/**
 * @author wangxiulong
 * @ClassName: OperatoreTypeEnum
 * @Description: 任务操作类型枚举类
 * @date 2018年4月9日
 */
public enum OperatoreTypeEnum {
    /**
     * 创建桌面
     */
    DESKTOP,
    /**
     * 删除桌面
     */
    DELETE,
    /**
     * 修改桌面属性
     */
    CHANGE,
    /**
     * 变更桌面规格
     */
    RESIZE,
    /**
     * 启动桌面
     */
    BOOT,
    /**
     * 重启桌面
     */
    REBOOT,
    /**
     * 关闭桌面
     */
    CLOSE;

}
