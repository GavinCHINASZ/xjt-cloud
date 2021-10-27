package com.xjt.cloud.commons.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

/**
 * 时间工具类
 *
 * @author 消检通
 * @date 2019/04
 */
public class DateUtils {
    public static void main(String[] args) {
        for (int i = 0; i < 2000; i++) {
            System.out.println(monthEndDate(new Date()));
        }
    }

    private static Log log = LogFactory.getLog(DateUtils.class);
    private static String timePattern = "HH:mm";

    static final SimpleDateFormat SDF_DATE = new SimpleDateFormat("yyyy-MM-dd");

    static final SimpleDateFormat SDF_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static final SimpleDateFormat SDF_SHORTDATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    static final SimpleDateFormat SDF_TIME = new SimpleDateFormat("HH:mm:ss");
    static final SimpleDateFormat HH_MM = new SimpleDateFormat("HH:mm");

    static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    static final SimpleDateFormat yyyyMMdd2 = new SimpleDateFormat("yyyy/MM/dd/");

    public static final String DATETIME = "yyyyMMddHHmmss";

    private static SimpleDateFormat Y_M_D = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat Y_M_D_S = new SimpleDateFormat("yyyy年MM月dd日");

    private static SimpleDateFormat Y_M = new SimpleDateFormat("yyyy-MM");
    private static SimpleDateFormat Y = new SimpleDateFormat("yyyy");

    private static SimpleDateFormat Y_M_D_H = new SimpleDateFormat("yyyy-MM-dd HH");
    private static SimpleDateFormat Y_M_D_H_M_S = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 把日期转换成2008-07-17 14:22:33 2008-7-17
     *
     * @param date Date
     * @return Date
     * @author yaoyuan
     */
    public static Date changeDateFormat(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * This method returns the current date time in the format: MM/dd/yyyy HH:MM
     *
     * @param theTime the current time
     * @return the current date/time
     */
    public static String getTimeNow(Date theTime) {
        return getDateTime(timePattern, theTime);
    }

    /**
     * This method generates a string representation of a date's date/time in
     * the format you specify on input
     *
     * @param aMask the date pattern the string is in
     * @param aDate a date object
     * @return a formatted string representation of the date
     * @see SimpleDateFormat
     */
    public static String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df;
        String returnValue = "";

        if (aDate == null) {
            log.error("aDate is null!");
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }
        return (returnValue);
    }

    /**
     * 返回上市年份下拉菜单，共n�?
     *
     * @param n int
     * @return List
     */
    @SuppressWarnings("unchecked")
    public static List getLatelyYears(int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        List years = new ArrayList(n);
        int year = calendar.get(Calendar.YEAR);
        for (int i = 0; i < n; i++) {
            years.add(year);
            year--;
        }
        return years;
    }

    /**
     * 获取当前日期,java.util.Date
     *
     * @return 当前日期
     */
    public static Date getCurrentDate() {
        return new Date();
    }

    /**
     * 下一�?�?�?�?毫秒 2008-7-4
     *
     * @return Date
     * @author yaoyuan
     */
    public static Date getNextDay() {
        return getNextDay(0);
    }

    /**
     * 下一天hour�?�?�?毫秒 2008-7-4
     *
     * @param hour 指定下一天的某时
     * @return Date
     * @author yaoyuan
     */
    public static Date getNextDay(int hour) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.setTime(setHourToZero(cal.getTime()));
        cal.add(Calendar.HOUR, hour);
        return cal.getTime();
    }

    /**
     * 取得某一天的当前时间 2008-7-4
     *
     * @param num 向前或向后滚动num天，正数向未来，负数向过�?
     * @return Date
     * @author yaoyuan
     */
    public static Date getNowOnOneDay(int num) {
        Calendar cal = Calendar.getInstance();
        cal.roll(Calendar.DATE, num);
        if (num > 0) {
            if (cal.getTime().before(new Date())) {
                cal.add(Calendar.MONTH, 1);
            }
            if (cal.getTime().before(new Date())) {
                cal.add(Calendar.YEAR, 1);
            }
        } else if (num < 0) {
            if (cal.getTime().after(new Date())) {
                cal.add(Calendar.MONTH, -1);
            }
            if (cal.getTime().after(new Date())) {
                cal.add(Calendar.YEAR, -1);
            }
        }
        return cal.getTime();
    }

    /**
     * 取得0.X对应的分钟数�?.5小时�?0分钟 2008-9-1
     *
     * @param f Float
     * @return int
     * @author yaoyuan
     */
    private static int getDecimal(Float f) {
        if (f.toString().indexOf(".") > -1) {
            float result = f.floatValue() - f.intValue();
            return new Float(60 * result).intValue();
        }
        return 0;
    }

    /**
     * 下一天hour�?�?�?毫秒 2008-7-4
     *
     * @param hour 指定下一天的某时
     * @return Date
     * @author yaoyuan
     */
    @SuppressWarnings("deprecation")
    public static Date getNextDay(Float hour) {
        Date date = getNextDay(hour.intValue());
        date.setMinutes(getDecimal(hour));
        return date;
    }

    public static Date getNextMinite(int minite) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, minite);
        return cal.getTime();
    }

    /**
     * 时间格式化方�?格式:2008-05-20 16:40:40 2008-9-2
     *
     * @param date Date
     * @return yyyy-MM-dd HH:mm:ss
     * @author yaoyuan
     */
    public static String getDateTimeString(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    /**
     * 时间格式化方�?格式:201112251212
     *
     * @param date Date
     * @return yyyy-MM-dd HH:mm:ss
     * @author qiaolaing
     */
    public static String getTimeString(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    /**
     * 时间格式化方�?格式:2011122512
     *
     * @param date Date
     * @return yyyyMMddHHmm
     * @author LQY
     */
    public static String getTimeForString(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("yyyyMMddHHmm").format(date);
    }

    /**
     * 时间格式化方�?格式:
     *
     * @param date Date
     * @return yyyy年MM月dd HH:mm
     * @author qiaolaing
     */
    public static String getChineseTime(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy年MM月dd HH:mm").format(date);
    }

    /**
     * 时间格式化方�?格式:2008-05-20 16:40:40 2008-9-2
     *
     * @param date Date
     * @return yyyy-MM
     * @author yaoyuan
     */
    public static String getDateYearMonth(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM").format(date);
    }

    /**
     * 时间格式化方�?格式:2008-05-20 16:40:40 2008-9-2
     *
     * @param date Date
     * @return yyyy-MM-dd HH
     * @author yaoyuan
     */
    public static String getDateYearMonthHour(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH").format(date);
    }

    /**
     * 时间格式化方�?格式:2008-05-20 16:40:40 2008-9-2
     *
     * @param date Date
     * @return yyyy-MM-dd
     * @author yaoyuan
     */
    public static String getDateYearMonthDay(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    /**
     * 功能描述:得到年月日
     *
     * @param date Date
     * @return java.util.Date
     * @author wangzhiwen
     * @date 2019/11/11 16:29
     */
    public static String getDateCN(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy年MM月dd日").format(date);
    }

    /**
     * 功能描述:得到年月日
     *
     * @param date Date
     * @return java.util.Date
     * @author wangzhiwen
     * @date 2019/11/11 16:29
     */
    public static Date getDate(Date date) {
        try {
            if (null == date) {
                date = getDate();
            }
            return SDF_DATE.parse(getDateYearMonthDay(date));
        } catch (Exception ex) {
            SysLog.error(ex);
        }
        return null;
    }

    /**
     * 功能描述:得到当前时间的年月日
     *
     * @return java.util.Date
     * @author wangzhiwen
     * @date 2019/11/11 16:29
     */
    public static Date getDate() {
        try {
            return (new SimpleDateFormat("yyyy-MM-dd")).parse(getDateYearMonthDay(new Date()));
        } catch (Exception ex) {
            SysLog.error(ex);
        }
        return null;
    }

    public static Map<String, String> getHourMap() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("00:00", "00:00");
        map.put("01:00", "01:00");
        map.put("02:00", "02:00");
        map.put("03:00", "03:00");
        map.put("04:00", "04:00");
        map.put("05:00", "05:00");
        map.put("06:00", "06:00");
        map.put("07:00", "07:00");
        map.put("08:00", "08:00");
        map.put("09:00", "09:00");
        map.put("10:00", "10:00");
        map.put("11:00", "11:00");
        map.put("12:00", "12:00");
        map.put("13:00", "13:00");
        map.put("14:00", "14:00");
        map.put("15:00", "15:00");
        map.put("16:00", "16:00");
        map.put("17:00", "17:00");
        map.put("18:00", "18:00");
        map.put("19:00", "19:00");
        map.put("20:00", "20:00");
        map.put("21:00", "21:00");
        map.put("22:00", "22:00");
        map.put("23:00", "23:00");
        return map;
    }

    /**
     * �?6:30转化�?.5 2008-9-4
     *
     * @param src
     * @return
     * @author yaoyuan
     */
    public static double converToHourNumber(String src) {
        String h = src.substring(0, 2);
        String m = src.substring(3, 5);
        return (Double.valueOf(h) + (Double.valueOf(m) / 60.00));
    }

    /**
     * 增加�?�� 2008-9-4
     *
     * @param src
     * @return
     * @author yaoyuan
     */
    public static Date add24Hours(Date src) {
        return new Date(src.getTime() + 86400000);
    }

    /**
     * 减去num天，传入num天数，得到num天前
     *
     * @param date 日期
     * @param num  要减去的天数
     * @return
     */
    public static Date reduce24Hours(Date date, int num) {
        return new Date(date.getTime() + num * 86400000);
    }

    /**
     * 增加N�?2008-9-4
     *
     * @param src
     * @return
     * @author yaoyuan
     */
    public static Date addSecond(Date src, int second) {
        return new Date(src.getTime() + second * 1000);
    }

    private static Date setHourToZero(Date date) {
        if (getDateTimeString(date).subSequence(11, 13).equals("12")) {
            date.setTime(date.getTime() - 12 * 60 * 60 * 1000);
        }
        return date;
    }

    /**
     * 根据str长度来解析日�?
     *
     * @param str
     * @return date
     * @throws ParseException
     * @author yaoyuan
     */
    public static Date parseDate(String str) throws ParseException {
        if (str == null) {
            return null;
        }

        if (str.length() == 10) {
            return SDF_DATE.parse(str);
        }

        if (str.length() == 8) {
            return SDF_TIME.parse(str);
        }

        if (str.length() == 16) {
            return SDF_SHORTDATETIME.parse(str);
        }

        return SDF_DATETIME.parse(str);
    }

    /**
     * 把日期转换为yyyy-MM-dd格式字符�?
     *
     * @param d 日期
     * @author yaoyuan
     */
    public static String formatDate(Date d) {
        return d != null ? SDF_DATE.format(d) : "";
    }

    /**
     * 把日期转换成规定格式的字符串
     *
     * @param pattern 日期格式
     * @param d       日期
     * @return
     * @author huangrui
     * @date 2011-7-18
     */
    public static String formatDate(String pattern, Date d) {
        if (d == null) {
            return "";
        }

        return new SimpleDateFormat(pattern).format(d);
    }

    /**
     * 把日期转换为yyyy-MM-dd HH:mm:ss格式字符�?
     *
     * @param d 日期
     * @throws ParseException
     * @author yaoyuan
     */
    public static String formatDateTime(Date d) {
        return d != null ? SDF_DATETIME.format(d) : "/";
    }

    /**
     * 把日期转换为yyyy-MM-dd HH:mm格式字符�?
     *
     * @param d 日期
     * @author yaoyuan
     */
    public static String formatShortDateTime(Date d) {
        return d != null ? SDF_SHORTDATETIME.format(d) : "";
    }

    /**
     * 把日期转换为HH:mm:ss格式字符�?
     *
     * @param d 日期
     * @author yaoyuan
     */
    public static String formatTime(Date d) {
        return d != null ? SDF_TIME.format(d) : "";
    }

    /**
     * 把日期转换为yyyyMMdd格式字符�?
     * <p>
     * 2011-2-24
     *
     * @param d 日期
     * @author yaoyuan
     */
    public static String formatyyyyMMddDate(Date d) {
        return d != null ? yyyyMMdd.format(d) : "";
    }

    /**
     * 这个方法可以将时间字符串的特殊符号去除，然后使yyyyMMddHHmmss格式去得到日�?2008-11-1
     *
     * @param dateString
     * @return
     * @author yaoyuan
     */
    public static String replaceCharToEmpty(String dateString) {
        return dateString.replaceAll(":", "").replaceAll("-", "").replaceAll(
                "：", "").replaceAll("/", "").replaceAll(" ", "");
    }

    /**
     * 下一个月�?�?�?�?2009-2-11
     *
     * @param date
     * @return
     * @author yaoyuan
     */
    @SuppressWarnings("deprecation")
    public static Timestamp getNextMonthStart(Date date) {
        Calendar cal = new GregorianCalendar(date.getYear() + 1900, date
                .getMonth() + 1, 1);
        return new Timestamp(cal.getTimeInMillis());
    }

    /**
     * 下一年的第一�?�?�?�?2009-2-11
     *
     * @param date
     * @return
     * @author yaoyuan
     */
    @SuppressWarnings("deprecation")
    public static Timestamp getNextYearStart(Date date) {
        Calendar cal = new GregorianCalendar(date.getYear() + 1901, 0, 1);
        return new Timestamp(cal.getTimeInMillis());
    }


    /**
     * 调试工具,显示�?��代码执行耗费的时�?
     * <p>
     * 使用方法: 在代码开始处showTime("code"); 在代码结束处showTime("code"); �? showTime("code",
     * this);
     * <p>
     * var sum : int = 0; for(var i:int=0;i<100;i++){ sum += i; }
     * <p>
     * showTime("code", this);
     * <p>
     * 注意: 1、开始和结束处调用showTime的参数须�?��. 2、请在debug模式下使用该方法,
     * 3、结果将打印在控制台�?格式�?code:521ms 4、showFlag启用�?��，true:打开,false:关闭
     * 5、参数path�?��用this,标识代码段所在的文件
     *
     * @author wuliuhua 2009-07-15
     */
    private static final Boolean showFlag = true;// showTime启用�?��，true:打开,false:关闭
    private static final Map<String, Date> timeArray = new HashMap<String, Date>();// 存放时间

    public static void showTime(String flag, Object obj) {
        if (!showFlag) {
            return;
        }
        String temp = flag;
        if (obj != null) {
            temp = obj.getClass().getName() + " " + flag;
        }
        if (timeArray.containsKey(temp) && timeArray.get(temp) != null) {
            SysLog.info("showTime " + temp + " : " + (System.currentTimeMillis() - timeArray.get(temp).getTime()) + "ms");
            timeArray.remove(temp);
        } else {
            timeArray.put(temp, new Date());
        }
    }

    private static Date date;

    /**
     * 循环记时
     *
     * @param flag
     * @date 2009-9-15
     * @author wuliuhua
     */
    public static void cycleTime(String flag) {
        if (!showFlag) {
            return;
        }
        if (date != null) {
            SysLog.info("循环记时: " + flag + ":" + (System.currentTimeMillis() - date.getTime()));
        }
        date = new Date();
    }

    public static void clearTimeMap() {
        System.out.println("=========== showTime clear ============");
        timeArray.clear();
        date = new Date();
    }

    /**
     * 根据月份取当月最大天�?
     * <p>
     * 2011-3-10
     *
     * @param date
     * @return
     * @author yaoyuan
     */
    @SuppressWarnings("deprecation")
    public static int getMonthMaxmumDay(Date date) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.set(date.getYear(), date.getMonth(), date.getDate());
        return aCalendar.getActualMaximum(Calendar.DATE);
    }

    /**
     * 为某日期添加天数，查询多少天之后的日�?
     * <p>
     * 2011-4-8
     *
     * @param date
     * @param dayNum
     * @return Date
     * @author yaoyuan
     */
    public static Date addDayOfMonth(Date date, int dayNum) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, dayNum);
        return cal.getTime();
    }

    /**
     * �?yyyy-MM-dd'格式的字符串转化为日期对�?
     *
     * @param str qiaoliang
     *            格式化字符串
     * @return 日期对象
     */
    public static Date strToY_M_D(String str) {
        try {
            return Y_M_D.parse(str);
        } catch (Exception e) {
            SysLog.error(e);
            return null;
        }
    }

    /**
     * 时间 转 年月日字符串
     *
     * @param date 时间
     * @return 日期对象
     * @author huanggc
     * @date 2021/04/27
     */
    public static String dateToY_M_DString(Date date) {
        try {
            return Y_M_D.format(date);
        } catch (Exception e) {
            SysLog.error(e);
            return "";
        }
    }

    /**
     * 时间 转 年月日 字符串
     *
     * @param date 时间
     * @return 日期对象
     * @author huanggc
     * @date 2021/04/27
     */
    public static String dateToY_M_DStrings(Date date) {
        try {
            return Y_M_D_S.format(date);
        } catch (Exception e) {
            SysLog.error(e);
            return "";
        }
    }

    /**
     * �?yyyy-MM-dd'格式的字符串转化为日期对�?
     *
     * @param str qiaoliang
     *            格式化字符串
     * @return 日期对象
     */
    public static Date strToY_M_D_H(String str) {
        try {
            return Y_M_D_H.parse(str);
        } catch (Exception e) {
            SysLog.error(e);
            return null;
        }
    }

    /**
     * �?yyyy-MM-dd HH:mm:ss'格式的字符串转化为日期对�?
     *
     * @param str qiaoliang
     *            格式化字符串
     * @return 日期对象
     */
    public static Date strToY_M_D_H_M_S(String str) {
        try {
            return Y_M_D_H_M_S.parse(str);
        } catch (Exception e) {
            SysLog.error(e);
            return null;
        }
    }

    /**
     * �?yyyy-MM-dd'格式的字符串转化为日期对�?
     *
     * @param str qiaoliang
     *            格式化字符串
     * @return 日期对象
     */
    public static Date strToY_M(String str) {
        try {
            return Y_M.parse(str);
        } catch (Exception e) {
            SysLog.error(e);
            return null;
        }
    }

    /**
     * getCurrentHour
     * <p>方法说明:获取当前小时�?/p>
     *
     * @2011-7-1 覃江
     */
    public static String getCurrentHour() {
        String date = formatTime(new Date());
        String[] array = date.split(":");
        return array[0];
    }

    /**
     * @param @param  date
     * @param @return
     * @return int
     * @throws
     * @Title: getWeek
     * 得到当前周第几天，周一为第一天
     * @author wangzhiwen
     * @date 2015-12-14 下午02:41:56
     */
    public static int getWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;

    }

    /**
     * 计算两个日期之间相差的分钟
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int minuteBetween(Date smdate, Date bdate) {
        return (int) Math.ceil((double) (bdate.getTime() - smdate.getTime()) / (double) (1000 * 60));
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) {
        return (int) Math.ceil((double) (bdate.getTime() - smdate.getTime()) / (double) (1000 * 3600 * 24));
    }

    /**
     * 计算两个日期之间相差的小时
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int hoursBetween(Date smdate, Date bdate) {
        return (int) Math.ceil((double) (bdate.getTime() - smdate.getTime()) / (double) (1000 * 3600));
    }

    /**
     * 计算两个日期之间相差的月
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int monthsBetween(Date smdate, Date bdate) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(bdate);
        c2.setTime(smdate);
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值 
        int yearInterval = year1 - year2;
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
       /* if (month1 < month2 || month1 == month2 && day1 < day2) {
            yearInterval--;
        }
        // 获取月数差值
        int monthInterval = (month1 + 12) - month2;
        if (day1 >= day2) {
            monthInterval++;
        }
        monthInterval %= 12;
        int monthsDiff = (int)Math.ceil((double)yearInterval * 12 + (double)monthInterval);
        return monthsDiff;*/
        /*if (month1 <= month2 && day1 < day2) {
            yearInterval--;
        }*/
        int monthNum;
        if (yearInterval > 0) {
            monthNum = month1 + (yearInterval * 12) - month2 + 1;
        } else {
            monthNum = month1 - month2 + 1;
        }
        return monthNum;
    }

    /**
     * 计算两个日期之间相差的年
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     */
    public static int yearsBetween(Date smdate, Date bdate) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(bdate);
        c2.setTime(smdate);
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        // 获取年的差值 
        int yearInterval = year1 - year2;
        // 如果 d1的 月 大于 d2的 月 就加1年
        if (year1 > year2 || year1 == year2) {
            yearInterval++;
        }
        return yearInterval;
    }

    /**
     * 功能描述: 根据类型得到两个日期相差的年、月、日、小时
     *
     * @param type
     * @param beginTime
     * @param endTime
     * @return java.lang.Integer
     * @author wangzhiwen
     * @date 2019/10/23 11:49
     */
    public static Integer getDateTimeCount(int type, Date endTime, Date beginTime) {
        //endTime = reduce24Hours(endTime,-1);
        int limitCount = 10;
        if (1 == type) {
            limitCount = DateUtils.hoursBetween(beginTime, endTime);
        } else if (2 == type) {
            limitCount = DateUtils.daysBetween(beginTime, endTime);
        } else if (3 == type) {//计算月分
            limitCount = DateUtils.monthsBetween(beginTime, endTime);
        } else if (4 == type) {
            limitCount = DateUtils.yearsBetween(beginTime, endTime);
        }
        return (int) Math.ceil(limitCount);
    }

    /**
     * 功能描述: 计算两个时间之间相当的类型　1　小时　2日　3月　4年
     *
     * @param beginTime
     * @param endTime
     * @return java.lang.Integer
     * @author wangzhiwen
     * @date 2019/10/31 15:09
     */
    public static Integer getBetweenDateTimeType(Date beginTime, Date endTime) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(beginTime);
        c2.setTime(endTime);
        int type;
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);

        int differDays = (int) ((endTime.getTime() - beginTime.getTime()) / (24 * 3600 * 1000));

        if (year1 != year2 && differDays > 365) {
            type = 4;
        } else if (month1 != month2 && differDays > 7) {
            type = 3;
        } else if (day1 != day2) {
            type = 2;
        } else {
            type = 1;
        }
        return type;
    }

    /**
     * 功能描述: 返回时间的毫秒
     *
     * @param date
     * @return long
     * @author wangzhiwen
     * @date 2019/4/25 16:32
     */
    public static long getDateTime(Date date) {
        return date.getTime();
    }

    /**
     * 功能描述: 返回时间的毫秒
     *
     * @return long
     * @author wangzhiwen
     * @date 2019/4/25 16:32
     */
    public static long getDateTime() {
        return getDateTime(new Date());
    }

    public static String getFilePath() {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd/");
        return df.format(new Date());
    }

    /**
     * @param date Date
     * @return String
     */
    public static String getDateYearMonthDays(Date date) {
        return yyyyMMdd2.format(new Date());
    }

    /**
     * 功能描述: 把 yyyy-MM-dd 转换成 yyyy年MM月dd日
     *
     * @param date 时间
     */
    public static String getChangeDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(cal.getTime());
    }

    /**
     * 功能描述: 获取 近多少天, 如近30天
     * 根据当前时间，添加或减去指定的时间量。例如，要从当前日历时间减去 -5 天，可以通过调用以下方法做到这一点：
     * getBeforeOrAfterDate(Calendar.DAY_OF_MONTH, -5)。
     *
     * @param date 指定时间
     * @param num  为时间添加或减去的时间天数
     * @return
     * @author huangGuiChuan
     */
    public static Date getBeforeOrAfterDate(Date date, int num) {
        //SimpleDateFormat sdf = new SimpleDateFormat(yyyy-MM-dd);
        Calendar calendar = Calendar.getInstance();//获取日历
        if (null != date) {
            calendar.setTime(date);
        }
        calendar.add(Calendar.DATE, num);
        Date d = calendar.getTime();// 把日历转换为Date
        return d;
    }

    /**
     * 功能描述: 获取 某天的开始时间
     *
     * @param date 指定时间
     * @return Date  例: 2019-12-01 00:00:00
     * @author huangGuiChuan
     * @date 2019-12-04
     */
    public static Date startDayDate(Date date) {
        Calendar calendar = Calendar.getInstance();//获取日历
        if (null != date) {
            calendar.setTime(date);
        }
        calendar.set(Calendar.HOUR, 0);// 时
        calendar.set(Calendar.MINUTE, 0);// 分
        calendar.set(Calendar.SECOND, 0);// 秒
        Date startDate = calendar.getTime();// 把日历转换为Date
        return startDate;
    }

    /**
     * 功能描述: 获取 某天的结束时间
     *
     * @param date 指定时间
     * @return Date  例: 2019-12-01 23:59:59
     * @author huangGuiChuan
     * @date 2019-12-04
     */
    public static Date endDayDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (null != date) {
            calendar.setTime(date);
        }
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date endDate = calendar.getTime();
        return endDate;
    }

    /**
     * 加减月份 正数为加 负数为减
     *
     * @param date  Date
     * @param month 月
     * @return java.util.Date
     * @author wangzhiwen
     * @date 2020/9/10 16:06
     */
    public static Date getDateAddMonth(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        // 设置为当前时间
        calendar.setTime(strToY_M(Y_M.format(date)));
        // 设置为上一个月
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + month);
        return calendar.getTime();
    }

    /**
     * getDatePoor 计算两个时间相差多久
     *
     * @param endDate 结束时间
     * @param nowDate 时间
     * @param type    type
     * @return String
     * @author zhangZaiFa
     * @date 2019/9/27 13:58
     **/
    public static String getDatePoor(Date endDate, Date nowDate, int type) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;
        String countdownTime = "";
        if (type == 1) {
            return min + ":" + sec;
        }
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 根据分钟数计算：X天X小时X分钟
     *
     * @param minute 时间
     * @return java.lang.String
     * @author dwt
     * @date 2019-12-16 17:34
     */
    public static String getDayHourMin(Long minute) {
        String str = "";
        Long day = 0L;
        Long hour = 0L;
        if (minute >= 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        if (hour >= 24) {
            day = hour / 24;
            hour = hour % 24;
        }
        if (day > 0) {
            str = day + "天";
        }
        if (hour > 0) {
            str = str + hour + "小时";
        }
        if (minute > 0) {
            str = str + minute + "分钟";
        }
        return str;
    }

    /**
     * 判断时间字符串是否和当前时间同年同月同日等
     *
     * @param timeStr
     * @param pattern
     * @return boolean
     * @author dwt
     * @date 2020-03-05 9:37
     */
    public static boolean isSameCurrentDate(String timeStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        // 当前时间
        String now = sdf.format(new Date());
        if (timeStr.equals(now)) {
            return true;
        }
        return false;
    }

    /**
     * 获得本周一0点时间
     *
     * @return Date
     */
    public static Date getTimesWeekmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        // 2020-12-7 0:00:00
        return cal.getTime();
    }

    /**
     * 获得本周日24点时间
     *
     * @return Date
     */
    public static Date getTimesWeeknight() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimesWeekmorning());
        cal.add(Calendar.DAY_OF_WEEK, 7);
        // 2020-12-14 0:00:00
        return cal.getTime();
    }

    /**
     * 获取上周的开始时间
     *
     * @return Date
     */
    @SuppressWarnings("unused")
    public static Date getBeginDayOfLastWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek - 7);
        return getDayStartTime(cal.getTime());
    }

    /**
     * 获取上周的结束时间
     *
     * @return Date
     */
    public static Date getEndDayOfLastWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfLastWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    /**
     * 获取某个日期的开始时间
     *
     * @param date Date
     * @return Timestamp
     */
    public static Timestamp getDayStartTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (null != date) {
            calendar.setTime(date);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取某个日期的结束时间
     *
     * @param date Date
     * @return Timestamp
     */
    public static Timestamp getDayEndTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (null != date) {
            calendar.setTime(date);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 每月开始时间
     *
     * @param date 时间
     * @return Data
     * @author huanggc
     * @date 2019-11-25
     */
    public static Date monthStarDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);// 时
        cal.set(Calendar.MINUTE, 0);// 分
        cal.set(Calendar.SECOND, 0);// 秒
        // 毫秒
        cal.set(Calendar.MILLISECOND, 0);

        return strToY_M_D(formatDate(cal.getTime()));
    }

    /**
     * 每月结束时间
     *
     * @param date 时间,例: 2019-12-01
     * @return Data
     * @author huanggc
     * @date 2019-11-25
     */
    public static Date monthEndDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);// 时
        cal.set(Calendar.MINUTE, 59);// 分
        cal.set(Calendar.SECOND, 59);// 秒
        cal.add(Calendar.MONTH, +1);// 月份加一
        cal.add(Calendar.DATE, -1);// 日减一 (减一后是上个月的最后一天)
        // 毫秒
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    /**
     * 获取当年的开始时间戳
     *
     * @param timeStamp 毫秒级时间戳
     * @return Date
     */
    public static Date getYearStartTime(Long timeStamp) {
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        //calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        calendar.setTimeInMillis(timeStamp);
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.DATE, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    /**
     * 获取当年的最后时间戳
     *
     * @param timeStamp 毫秒级时间戳
     * @return Date
     */
    public static Date getYearEndTime(Long timeStamp) {
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        //calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        calendar.setTimeInMillis(timeStamp);
        int year = calendar.get(Calendar.YEAR);
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date time = calendar.getTime();
        Date parse = null;
        try {
            String format = Y_M_D_H_M_S.format(time);
            parse = Y_M_D_H_M_S.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }

    /**
     * 获取是第几季度
     *
     * @param date Date
     * @return int 第几个季度
     */
    public static int getQuarterByYearMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int m = cal.get(Calendar.MONTH) + 1;
        // 获取当前季度
        // 季度用的笨方法 获取了月份后 自己加的判断
        int quarter = 0;
        if (m <= 3) {
            quarter = 1;
        } else if (m <= 6) {
            quarter = 2;
        } else if (m <= 9) {
            quarter = 3;
        } else if (m <= 12) {
            quarter = 4;
        }
        return quarter;
    }

    private static void setMinTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private static void setMaxTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
    }

    /**
     * 获取上个季度的开始时间
     *
     * @return Date
     */
    public static Date getStarttQuarter() {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.MONTH, ((int) startCalendar.get(Calendar.MONTH) / 3 - 1) * 3);
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        setMinTime(startCalendar);
        return startCalendar.getTime();
    }

    /**
     * 获取上个季度的结束时间
     *
     * @return Date
     */
    public static Date getEndQuarter() {
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.MONTH, ((int) endCalendar.get(Calendar.MONTH) / 3 - 1) * 3 + 2);
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setMaxTime(endCalendar);
        return endCalendar.getTime();
    }

    /**
     * 判断2个时间
     *
     * @param date
     * @param date2
     * @param state 1:比较是否是同一个月，2:比较是否是同一天，3:比较是否是同一年
     * @return boolean
     */
    public static boolean matchSameMonth(Date date, Date date2, Integer state) {
        if (null == state) {
            return false;
        } else if (1 == state) {
            String str1 = Y_M.format(date);
            String str2 = Y_M.format(date2);
            if (str1.equals(str2)) {
                return true;
            }
        } else if (2 == state) {
            String str1 = Y_M_D.format(date);
            String str2 = Y_M_D.format(date2);
            if (str1.equals(str2)) {
                return true;
            }
        } else if (3 == state) {
            String str1 = Y.format(date);
            String str2 = Y.format(date2);
            if (str1.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 某月天数
     *
     * @param date
     * @return int
     */
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取 24小时的整点 --> 01:00  02:00
     *
     * @return ArrayList<String>
     */
    public static ArrayList<String> getDayOnTheHourList() {
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String s = df.format(day);
        Date date = null;
        try {
            date = df.parse(s);
        } catch (ParseException e) {
            SysLog.error(e);
        }

        ArrayList<String> dates = new ArrayList<>(24);
        for (int i = 0; i < 24; i++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.HOUR, i > 0 ? 1 : 0);
            date = cal.getTime();
            String s1 = HH_MM.format(date);
            dates.add(s1);
        }
        return dates;
    }

    /**
     * 获取 月的每天
     *
     * @return ArrayList<String>
     */
    public static ArrayList<String> getMonthOnTheHourList(Date date) {
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String s = df.format(day);
        try {
            date = df.parse(s);
        } catch (ParseException e) {
            SysLog.error(e);
        }

        int daysOfMonth = getDaysOfMonth(date);
        ArrayList<String> dates = new ArrayList<>(daysOfMonth);
        for (int i = 1; i <= daysOfMonth; i++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.DATE, i);
            date = cal.getTime();
            String s1 = Y_M_D.format(date);
            dates.add(s1);
        }
        return dates;
    }

    /**
     * 获取 年的每月 --> 2021-01, 2021-02, 2021-03
     *
     * @return ArrayList<String>
     */
    public static ArrayList<String> getYearOnTheHourList(Date date) {
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String s = df.format(day);
        try {
            date = df.parse(s);
        } catch (ParseException e) {
            SysLog.error(e);
        }

        ArrayList<String> dates = new ArrayList<>(12);
        for (int i = 0; i < 12; i++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.MONTH, i > 0 ? i : 0);
            date = cal.getTime();
            String s1 = Y_M.format(date);
            dates.add(s1);
        }
        return dates;
    }

    /**
     * 根据 年/月/日 获取时间
     *
     * @return ArrayList<String>
     */
    public static Date getDateByYearAndMonth(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        // 月从0开始计划0-11
        cal.set(Calendar.MONTH, month - 1);
        return cal.getTime();
    }
}
