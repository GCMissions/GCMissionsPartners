package com.hengtiansoft.wrw.enums;
/**
 * 
* Class Name: MSmStatusEnum
* Description: 短信发送状态枚举
* @author yigesong
*
 */
public enum MSmStatusEnum {
    UNSEND("0", "未发送"), SEND("1", "已发送");

    private String key;

    private String value;

    private MSmStatusEnum(String key, String value) {
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
        for (MSmStatusEnum state : values()) {
            if (state.getKey().equals(key)) {
                return state.getValue();
            }
        }
        return null;
    }
}
