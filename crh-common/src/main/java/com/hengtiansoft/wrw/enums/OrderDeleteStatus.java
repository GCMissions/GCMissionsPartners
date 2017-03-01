package com.hengtiansoft.wrw.enums;

public enum OrderDeleteStatus {

    DELETED("1", "已删除"), EXISTING("0", "未删除");

    private String key;

    private String value;

    private OrderDeleteStatus(String key, String value) {
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
        for (OrderDeleteStatus type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }

    public static String getKey(String value) {
        for (OrderDeleteStatus type : values()) {
            if (type.getValue().equals(value)) {
                return type.getKey();
            }
        }
        return null;
    }

    public static OrderDeleteStatus getOrderStatus(String key) {
        for (OrderDeleteStatus type : values()) {
            if (type.getKey().equals(key)) {
                return type;
            }
        }
        return null;
    }

}
