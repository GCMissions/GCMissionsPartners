package com.hengtiansoft.task.enumeration;


/**
 * 
* Class Name: DataStatus
* Description: 数据状态
* @author huizhuang
*
 */
public enum MessageEnum {
    INSERT("1", "新增"), UPDATE("2", "更新"), INVALID("3", "非法"), DEALED("0", "已处理");     

    private String key;

    private String value;

    private MessageEnum(String key, String value) {
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
        for (MessageEnum status : values()) {
            if (status.getKey().equals(key)) {
                return status.getValue();
            }
        }
        return null;
    }
}
