package com.hengtiansoft.wrw.enums;

public enum BasicParaEnum {

    APP_ACTIVITY_COUNTDOWN_SECONDS(51, "app活动广告倒计时时间"), APP_ACTIVITY_CAN_SKIP(52, "app活动广告允许跳过flag"),
    ORDER_TIME_RANGE(33, "下单时间范围"), SHIP_DAY_PERIOD(35, "配送天数间隔"), SHIP_TIME_PERIOD(34, "配送间隔分钟数");

    private Integer key;

    private String  value;

    private BasicParaEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getValue(Integer key) {
        for (BasicParaEnum type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }

    public static Integer getKey(String value) {
        for (BasicParaEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type.getKey();
            }
        }
        return null;
    }
}
