package com.hengtiansoft.common.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Class Name: DateSplitUtil
 * Description: 日期分割器
 * @author zhongyidong
 *
 */
public class DateSplitUtil {
    
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    
    public static final long ONE_DAY = 24 * 60 * 60 * 1000;
    
    public static  Calendar cal = Calendar.getInstance(); 

    /**
     * Description: 将时间段进行分隔
     *
     * @param startDate
     * @param endDate
     * @return 日期列表
     * @throws Exception
     */
    public static List<Date> dateSplit(Date startDate, Date endDate) throws Exception {  
        if (startDate.after(endDate))  {
            throw new Exception("开始时间应该在结束时间之后");  
        }
        
        Long spi = endDate.getTime() - startDate.getTime();  
        Long step = (spi / ONE_DAY);  
        System.out.println("相差天数：" + step);
        
        List<Date> dateList = new ArrayList<Date>();  
        for (int i = 0; i <= step.intValue(); i++) {  
            dateList.add(new Date(startDate.getTime() + ONE_DAY * i));  
        }  
        return dateList;  
    }  
  
    /**
     * Description: 获取当月的天数
     *
     * @param date
     * @return
     */
    public static Integer getDaysOfMonth(Date date) {  
        cal.setTime(date);
        // 设为当月的第一天
        cal.set(Calendar.DAY_OF_MONTH, 1);
        // 日期回滚一天，即最后一天
        cal.roll(Calendar.DAY_OF_MONTH, -1);
        
        return cal.get(Calendar.DAY_OF_MONTH);  
    }  
  
    /**
     * Description: 获取两个日期之间的月份之差
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer getDiffOfDate(Date startDate, Date endDate) {
        if (startDate.after(endDate)) {
            return null;
        }
        cal.setTime(startDate);
        Integer startYear = cal.get(Calendar.YEAR);
        Integer startMonth = cal.get(Calendar.MONTH);
        
        cal.setTime(endDate);
        Integer endYear = cal.get(Calendar.YEAR);
        Integer endMonth = cal.get(Calendar.MONTH);
        
        Integer diff = 0;
        if (startYear.equals(endYear)) {
            // 如果在同一年，直接相减
            diff = endMonth - startMonth;
        } else {
            // 反之，用12减去开始月份再加上结束月份
            diff = 12 - startMonth + endMonth;
        }
        return diff;
    }
    
}
