package com.hengtiansoft.wrw.enums;

/**
 * 
 * Class Name: ShopGroupStatus
 * Description: 团购订单状态 枚举
 * @author kangruan
 *
 */
public enum ShopGroupStatus {
    undeal("1","未处理"),
    deal("2","已处理");
    
    private String key;
    
    private String value;
    
    private ShopGroupStatus(String key, String value) {
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
        for (ShopGroupStatus type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }
    
    public static String getKey(String value) {
        for (ShopGroupStatus type : values()) {
            if (type.getValue().equals(value)) {
                return type.getKey();
            }
        }
        return null;
    }

    public static ShopGroupStatus getStatusByValue(String key) {
        for (ShopGroupStatus type : values()) {
            if (type.getKey().equals(key)) {
                return type;
            }
        }
        return null;
    }
    
    public static ShopGroupStatus getStatusByKey(String value) {
        for (ShopGroupStatus type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
}
