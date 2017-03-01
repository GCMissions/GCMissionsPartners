package com.hengtiansoft.wrw.enums;

public enum VipOrderStatusEnum {
    
    WAIT_PAY("1", "待支付"),
    PAYED("2", "已支付");

    String code;
    
    String text;

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

    private VipOrderStatusEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }
}
