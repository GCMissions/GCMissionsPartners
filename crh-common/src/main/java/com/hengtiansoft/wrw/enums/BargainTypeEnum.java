package com.hengtiansoft.wrw.enums;
/**
 * 
* Class Name: BargainTypeEnum
* Description: 砍价金额方式
* @author chenghongtu
*
 */
public enum BargainTypeEnum {

    RANDOM("0", "范围内随机"), FIXED("1", "固定金额");
    
    private String key;
    
    private String value;

    private BargainTypeEnum(String key, String value) {
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
        for (BargainTypeEnum type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }
    
    public static String getKey(String value) {
        for (BargainTypeEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type.getKey();
            }
        }
        return null;
    }
    
}
