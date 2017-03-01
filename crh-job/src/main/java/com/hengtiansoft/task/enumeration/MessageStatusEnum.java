package com.hengtiansoft.task.enumeration;


/**
 * 
* Class Name: MessageReturnEnum
* Description: 数据状态
* @author yigesong
*
 */
public enum MessageStatusEnum {
    SEND("1", "已发送"), UNSEND("0", "未发送");     

    private String key;

    private String value;

    private MessageStatusEnum(String key, String value) {
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
        for (MessageStatusEnum status : values()) {
            if (status.getKey().equals(key)) {
                return status.getValue();
            }
        }
        return null;
    }
}
