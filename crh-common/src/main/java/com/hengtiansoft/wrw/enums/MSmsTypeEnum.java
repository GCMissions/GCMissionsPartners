package com.hengtiansoft.wrw.enums;

public enum MSmsTypeEnum {
    REGISTER("1", "注册短信"), BALANCE_VERTIFY("2", "余额使用验证"), RESET_PASSWORD("3", "重置密码"), ORDER_TRANSFER("4", "订单转移");

    private String key;

    private String value;

    private MSmsTypeEnum(String key, String value) {
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
        for (MSmsTypeEnum state : values()) {
            if (state.getKey().equals(key)) {
                return state.getValue();
            }
        }
        return null;
    }
}
