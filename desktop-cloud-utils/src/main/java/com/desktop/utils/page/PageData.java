package com.desktop.utils.page;

import lombok.Data;

import java.util.List;

/**
 * PageData
 *
 * @author
 * @since 2/13/18
 */
@Data
public class PageData<T> {
    private long total;
    private List<T> list;
}