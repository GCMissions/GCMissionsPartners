package com.hengtiansoft.wrw.enums;

public enum MMessageTypeEnum {
   
    SYSTEM("1", "系统消息"),
    ORDER("2", "订单消息");

    private MMessageTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }
    
    private String key;

    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public static String getValue(String key) {
        for (MMessageTypeEnum type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }
}
