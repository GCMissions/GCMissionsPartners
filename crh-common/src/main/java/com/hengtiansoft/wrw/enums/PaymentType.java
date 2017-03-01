package com.hengtiansoft.wrw.enums;

public enum PaymentType {

    WECHAT("0", "微信支付"),
    LLPAY("1", "连连支付"),
    UNION("3", "银联支付");
    
    
    private String key;

    private String value;

    private PaymentType(String key, String value) {
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
        for (PaymentType type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }

}
