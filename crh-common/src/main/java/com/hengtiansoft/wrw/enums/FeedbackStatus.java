package com.hengtiansoft.wrw.enums;

public enum FeedbackStatus {

    UNDEALED("0", "未处理"), DEALED("1", "已处理");

    private String key;

    private String value;

    private FeedbackStatus(String key, String value) {
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
        for (FeedbackStatus status : values()) {
            if (status.getKey().equals(key)) {
                return status.getValue();
            }
        }
        return null;
    }

}
