package com.hengtiansoft.wrw.enums;

public enum CodeUsedEnum {

    UN_USED("0", "未验证"),
    USED("1", "已验证"),
    INVALID("2", "已失效");

    private String key;
    
    private String value;
    
    private CodeUsedEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }
    
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
        for (CodeUsedEnum type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }
    
    public static String getKey(String value) {
        for (CodeUsedEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type.getKey();
            }
        }
        return null;
    }

}
