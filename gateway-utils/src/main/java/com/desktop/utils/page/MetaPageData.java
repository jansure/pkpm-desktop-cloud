package com.desktop.utils.page;

import lombok.Data;

import java.util.List;


/**
 * MetaPageData
 *
 * @author zhangshuai
 * @since 2/13/18
 */
@Data
public class MetaPageData<T> {
    private List<T> list;
}

