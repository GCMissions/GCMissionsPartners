package com.hengtiansoft.wrw.enums;

import com.hengtiansoft.common.util.WRWUtil;

public enum SexEnum {
    
    MALE("0", "男"),
    FEMALE("1", "女");

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

    private SexEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }
    
    public static String getTextByCode(String code) {
        if (WRWUtil.isEmpty(code)) {
            return "";
        }
        for (SexEnum sex : values()) {
            if (code.equals(sex.getCode())) {
                return sex.getText();
            }
        }
        return "";
    }
}
