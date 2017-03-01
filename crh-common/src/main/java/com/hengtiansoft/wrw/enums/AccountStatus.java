package com.hengtiansoft.wrw.enums;

public enum AccountStatus {
    
    INVALID("0", "失效"),
    VALID("1", "生效"),
    FREEZE("2", "冻结");
    
    private String key;
    
    private String value;
    
    private AccountStatus(String key, String value) {
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
        for (AccountStatus type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }
    
}
