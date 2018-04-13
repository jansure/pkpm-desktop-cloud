package com.desktop.utils.page;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询返回对象
 *
 * @param <T>
 * @author zhangshuai
 * @since 2018/02/03
 */
public class Page<T> implements Serializable {

    private long total;
    private List<T> pageData;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getPageData() {
        return pageData;
    }

    public void setPageData(List<T> pageData) {
        this.pageData = pageData;
    }

}
