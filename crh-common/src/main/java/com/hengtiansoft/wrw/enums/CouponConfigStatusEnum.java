package com.hengtiansoft.wrw.enums;

public enum CouponConfigStatusEnum {
    
    INVALID("0", "无效"),
    EFFECTIVE("1", "有效"),
    NOT_USED("2", "未启用");

    private String code;
    
    private String text;

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

    private CouponConfigStatusEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }
    
    public static String getTextByCode(String code) {
        for (CouponConfigStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status.getText();
            }
        }
        return null;
    }
}
