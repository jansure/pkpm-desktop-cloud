package com.desktop.utils;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoUnit.*;

/**
 * 时间工具类
 *
 * @author zhangshuai
 * @since 13/03/2018
 */

public class DateUtils {

    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前系统时间
     *
     * @return LocalDateTime
     */
    public static LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }

    public static String time2String(LocalDateTime time) {
        return time2String(time, PATTERN);
    }

    public static LocalDateTime string2LocalDateTime(String time) {
        return string2LocalDateTime(time, PATTERN);
    }

    public static LocalDateTime string2LocalDateTime(String time, String pattern) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(time, df);
    }

    /**
     * LocalDateTime转Date
     *
     * @param localDateTime
     * @return
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * Date转LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * 日期按格式转字符串
     *
     * @param time
     * @param pattern yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String time2String(LocalDateTime time, String pattern) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return df.format(time);
    }

    public static long monthsBetween(LocalDateTime beginDate, LocalDateTime endDate) {
        return MONTHS.between(beginDate, endDate);
    }

    public static long daysBetween(LocalDateTime beginDate, LocalDateTime endDate) {
        return DAYS.between(beginDate, endDate);
    }

    public static long yearsBetween(LocalDateTime beginDate, LocalDateTime endDate) {
        return YEARS.between(beginDate, endDate);
    }

    /**
     * 获取两个日期相差多少个月份，如果endDate小于startDate，则相差月份数为0
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getMonthsBetween(LocalDateTime startDate, LocalDateTime endDate) {
        if (endDate.isBefore(startDate)) {
            return 0;
        }

        int year1 = startDate.getYear();
        int month1 = startDate.getMonthValue();
        int year2 = endDate.getYear();
        int month2 = endDate.getMonthValue();
        return (year2 - year1) * 12 + (month2 - month1);
    }

    /**
     * 两个字符串日期之间的天数
     *
     * @param beginDateStr,endDateStr
     * @return long
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate;
        java.util.Date endDate;
        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
            day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    /**
     * 判断日期格式和范围
     *
     * @param date
     * @return true false
     */
    public boolean isDate(String date) {
        String rexp = "^((\\d{2}(([02468][048])|" +
                "([13579][26]))[\\-\\/\\s]?((((0?[13578])|" +
                "(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|" +
                "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|" +
                "([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|" +
                "([1-2][0-9])))))|(\\d{2}(([02468][1235679])|" +
                "([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|" +
                "(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|" +
                "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|" +
                "([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|" +
                "(2[0-8]))))))";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(date);
        boolean isdate = mat.matches();
        return isdate;
    }
}
