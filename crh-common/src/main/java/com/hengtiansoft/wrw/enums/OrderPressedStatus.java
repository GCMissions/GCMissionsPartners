package com.hengtiansoft.wrw.enums;

/**
 * 
 * Class Name: OrderPressedStatus
 * Description: 订单催单状态枚举
 * @author kangruan
 *
 */
public enum OrderPressedStatus {
    UNPRESSED("0", "未催单"),
    PRESSED("1", "已催单");
    
    private String key;
    
    private String value;
    
    private OrderPressedStatus(String key, String value) {
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
        for (OrderPressedStatus type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }
    
    public static String getKey(String value) {
        for (OrderPressedStatus type : values()) {
            if (type.getValue().equals(value)) {
                return type.getKey();
            }
        }
        return null;
    }
}
