package com.hengtiansoft.wrw.enums;

public enum BillStatus {
    
    UNPAID("1", "未支付"),
    PAID("2", "已支付");
    
    private String key;
    
    private String value;
    
    private BillStatus(String key, String value) {
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
        for (BillStatus type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }
}
