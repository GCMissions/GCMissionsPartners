package com.hengtiansoft.wrw.enums;

public enum AcctRechargePayStatus {
    UNPAID("0","待支付"),
    PAID("1","已支付"),
    CANCELED("2", "已取消");
    
    private String key;
    
    private String value;
    
    private AcctRechargePayStatus(String key, String value) {
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
        for (AcctRechargePayStatus type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }
}
