package com.hengtiansoft.business.activity.dto;

import java.util.Calendar;

/**
 * Class Name: TimeSelDto
 * Description: 选择活动时间
 * @author zhongyidong
 *
 */
public class TimeSelDto {
    
    private Integer year;
    
    private Integer month;
    
    private Integer day;
    /**
     * 当前月的天数
     */
    private Integer days;
    
    public TimeSelDto() {
        Calendar calendar = Calendar.getInstance();
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH) + 1;
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        // 设为当月的第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        // 日期回滚一天，即最后一天
        calendar.roll(Calendar.DAY_OF_MONTH, -1);
        this.days = calendar.get(Calendar.DAY_OF_MONTH);
    }
    
    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public Integer getMonth() {
        return month;
    }
    public void setMonth(Integer month) {
        this.month = month;
    }
    public Integer getDay() {
        return day;
    }
    public void setDay(Integer day) {
        this.day = day;
    }
    public Integer getDays() {
        return days;
    }
    public void setDays(Integer days) {
        this.days = days;
    }
}
