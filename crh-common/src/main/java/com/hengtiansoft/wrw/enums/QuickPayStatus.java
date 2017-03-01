package com.hengtiansoft.wrw.enums;

/**
 * 
* Class Name: QuickPayStatus
* Description: 市民卡支付的状态
* @author yigesong
*
 */
public enum QuickPayStatus {

    NO_DEAL("00", "未处理"), SUCCESS("01", "成功"), FAILED("02", "失败"), DEALING("03", "处理中"), CANCEL("04", "交易取消"),NO_ORDER_ID("6000","查无此单");

    private String code;
    private String desc;

    private QuickPayStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
