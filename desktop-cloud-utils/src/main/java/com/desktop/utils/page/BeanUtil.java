package com.desktop.utils.page;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description: Bean工具类
 * @Author: xuhe
 * @Date: 2018/4/11
 */
public class BeanUtil {
    /**
     * @param src,target 复制源，目标对象
     * @return void
     * @Description 非空字段复制  将源Bean的非空字段的值复制到对应的目标Bean中
     * @Author xuhe
     */
    public static void copyPropertiesIgnoreNull(Object src, Object target) {

        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    /**
     * @param source
     * @return java.lang.String[]
     * @Description 将空字段添加到过滤列表中，复制属性时会过滤掉
     * @Author xuhe
     */
    public static String[] getNullPropertyNames(Object source) {

        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
