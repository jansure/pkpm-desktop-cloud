package com.desktop.utils.page;

import com.google.common.base.Preconditions;
import lombok.NonNull;

/**
 * PageUtils
 *
 * @author zhangshuai
 * @since 2/3/18
 */
public class PageUtils {
    public static Integer getBeginPos(@NonNull Integer pageStart, @NonNull Integer pageSize) {
        Preconditions.checkArgument(null != pageStart && pageStart > 0, "开始页数必须大于0");
        Preconditions.checkArgument(null != pageSize && pageSize > 0, "每页大小必须大于0");
        return (pageStart - 1) * pageSize;
    }
}
