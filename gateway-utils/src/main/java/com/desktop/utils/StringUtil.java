package com.desktop.utils;

import java.text.NumberFormat;
import java.util.UUID;

public class StringUtil {

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String double2percentage(Double d) {
        NumberFormat num = NumberFormat.getPercentInstance();
        num.setMaximumIntegerDigits(3);
        num.setMaximumFractionDigits(2);
        String format = num.format(d);
        return format;
    }
}
