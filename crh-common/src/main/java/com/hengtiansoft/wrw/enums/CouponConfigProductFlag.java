package com.hengtiansoft.wrw.enums;

public enum CouponConfigProductFlag {

    SPEC("0", "指定商品可用"), ALL("1", "全品类");

    private String key;

    private String value;

    private CouponConfigProductFlag(String key, String value) {
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
    
    public static String getValueByKey(String key) {
        for (CouponConfigProductFlag falg : values()) {
            if (falg.getKey().equals(key)) {
                return falg.getValue();
            }
        }
        return null;
    }
}
