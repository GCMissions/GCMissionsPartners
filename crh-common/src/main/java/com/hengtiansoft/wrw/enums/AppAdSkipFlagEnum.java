package com.hengtiansoft.wrw.enums;

/**
 * app活动广告是否允许跳过flag
* Class Name: AppAdSkipFlagEnum
* Description: TODO
* @author fengpan
*
 */
public enum AppAdSkipFlagEnum {
    
    allowed("0", "允许"),
    forbidden("1", "禁止");
    
    private String key;
    
    private String value;
    
    private AppAdSkipFlagEnum(String key, String value) {
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
        for (AppAdSkipFlagEnum type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }
    
}
