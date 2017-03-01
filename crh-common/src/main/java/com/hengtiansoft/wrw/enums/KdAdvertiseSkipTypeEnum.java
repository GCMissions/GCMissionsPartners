package com.hengtiansoft.wrw.enums;

public enum KdAdvertiseSkipTypeEnum {

    FEATURE("0", "专辑", "关联专辑"),
    VIP("1", "专享", "关联专享"),
    OTHER("2", "其他", "关联其他");
    
    private String code;
    private String text;
    private String desc;
    
    private KdAdvertiseSkipTypeEnum(String code, String text, String desc) {
        this.code = code;
        this.text = text;
        this.desc = desc;
    }
    
    public static String getText(String code) {
        for (KdAdvertiseSkipTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type.getText();
            }
        }
        return null;
    }
    
    public static String getDesc(String code) {
        for (KdAdvertiseSkipTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type.getDesc();
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
