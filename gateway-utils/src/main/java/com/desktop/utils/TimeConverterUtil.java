package com.desktop.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
/**
 * @Description 对UTC时间的转换工具类
 * @author yangpengfei
 * @time 2018年4月3日 下午3:11:06
 */
public class TimeConverterUtil {
    /**
     * 函数功能描述:UTC时间转本地时间格式
     *
     * @param utcTime         UTC时间
     * @param utcTimePatten   UTC时间格式
     * @param localTimePatten 本地时间格式
     * @return 本地时间格式的时间
     * eg:utc2Local("2017-06-14 09:37:50.788+08:00", "yyyy-MM-dd HH:mm:ss.SSSXXX", "yyyy-MM-dd HH:mm:ss.SSS")
     */
    public static String utc2Local(String utcTime, String utcTimePatten, String localTimePatten) {
        SimpleDateFormat utcFormater = new SimpleDateFormat(utcTimePatten);
        //  utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));//时区定义并进行时间获取
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return utcTime;
        }
        SimpleDateFormat localFormater = new SimpleDateFormat(localTimePatten);
        localFormater.setTimeZone(TimeZone.getDefault());
        String localTime = localFormater.format(gpsUTCDate.getTime());
        return localTime;
    }

    /**
     * 函数功能描述:UTC时间转本地时间格式
     *
     * @param utcTime          UTC时间
     * @param localTimePattern 本地时间格式(要转换的本地时间格式)
     * @return 本地时间格式的时间
     */
    public static String utc2Local(String utcTime, String localTimePattern) {
        String utcTimePattern = "yyyy-MM-dd";
        String subTime = utcTime.substring(10);//UTC时间格式以 yyyy-MM-dd 开头,将utc时间的前10位截取掉,之后是含有多时区时间格式信息的数据

        //处理当后缀为:+8:00时,转换为:+08:00 或 -8:00转换为-08:00
        if (subTime.indexOf("+") != -1) {
            subTime = changeUtcSuffix(subTime, "+");
        }
        if (subTime.indexOf("-") != -1) {
            subTime = changeUtcSuffix(subTime, "-");
        }
        //处理000Z问题:如2018-04-08T10:50:32.923000Z应转换为2018-04-08T10:50:32.923Z
        if (subTime.length() == 17 && subTime.endsWith("000Z")) {
            subTime = subTime.replace("000Z", "Z");
        }
        utcTime = utcTime.substring(0, 10) + subTime;

        //依据传入函数的utc时间,得到对应的utc时间格式
        //步骤一:处理 T
        if (utcTime.indexOf("T") != -1) {
            utcTimePattern = utcTimePattern + "'T'";
        }

        //步骤二:处理毫秒SSS
        if (utcTime.indexOf(".") != -1) {
            utcTimePattern = utcTimePattern + "HH:mm:ss.SSS";
        } else {
            utcTimePattern = utcTimePattern + "HH:mm:ss";
        }

        //步骤三:处理时区问题
        if (subTime.indexOf("+") != -1 || subTime.indexOf("-") != -1) {
            utcTimePattern = utcTimePattern + "XXX";
        } else if (subTime.indexOf("Z") != -1) {
            utcTimePattern = utcTimePattern + "'Z'";
        }

        if ("yyyy-MM-dd HH:mm:ss".equals(utcTimePattern) || "yyyy-MM-dd HH:mm:ss.SSS".equals(utcTimePattern)) {
            return utcTime;
        }

        SimpleDateFormat utcFormater = new SimpleDateFormat(utcTimePattern);
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUtcDate = null;
        try {
            log.info("utcTime=" + utcTime);
            gpsUtcDate = utcFormater.parse(utcTime);
        } catch (Exception e) {
            log.error("utcTime converter localTime failed!!!", e);
            return utcTime;
        }
        SimpleDateFormat localFormater = new SimpleDateFormat(localTimePattern);
        localFormater.setTimeZone(TimeZone.getDefault());
        String localTime = localFormater.format(gpsUtcDate.getTime());
        return localTime;
    }

    /**
     * 函数功能描述:修改时间格式后缀
     * 函数使用场景:处理当后缀为:+8:00时,转换为:+08:00 或 -8:00转换为-08:00
     *
     * @param subTime
     * @param sign
     * @return
     */
    private static String changeUtcSuffix(String subTime, String sign) {
        String timeSuffix = null;
        String[] splitTimeArrayOne = subTime.split("\\" + sign);
        String[] splitTimeArrayTwo = splitTimeArrayOne[1].split(":");
        if (splitTimeArrayTwo[0].length() < 2) {
            timeSuffix = "+" + "0" + splitTimeArrayTwo[0] + ":" + splitTimeArrayTwo[1];
            subTime = splitTimeArrayOne[0] + timeSuffix;
            return subTime;
        }
        return subTime;
    }

    /**
     * 函数功能描述:获取本地时区的表示(比如:第八区-->+08:00)
     *
     * @return
     */
    public static String getTimeZoneByNumExpress() {
        Calendar cal = Calendar.getInstance();
        TimeZone timeZone = cal.getTimeZone();
        int rawOffset = timeZone.getRawOffset();
        int timeZoneByNumExpress = rawOffset / 3600 / 1000;
        String timeZoneByNumExpressStr = "";
        if (timeZoneByNumExpress > 0 && timeZoneByNumExpress < 10) {
            timeZoneByNumExpressStr = "+" + "0" + timeZoneByNumExpress + ":" + "00";
        } else if (timeZoneByNumExpress >= 10) {
            timeZoneByNumExpressStr = "+" + timeZoneByNumExpress + ":" + "00";
        } else if (timeZoneByNumExpress > -10 && timeZoneByNumExpress < 0) {
            timeZoneByNumExpress = Math.abs(timeZoneByNumExpress);
            timeZoneByNumExpressStr = "-" + "0" + timeZoneByNumExpress + ":" + "00";
        } else if (timeZoneByNumExpress <= -10) {
            timeZoneByNumExpress = Math.abs(timeZoneByNumExpress);
            timeZoneByNumExpressStr = "-" + timeZoneByNumExpress + ":" + "00";
        } else {
            timeZoneByNumExpressStr = "Z";
        }
        return timeZoneByNumExpressStr;
    }

    /**
     * @return 例如:UTC时间:2018-04-03T05:57:06.723Z; 如果获取失败，返回null
     * @Description 得到当前UTC时间，类型为字符串，格式为"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
     * @author yangpengfei
     * @time 2018年4月3日 下午1:36:22
     */
    public static String getUTCTimeStr() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        // 取得本地时间：
        Calendar cal = Calendar.getInstance();
        // 取得时间偏移量：
        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
        // 取得夏令时差：
        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
        // 从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        int millisecond = cal.get(Calendar.MILLISECOND);

        StringBuffer UTCTimeBuffer = new StringBuffer();
        UTCTimeBuffer.append(year).append("-");
        if (month < 10) {
            UTCTimeBuffer.append("0");
        }
        UTCTimeBuffer.append(month).append("-");
        if (day < 10) {
            UTCTimeBuffer.append("0");
        }
        UTCTimeBuffer.append(day).append("T");
        if (hour < 10) {
            UTCTimeBuffer.append("0");
        }
        UTCTimeBuffer.append(hour).append(":");
        if (minute < 10) {
            UTCTimeBuffer.append("0");
        }
        UTCTimeBuffer.append(minute).append(":");
        if (second < 10) {
            UTCTimeBuffer.append("0");
        }
        UTCTimeBuffer.append(second).append(".");
        if (millisecond < 10) {
            UTCTimeBuffer.append("00");
        } else if (millisecond >= 10 && millisecond < 100) {
            UTCTimeBuffer.append("0");
        }
        UTCTimeBuffer.append(millisecond).append("Z");

        try {
            format.parse(UTCTimeBuffer.toString());
            return UTCTimeBuffer.toString();
        } catch (ParseException e) {
            log.error("localTime converter utcTime failed!!!", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param UTCTime UTC时间
     * @return long型数字，失败返回-1
     * @Description 将UTC时间转换为long型数字
     * @author yangpengfei
     * @time 2018年4月4日 下午4:51:39
     */
    public static long getLongByUTCTime(String UTCTime) {
        //UTC时间格式以 "yyyy-MM-dd'T'HH:mm:ss.SSS"开头,将utc时间的前23位截取出来，作为变换为long型的数据
        String subTime = UTCTime.substring(0, 23);
        //步骤1:处理 T
        if (subTime.indexOf("T") != -1) {
            subTime = subTime.replace("T", "");
        }
        //步骤2:处理 -
        if (subTime.indexOf("-") != -1) {
            subTime = subTime.replace("-", "");
        }
        //步骤3:处理 :
        if (subTime.indexOf(":") != -1) {
            subTime = subTime.replace(":", "");
        }
        //步骤4:处理 .
        if (subTime.indexOf(".") != -1) {
            subTime = subTime.replace(".", "");
        }
        try {
            long UTCTimeLong = Long.parseLong(subTime);
            return UTCTimeLong;
        } catch (Exception e) {
            log.error("utcTime converter long failed!!!", e);
            e.printStackTrace();
        }
        return -1;
    }

    public static void main(String[] args) {
        String localtime = utc2Local("2018-04-08T10:50:32.923000Z", "yyyy-MM-dd HH:mm:ss");
        System.out.println("issued_at=======" + localtime);
        String localtime2 = utc2Local("2018-04-09T10:50:32.923000Z", "yyyy-MM-dd HH:mm:ss");
        System.out.println("expires_at=======" + localtime2);

        System.out.println("本地时区:" + getTimeZoneByNumExpress());

        System.out.println("当前UTC时间:" + getUTCTimeStr());

        long utcLong = getLongByUTCTime(getUTCTimeStr());
        System.out.println("utcLong=======" + utcLong);

        long b = getLongByUTCTime("2018-04-04T06:33:23.399000Z");
        if (utcLong < b) {
            System.out.println("=======当前时间小======");
        }

        String utcToCST = UTCToCST("2018-04-08T10:50:32.923000Z", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        System.out.println(utcToCST);
    }


    /**
     * @param UTCStr
     * @param format
     * @return 北京时间
     */
    public static String UTCToCST(String UTCStr, String format) {
        String str = UTCStr.replace("000Z", "Z");
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            date = sdf.parse(str);
            System.out.println("UTC时间: " + date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
            //calendar.getTime() 返回的是Date类型，也可以使用calendar.getTimeInMillis()获取时间戳
            System.out.println("北京时间: " + calendar.getTime());
            Date date1 = calendar.getTime();

            String format1 = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat sdf1 = new SimpleDateFormat(format1);
            String str2 = sdf1.format(date1);
            return str2;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
