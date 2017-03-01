package com.hengtiansoft.wrw.enums;


/**
* Class Name: BarcodeFlagEnum
* Description: 标识扫码终端Z
* @author xianghuang
*
*/
public enum BarcodeFlagEnum {
    
    FLASE("0", "不可以扫码"),
    TRUE("1", "可以扫码");
    
    private String key;
    
    private String value;
    
    private BarcodeFlagEnum(String key, String value) {
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
        for (BarcodeFlagEnum type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }
    
    public static String getKey(String value) {
        for (BarcodeFlagEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type.getKey();
            }
        }
        return null;
    }

    public static BarcodeFlagEnum getOrderStatus(String key){
        for(BarcodeFlagEnum type : values()){
            if(type.getKey().equals(key)){
                return type;
            }
        }
        return null;
    }
}
