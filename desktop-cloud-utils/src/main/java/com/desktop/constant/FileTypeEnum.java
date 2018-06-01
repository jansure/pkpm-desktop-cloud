package com.desktop.constant;

import com.desktop.utils.exception.Exceptions;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

/**
 * @ClassName: FileTypeEnum  
 * @Description: 文件类型枚举类
 * @author wangxiulong  
 * @date 2018年6月1日  
 *
 */
public enum FileTypeEnum {
    /**
     * 图片
     */
	IMAGE,
    /**
     * 视频
     */
	VIDEO,
    /**
     * 文档
     */
	DATUM;

    public static FileTypeEnum eval(String code) {
        return Arrays.stream(values())
                .filter(value -> StringUtils.equals(value.name(), code))
                .findFirst()
                .orElseThrow(() -> Exceptions.newBusinessException(String.format("错误的文件类型:%s", code)));
    }
}
