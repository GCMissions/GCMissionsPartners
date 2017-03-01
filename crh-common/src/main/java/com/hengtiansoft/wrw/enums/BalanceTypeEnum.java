package com.hengtiansoft.wrw.enums;

public enum BalanceTypeEnum {

    normal("0", "平台报表"), mail("1", "平台邮寄");

    private String key;

    private String value;

    private BalanceTypeEnum(String key, String value) {
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
    
    
}
