/**
 * 
 */
package com.hengtiansoft.business.setting.dto;

import java.io.Serializable;

/**
 * @author jiekaihu
 *
 */
public class ParaSettingDto implements Serializable {

	private static final long serialVersionUID = 1311960382293593725L;
	
	// 客户端参数
	private String c_hotline;                 // 客服热线
	private String c_orderTimeStart;          // 消费者下单时间范围（起）
	private String c_orderTimeEnd;            // 消费者下单时间范围（止）
	private String c_timeSpace;               // 配送间隔分钟数
	private String c_orderDay;                // 消费者天数
	private String c_serviceQQ;               // 客服QQ
	private String c_authServiceHint;         // 认证服务提示
	
	// 平台参数
	private String remindTime;                // 订单超时时间（单位：分）
	private String overTimeOrderInterval;     // 超时订单轮询时间（单位：秒）
	private String overTimeOrderTwiceInterval;// 催单后未接单时间（单位:分）
	private String overTimePunishAmount;      // 未接单惩罚 / 接单奖励金额（单位：分）
	
	// 终端配送商参数
	private String z_newOrderInterval;        // 新订单轮询时间（单位：秒）
	
	// APP版本参数
    private String appType_android;           // app类型
    private String appVersion_android;        // 版本号
    private String appDownLoadUrl_android;    // 下载地址
    private String appForceUpdate_android;    // 是否强制更新
    private String appUpdateDesc_android;     // 更新说明
    
    //app广告参数
    private String appAdCountDown;          // app活动广告倒计时 （单位：秒）
    private String appAdSkipFlag;           // app活动广告跳过标志  0-允许   1-禁止    对应枚举 AppAdSkipFlagEnum
   
    public String getC_hotline() {
        return c_hotline;
    }
    
    public void setC_hotline(String c_hotline) {
        this.c_hotline = c_hotline;
    }
    
    public String getC_orderTimeStart() {
        return c_orderTimeStart;
    }
    
    public void setC_orderTimeStart(String c_orderTimeStart) {
        this.c_orderTimeStart = c_orderTimeStart;
    }
    
    public String getC_orderTimeEnd() {
        return c_orderTimeEnd;
    }
    
    public void setC_orderTimeEnd(String c_orderTimeEnd) {
        this.c_orderTimeEnd = c_orderTimeEnd;
    }
    
    public String getC_timeSpace() {
        return c_timeSpace;
    }
    
    public void setC_timeSpace(String c_timeSpace) {
        this.c_timeSpace = c_timeSpace;
    }
    
    public String getC_orderDay() {
        return c_orderDay;
    }
    
    public void setC_orderDay(String c_orderDay) {
        this.c_orderDay = c_orderDay;
    }
    
    public String getC_serviceQQ() {
        return c_serviceQQ;
    }
    
    public void setC_serviceQQ(String c_serviceQQ) {
        this.c_serviceQQ = c_serviceQQ;
    }
    
    public String getC_authServiceHint() {
        return c_authServiceHint;
    }
    
    public void setC_authServiceHint(String c_authServiceHint) {
        this.c_authServiceHint = c_authServiceHint;
    }
    
    public String getRemindTime() {
        return remindTime;
    }
    
    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }
    
    public String getOverTimeOrderInterval() {
        return overTimeOrderInterval;
    }
    
    public void setOverTimeOrderInterval(String overTimeOrderInterval) {
        this.overTimeOrderInterval = overTimeOrderInterval;
    }
    
    public String getOverTimeOrderTwiceInterval() {
        return overTimeOrderTwiceInterval;
    }
    
    public void setOverTimeOrderTwiceInterval(String overTimeOrderTwiceInterval) {
        this.overTimeOrderTwiceInterval = overTimeOrderTwiceInterval;
    }
    
    public String getOverTimePunishAmount() {
        return overTimePunishAmount;
    }

    public void setOverTimePunishAmount(String overTimePunishAmount) {
        this.overTimePunishAmount = overTimePunishAmount;
    }

    public String getZ_newOrderInterval() {
        return z_newOrderInterval;
    }
    
    public void setZ_newOrderInterval(String z_newOrderInterval) {
        this.z_newOrderInterval = z_newOrderInterval;
    }
    
    public String getAppType_android() {
        return appType_android;
    }
    
    public void setAppType_android(String appType_android) {
        this.appType_android = appType_android;
    }
    
    public String getAppVersion_android() {
        return appVersion_android;
    }
    
    public void setAppVersion_android(String appVersion_android) {
        this.appVersion_android = appVersion_android;
    }
    
    public String getAppDownLoadUrl_android() {
        return appDownLoadUrl_android;
    }
    
    public void setAppDownLoadUrl_android(String appDownLoadUrl_android) {
        this.appDownLoadUrl_android = appDownLoadUrl_android;
    }
    
    public String getAppForceUpdate_android() {
        return appForceUpdate_android;
    }
    
    public void setAppForceUpdate_android(String appForceUpdate_android) {
        this.appForceUpdate_android = appForceUpdate_android;
    }
    
    public String getAppUpdateDesc_android() {
        return appUpdateDesc_android;
    }
    
    public void setAppUpdateDesc_android(String appUpdateDesc_android) {
        this.appUpdateDesc_android = appUpdateDesc_android;
    }
    
    public String getAppAdCountDown() {
        return appAdCountDown;
    }

    public void setAppAdCountDown(String appAdCountDown) {
        this.appAdCountDown = appAdCountDown;
    }

    public String getAppAdSkipFlag() {
        return appAdSkipFlag;
    }

    public void setAppAdSkipFlag(String appAdSkipFlag) {
        this.appAdSkipFlag = appAdSkipFlag;
    }

    @Override
    public String toString() {
        return "ParaSettingDto [c_hotline=" + c_hotline + ", c_orderTimeStart=" + c_orderTimeStart + ", c_orderTimeEnd="
                + c_orderTimeEnd + ", c_timeSpace=" + c_timeSpace + ", c_orderDay=" + c_orderDay + ", c_serviceQQ="
                + c_serviceQQ + ", c_authServiceHint=" + c_authServiceHint + ", remindTime=" + remindTime
                + ", overTimeOrderInterval=" + overTimeOrderInterval + ", overTimeOrderTwiceInterval="
                + overTimeOrderTwiceInterval + ", overTimePunishAmount=" + overTimePunishAmount
                + ", z_newOrderInterval=" + z_newOrderInterval + ", appType_android=" + appType_android
                + ", appVersion_android=" + appVersion_android + ", appDownLoadUrl_android=" + appDownLoadUrl_android
                + ", appForceUpdate_android=" + appForceUpdate_android + ", appUpdateDesc_android="
                + appUpdateDesc_android + ", appAdCountDown" + appAdCountDown + ", appAdSkipFlag" + appAdSkipFlag + "]";
    }
}
