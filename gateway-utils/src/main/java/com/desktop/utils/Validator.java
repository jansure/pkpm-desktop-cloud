package com.desktop.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static boolean isIPAddress(String str) {
        Pattern p = Pattern.compile(
                "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean isEmail(String str) {
        Pattern p = Pattern.compile("^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,5}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean isMobile(String str) {
        Pattern p = Pattern.compile("^1[3|4|5|6|7|8|9][0-9]{9}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }
}