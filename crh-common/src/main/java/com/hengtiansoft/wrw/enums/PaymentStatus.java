package com.hengtiansoft.wrw.enums;

/**
 * 
 * Class Name: PaymentStatus
 * Description: 订单支付状态
 * @author kangruan
 *
 */
public enum PaymentStatus {
    unpaid("1","待支付"),
    paid("2","已支付");
    
    private String key;
    
    private String value;
    
    private PaymentStatus(String key, String value) {
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
        for (PaymentStatus type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }
    
    public static String getKey(String value) {
        for (PaymentStatus type : values()) {
            if (type.getValue().equals(value)) {
                return type.getKey();
            }
        }
        return null;
    }

    public static PaymentStatus getStatusByValue(String key) {
        for (PaymentStatus type : values()) {
            if (type.getKey().equals(key)) {
                return type;
            }
        }
        return null;
    }
    
    public static PaymentStatus getStatusByKey(String value) {
        for (PaymentStatus type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
}
