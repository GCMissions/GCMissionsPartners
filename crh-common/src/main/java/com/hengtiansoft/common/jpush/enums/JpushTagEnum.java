package com.hengtiansoft.common.jpush.enums;

public enum JpushTagEnum {
    
    ALL("1", "发给所有接收方"),MEMBER("2","MEMBER");
    
    private String key;
    
    private String value;
    
    private JpushTagEnum(String key, String value) {
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
        for (JpushTagEnum type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }
}
