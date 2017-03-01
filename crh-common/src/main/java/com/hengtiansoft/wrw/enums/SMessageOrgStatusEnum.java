package com.hengtiansoft.wrw.enums;

/**
 * 
* Class Name: SMessageOrgStatusEnum
* Description: 站内信  是否删除   删除--禁用   未删除---启用
* @author chenghongtu
*
 */
public enum SMessageOrgStatusEnum {
    
    DISABLE("0","禁用"),ENABLED("1","启用");
    
    private String key;
    
    private String value;
    
    private SMessageOrgStatusEnum(String key, String value) {
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
