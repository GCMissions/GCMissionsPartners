package com.hengtiansoft.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DateTimeUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateTimeUtil.class);

    public static final int DEFAULT_HOUR = 23;

    public static final int DEFAULT_MINUTE = 59;

    public static final int DEFAULT_SENCEND = 0;

    public static final int START_HOUR = 0;

    public static final int FREE_START_HOUR = 9;

    public static final int START_MINUTE = 0;

    public static final int START_SENCEND = 0;

    public final static String SIMPLE_FMT = "yyyy-MM-dd HH:mm:ss";

    public final static String SIMPLE_FMT_MINUTE = "yyyy-MM-dd HH:mm";

    public final static String SIMPLE_YMD = "yyyy-MM-dd";

    public final static String SIMPLE_YM = "yyyy-MM";

    public final static String SIMPLE_MD = "MM-dd";

    public final static String SIMPLE_MILLS = "yyyyMMddHHmmssSSS";

    public final static String SIMPLE_SECONDS = "yyyyMMddHHmmss";

    public final static String SIMPLE_FMT_MILLS = "yyyy-MM-dd HH:mm:ss：SSS";

    public final static String EN_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";

    public final static String MONTH_DAY_FORMAT = "MMdd";

    public final static String SIMPLE_DATE_YMD = "yyyyMMdd";

    public final static String SIMPLE_DATE_YM = "yyyyMM";

    public final static String SIMPLE_TIME_HMS = "HHmmss";

    public final static String CHINA_YMD = "yyyy年MM月dd日";

    public final static String SIMPLE_M_D = "yyyy-M-d HH:mm";

    public final static String SIMPLE_M_D_H_M_S = "yyyy-M-d HH:mm:ss";

    public final static String YYYY_M_D = "yyyy-M-d";

    public final static String FOREIGN_FORMAT = "MM-dd-yyyy HH:mm";

    /**
     * Date to String
     *
     * @param date
     * @param sFmt
     *            Time mode:yyyy-MM-dd
     * @return
     */
    public static String parseDateToString(Date date, String sFmt) {
        return date == null ? null : new SimpleDateFormat(sFmt).format(date);
    }

    public static SimpleDateFormat getSimpleFormatTimeAll() {
        return new SimpleDateFormat(SIMPLE_FMT);
    }

    /**
     * Description:Converts the date type date to a String type
     * 
     * @param date
     * @return
     */
    public static String dateFormat(Date date) {
        return parseDateToString(date, SIMPLE_FMT);
    }

    /**
     * Description:Converts the date type date to a String type
     * 
     * @param date
     * @return
     */
    public static String monthDayFormat(Date date) {
        return parseDateToString(date, MONTH_DAY_FORMAT);
    }

    /**
     * Description:Converts the date type date to a String type
     * 
     * @param date
     * @return
     */
    public static String dateFormatToChinaYMD(Date date) {
        return parseDateToString(date, CHINA_YMD);
    }

    /**
     * Description:Converts the string type date to a date type
     * 
     * @param dateString
     * @return
     */
    public static Date dateParse(String dateString) {
        return parseDate(dateString, SIMPLE_FMT);
    }

    /**
     * Force conversion type from string to date
     *
     * @param sDate
     *            Source string, using "yyyy-MM-dd" format
     * @param sFormat
     * @return
     */
    public static Date parseDate(String sDate, String sFormat) {
        try {
            if (StringUtils.isEmpty(sDate)) {
                return null;
            }

            return new SimpleDateFormat(sFormat).parse(sDate);
        } catch (ParseException e) {
            LOGGER.warn("parseDate error:" + e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Description:The incoming TimeStamp type date is converted to a String type
     * 
     * @param timestamp
     * @return
     */
    public static String timeStampFormat(Timestamp timestamp) {
        return new SimpleDateFormat(SIMPLE_YMD).format(timestamp);
    }

    /**
     * Description:The incoming TimeStamp type date is converted to a String type
     * 
     * @param timestamp
     * @return
     */
    public static String timeStampFormatYYYYMMDDHHMMSS(Timestamp timestamp) {
        return new SimpleDateFormat(SIMPLE_FMT).format(timestamp);
    }

    /**
     * Description:The incoming String type date is converted to a TimeStamp type
     * 
     * @param ts
     * @return
     */
    public static Timestamp timeStampParse(String ts) {
        return Timestamp.valueOf(dateFormat(parseDate(ts, SIMPLE_YMD)));
    }

    public static Timestamp timeStampParse(String ts, String fmt) {
        return Timestamp.valueOf(dateFormat(parseDate(ts, fmt)));
    }

    /**
     * Converts the time of the string type to another format
     * 
     * @param dateString
     * @param fromFormat
     * @param toFormat
     * @return
     */
    public static String dateFormatParse(String dateString, String fromFormat, String toFormat) {
        return parseDateToString(parseDate(dateString, fromFormat), toFormat);
    }

    /**
     * Description:Get the current date
     * 
     * @return
     */
    public static String getNow() {
        return dateFormat(new Date());
    }

    public static String getNowForMills() {
        return parseDateToString(new Date(), SIMPLE_MILLS);
    }

    public static String getNowForYmd() {
        return parseDateToString(new Date(), SIMPLE_YMD);
    }

    public static String getNowForSampleYmd() {
        return parseDateToString(new Date(), SIMPLE_DATE_YMD);
    }

    public static String getNowForSampleYm() {
        return parseDateToString(new Date(), SIMPLE_DATE_YM);
    }

    public static String getTodayShort() {
        return parseDateToString(new Date(), "yyyy/MM/dd");
    }

    /**
     * Get the formatting date of <code>num</code> days after today
     * 
     * @param format
     *            Formatted characters
     * @param num
     *            The number of days apart
     * @return
     */
    public static String getDayAfterToday(String format, int num) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, num);
        return parseDateToString(calendar.getTime(), format);
    }

    public static String getTomorrowShort() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return parseDateToString(new Date(calendar.getTimeInMillis()), "yyyy/MM/dd");
    }

    /**
     * Verify that the specified time has timed out
     * 
     * @param createDate
     * @param seconds
     *            More than the number of seconds
     * @return true Represents overdue，false That is not overdue
     */
    public static boolean validateExpiry(String createDate, int seconds) {
        return (new Date().getTime() - parseDate(createDate, SIMPLE_FMT).getTime()) / 1000 > seconds;
    }

    public static String getNowFormat(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static String getDayBegin(String day) {
        if (day == null || !day.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Incoming date parameter is incorrect");
        }
        String[] dateData = day.split("-");
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, Integer.parseInt(dateData[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(dateData[1]) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateData[2]));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return parseDateToString(new Date(calendar.getTimeInMillis()), SIMPLE_FMT);
    }

    public static Date getDayBegin() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getDayEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public static Date getDayBegin(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getDayEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public static Date getDateBegin(String dateStr) {
        Date date = parseDate(dateStr, SIMPLE_YMD);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getDateEnd(String dateStr) {
        Date date = parseDate(dateStr, SIMPLE_YMD);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    // Take the current time after 10 years
    public static Date getDateEndAddYear(int year) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public static Date getMonthBegin(String dateStr) {
        Date date = parseDate(dateStr, SIMPLE_YM);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getMonthEnd(String dateStr) {
        Date date = parseDate(dateStr, SIMPLE_YM);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static String getDayEnd(String day) {
        if (day == null || !day.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Incoming date parameter is incorrect, the format should be yyyy-MM-dd");
        }
        String[] dateData = day.split("-");
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, Integer.parseInt(dateData[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(dateData[1]) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateData[2]));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        return parseDateToString(new Date(calendar.getTimeInMillis()), SIMPLE_FMT);
    }

    public static Timestamp getNowTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * Description: Set the end time of the day
     * 
     * @param date
     * @return
     */
    public static Date setDefaultEndDate(Date date) {
        date = DateUtils.setHours(date, DEFAULT_HOUR);
        date = DateUtils.setMinutes(date, DEFAULT_MINUTE);
        date = DateUtils.setSeconds(date, DEFAULT_MINUTE);
        return date;
    }

    /**
     * Description: Set the start time of the day
     * 
     * @param date
     * @return
     */
    public static Date setDefaultStartDate(Date date) {
        date = DateUtils.setHours(date, START_HOUR);
        date = DateUtils.setMinutes(date, START_MINUTE);
        date = DateUtils.setSeconds(date, START_MINUTE);
        return date;
    }

    /**
     * an hour later date format
     * 
     * @param dateString
     *            Date string format： MMddHH
     * @param foramt
     * 
     * @return
     */
    public static String getNextHour(String dateString, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, Integer.parseInt(parseDateToString(new Date(), "yyyy")));
        calendar.set(Calendar.MONTH, Integer.parseInt(dateString.substring(0, 2)) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateString.substring(2, 4)));
        calendar.set(Calendar.HOUR, Integer.parseInt(dateString.substring(4, 6)));

        calendar.add(Calendar.HOUR, 1);
        return parseDateToString(calendar.getTime(), format);
    }

    /**
     * an hour later date format
     * 
     * @param dateString
     *            Date string format： yyyyMMddHH
     * @param foramt
     * 
     * @return
     */
    public static String getNextHour2(String dateString) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, Integer.parseInt(dateString.substring(0, 4)));
        calendar.set(Calendar.MONTH, Integer.parseInt(dateString.substring(4, 6)) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateString.substring(6, 8)));
        calendar.set(Calendar.HOUR, Integer.parseInt(dateString.substring(8, 10)));

        calendar.add(Calendar.HOUR, 1);
        return parseDateToString(calendar.getTime(), "yyyyMMddHH");
    }

    /**
     * Description: Get the current year string
     * 
     * @return
     */
    public static String getFormatDateString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(new Date());
    }

    /**
     * Description: Get the current year string
     * 
     * @return
     */
    public static Long getDiffDays(Date begin, Date end) {
        return (end.getTime() - begin.getTime()) / 3600 / 24 / 1000;
    }

    /**
     * Description: Get the formatted string
     * 
     * @param date
     * @param formatter
     * @return
     */
    public static String getFormatDate(Date date, String formatter) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatter);
        return simpleDateFormat.format(date);
    }

    public static String getWeekOfDate(Date dt) {
        String[] weekDays = { "7", "1", "2", "3", "4", "5", "6" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * Description: Get the timestamp of the order
     *
     * @return
     */
    public static Long getOrderTimeStamp() {
        return new Long(parseDateToString(new Date(), SIMPLE_DATE_YMD));

    }

    /**
     * Description : To determine whether the two dates are the same day
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static Boolean isSameDate(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);
        if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                && c1.get(Calendar.DATE) == c2.get(Calendar.DATE)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Description: Get the current time
     *
     * @return
     */
    public static String getCurrentTime(String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(new Date());
    }
}
