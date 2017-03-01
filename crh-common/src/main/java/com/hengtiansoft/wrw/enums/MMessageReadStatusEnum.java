package com.hengtiansoft.wrw.enums;

/**
 * @author huizhuang
 */
public enum MMessageReadStatusEnum {
  
    UNREAD("0","未读"), READ("1","已读");
    
    private String key;
    
    private String value;

    private MMessageReadStatusEnum(String key, String value) {
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
}
