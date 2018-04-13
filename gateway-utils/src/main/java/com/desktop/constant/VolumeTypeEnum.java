package com.desktop.constant;

import com.desktop.utils.exception.Exceptions;
import org.apache.commons.codec.binary.StringUtils;

import java.util.Arrays;

public enum VolumeTypeEnum {
    /**
     * SATA
     */
    SATA,
    /**
     * SSD
     */
    SSD;

    public static VolumeTypeEnum eval(String code) {
        return Arrays.stream(values())
                .filter(value -> StringUtils.equals(value.name(), code))
                .findFirst()
                .orElseThrow(() -> Exceptions.newBusinessException(String.format("错误磁盘类型:%s", code)));
    }
}
