package com.hengtiansoft.wrw.enums;

public enum CouponState {
    
    INVALID("0", "未使用"), VALID("1", "使用中"), USED("2", "已使用"), EXPIRED("3", "已过期"),UNENABLED("4","未启用"),DELETE("9","已删除");
    
    private String key;
    
    private String value;
    
    private CouponState(String key, String value) {
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
        for (CouponState state : values()) {
            if (state.getKey().equals(key)) {
                return state.getValue();
            }
        }
        return null;
    }
}
