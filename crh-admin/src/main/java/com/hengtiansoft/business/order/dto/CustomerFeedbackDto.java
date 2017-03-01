package com.hengtiansoft.business.order.dto;

import java.util.Date;

import com.hengtiansoft.common.util.DateTimeUtil;

public class CustomerFeedbackDto {
    private Long feedbackId;
    private String name;
    private String phone;
    private String address;
    private String feedInfo;
    private String feedStatus;
    private String date;
    
    //f.FEEDBACK_ID,m.MEMBER_NAME,m.LOGIN_ID,a.ADDRESS,f.FEED_INFO,f.STATUS,f.CREATE_DATE
    public CustomerFeedbackDto(Long feedbackId,String memberName,String loginId,String mergerName,String address,String areaName,String feedInfo,String status,Date createDate) {
        this.feedbackId = feedbackId;
        this.name = memberName;
        this.phone = loginId;
        this.address = mergerName + " " + areaName + " " + address;
        this.feedInfo = feedInfo;
        this.feedStatus = status;
        this.date = DateTimeUtil.parseDateToString(createDate,DateTimeUtil.SIMPLE_FMT_MINUTE);
    }
    public Long getFeedbackId() {
        return feedbackId;
    }
    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getFeedInfo() {
        return feedInfo;
    }
    public void setFeedInfo(String feedInfo) {
        this.feedInfo = feedInfo;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getFeedStatus() {
        return feedStatus;
    }
    public void setFeedStatus(String feedStatus) {
        this.feedStatus = feedStatus;
    }
    
}
