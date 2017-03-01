package com.hengtiansoft.wrw.enums;

public enum AcctRecordType {

    SPEND("1", "消费"),
    RECHARGE("2", "充值"),
    RETURNORDER("3", "退款");
    
    private String key;
    
    private String value;
    
    private AcctRecordType(String key, String value) {
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
        for (AcctRecordType type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }

}
