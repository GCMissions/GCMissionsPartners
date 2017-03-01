package com.hengtiansoft.business.activity.dto;

/**
 * Class Name: ActivityDateDto
 * Description: 活动时间
 * @author zhongyidong
 *
 */
public class ActivityDateDto {

    /**
     * 活动时间
     */
    private String actDate;
    
    /**
     * 按总库存时修改的库存量
     */
    private Integer count;

    public String getActDate() {
        return actDate;
    }

    public void setActDate(String actDate) {
        this.actDate = actDate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    
}
