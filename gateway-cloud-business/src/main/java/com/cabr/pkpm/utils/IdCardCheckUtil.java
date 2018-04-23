package com.cabr.pkpm.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 身份证号码验证 1、号码的结构 公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。排列顺序从左至右依次为：六位数字地址码，
 * 八位数字出生日期码，三位数字顺序码和一位数字校验码。 2、地址码(前六位数）
 * 表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。 3、出生日期码（第七位至十四位）
 * 表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。 4、顺序码（第十五位至十七位）
 * 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号， 顺序码的奇数分配给男性，偶数分配给女性。 5、校验码（第十八位数）
 * （1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和
 * Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4
 * 2 （2）计算模 Y = mod(S, 11) （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0
 * X 9 8 7 6 5 4 3 2
 */

public class IdCardCheckUtil {
    private final static String VALID = "valid";
    private static final String[] CHECK_CODES = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
    private static final String[] WEIGHTING_CODES = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2"};

    public static boolean isValid(String id) {
        try {
            String errMsg = check(id);
            if (VALID.equals(errMsg)) {
                return true;
            }
        } catch (java.text.ParseException e) {
            return false;
        }
        return false;
    }

    /**
     * 功能：身份证的有效验证
     *
     * @param id id card number
     * @return error message, or valid
     * @throws java.text.ParseException 解析错误
     */

    private static String check(String id) throws java.text.ParseException {

        // ================ 号码的长度 15位或18位 ================
        if (id.length() != 15 && id.length() != 18) {
            return "身份证号码长度应该为15位或18位。";
        }

        // ================ 数字 除最后以为都为数字 ================
        String positionCode;
        if (id.length() == 18) {
            positionCode = id.substring(0, 17);
        } else {
            positionCode = id.substring(0, 6) + "19" + id.substring(6, 15);
        }
        	//TODO
        return positionCode;
    }
}