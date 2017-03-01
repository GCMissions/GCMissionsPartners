package com.hengtiansoft.wrw.enums;

public enum KdOrderStatusEnum {

    WAIT_PAY("0", "待支付"),
    WAIT_DELIVERY("1", "待发货"),
    WAIT_RECEIVE("2", "待收货"),
    WATI_RATE("3", "已完成"),
    CANCELED("5", "已取消"),
    COMPLETE_RETURNMONEY("6", "已退款"),
    WAIT_TEAM("7", "已支付待成团");
    
    private String code;
    private String text;
    
    private KdOrderStatusEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }
    
    public static String getText(String code) {
        for (KdOrderStatusEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type.getText().trim();
            }
        }
        return null;
    }
    
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
