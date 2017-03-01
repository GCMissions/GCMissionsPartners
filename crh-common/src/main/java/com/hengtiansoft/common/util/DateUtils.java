/*
 * Project Name: wrw-web
 * File Name: JsonUtils.java
 * Class Name: JsonUtils
 *
 * Copyright 2014 Hengtian Software Inc
 *
 * Licensed under the Hengtiansoft
 *
 * http://www.hengtiansoft.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dogndongliu
 */
public final class DateUtils {

    public static final String STRING_PATTERN_FULL = "yyyy-MM-dd HH:mm:ss";

    public static final String STRING_PATTERN_SHORT = "yyyy-MM-dd";

    public static final String DATE_PATTERN_FULL = "yyyyMMddHHmmss";

    public static final String DATE_PATTERN_YMD = "yyyyMMdd";
    
    public static final String DATE_PATTERN_YMDH = "yyyyMMddHH";

    public static final String DATE_PATTERN_HHMMSS = "HHmmss";

    public static final String DATE_PATTERN_HHMMSS_SSS = "HHmmssSSS";

    public static final String BETWEEN_DAY = "betweenDay";

    public static final String BETWEEN_HOUR = "betweenHour";

    public static final String BETWEEN_MIN = "betweenMin";

    public static final String BETWEEN_SEC = "betweenSec";

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    /**
     * 不可实例化
     */
    private DateUtils() {
    }

    /**
     * 获取Date型当前日期
     * 
     * @return
     */
    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }
    
    /**
     * 获取Date型当前月份
     * 
     * @return
     */
    public static int getCurrentMonth() {
        Calendar c = new GregorianCalendar();
        return c.get(Calendar.MONTH) + 1; 
    }

    /**
     * @param date
     * @return
     */
    public static String format(Date date) {
        FastDateFormat df = FastDateFormat.getInstance(STRING_PATTERN_FULL, null, null);
        return df.format(date);
    }

    /**
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        FastDateFormat df = FastDateFormat.getInstance(pattern, null, null);
        return df.format(date);
    }

    /**
     * 将传入的Date型日期转化为pattern类型的日期(默认使用yyyy-MM-dd HH:mm:ss格式)
     * 
     * @param date
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date getFormatDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            date = sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            LOGGER.warn("ParseException ", e);
        }
        return date;
    }

    /**
     * 将传入的字符串日期转化为指定格式的Date型日期(默认使用yyyy-MM-dd HH:mm:ss格式)
     * 
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date getFormatDateByString(String dateStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            LOGGER.warn("ParseException ", e);
        }
        return date;
    }

    /**
     * 获取两个时间间差的时间
     * 
     * @param biggerDate
     * @param smallerDate
     * @return
     * @throws XylException
     */
    public static Map<String, Integer> getBetweenDateTime(Date biggerDate, Date smallerDate) throws Exception {
        if (biggerDate == null || smallerDate == null) {
            throw new Exception("Arguments could not be null.");
        }
        if (biggerDate.getTime() < smallerDate.getTime()) {
            throw new Exception("The first argument must be smaller than the second.");
        }
        long l = biggerDate.getTime() - smallerDate.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long sec = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put(BETWEEN_DAY, (int) day);
        map.put(BETWEEN_HOUR, (int) hour);
        map.put(BETWEEN_MIN, (int) min);
        map.put(BETWEEN_SEC, (int) sec);

        return map;
    }
    
    /**
     * 获取某日期的月份
     * 
     * @return
     */
    public static int getDateMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1; 
    }
    
    /**
     * 获取某日期的日
     * 
     * @return
     */
    public static int getDateDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DATE); 
    }
    
    /**
     * 获得所在月的上个月的第一天和最后一天.
     * @param currentDate
     * @return
     */
    public static List<Date> getStartDateAndEndDateInPreviousMonth(Date currentDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
        Date startDate = cal.getTime();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), days, 23, 59, 59);
        Date endDate = cal.getTime();
        List<Date> dates = new ArrayList<Date>();
        dates.add(startDate);
        dates.add(endDate);
        return dates;
    }
    
    /** 
     * 获得指定日期的后一天 
     *  
     * @param specifiedDay 
     * @return 
     */  
    public static Date getSpecifiedDayAfter(Date specifiedDay) {  
//        List<Date> list = getStartDateAndEndDateInPreviousMonth(new Date());
        Calendar c = Calendar.getInstance();  
        c.setTime(specifiedDay); 
//        System.out.println(DateUtils.format(list.get(1), DateUtils.STRING_PATTERN_SHORT));;
        int day = c.get(Calendar.DATE);  
        c.set(Calendar.DATE, day + 1);  
        return c.getTime();  
    }  
    
}
