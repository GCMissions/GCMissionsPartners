package com.hengtiansoft.wrw.enums;

public enum KdAdvertiseTypeEnum {

    TYPE_ONE("1", "模式一"),
    TYPE_TWO("2", "模式二");
    
    private String code;
    private String text;
    
    public static String getText(String code) {
        for (KdAdvertiseTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type.getText();
            }
        }
        return null;
    }
    
    private KdAdvertiseTypeEnum(String code, String text) {
        this.code = code;
        this.text = text;
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
