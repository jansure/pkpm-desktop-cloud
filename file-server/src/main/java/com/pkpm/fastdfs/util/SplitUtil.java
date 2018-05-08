package com.pkpm.fastdfs.util;

import com.google.common.base.Splitter;

import java.util.List;

/**
 * 字符串分割工具类
 */
public class SplitUtil {
    /**
     * 字符串分割
     *
     * @param reg 按照什么格式分割
     * @param str 要分割的字符串
     * @return list
     */
    public static List<String> split(String reg, String str) {
        return Splitter.on(reg)
                .trimResults()
                .omitEmptyStrings()
                .splitToList(str);
    }
}
