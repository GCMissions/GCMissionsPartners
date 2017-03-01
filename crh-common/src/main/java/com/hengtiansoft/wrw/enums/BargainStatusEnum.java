package com.hengtiansoft.wrw.enums;
/**
 * 
* Class Name: BargainStatusEnum
* Description: 砍价活动状态
* @author chenghongtu
*
 */
public enum BargainStatusEnum {

    NOSTART("0", "未开始"), START("1", "进行中"), OVER("2", "已结束");
    
    private String key;
    
    private String value;

    private BargainStatusEnum(String key, String value) {
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
        for (BargainStatusEnum type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }
    
    public static String getKey(String value) {
        for (BargainStatusEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type.getKey();
            }
        }
        return null;
    }
    
}
