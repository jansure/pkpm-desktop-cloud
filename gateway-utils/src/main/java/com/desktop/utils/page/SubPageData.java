package com.desktop.utils.page;

import lombok.Data;

import java.util.List;

/**
 * SubPageData
 *
 * @author zhangshuai
 * @since 2/13/18
 */

@Data
public class SubPageData<T> {
    private Object key;
    private Object value;
    private List<T> list;
}