package com.hengtiansoft.common.jpush.enums;

/**
 * Jpush消息中，extras map字段的key
 * 
 * @author huizhuang
 */
public enum JpushExtraKeyEnum {
    
    MESSAGE_TYPE("1", "type"),
    MESSAGE_ID("2", "messageId"),
    ORDER_ID("3", "orderId"),
    ORDER_STATUS("4", "orderStatus");
    
    private String key;
    
    private String value;
    
    private JpushExtraKeyEnum(String key, String value) {
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
        for (JpushExtraKeyEnum type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }
}
