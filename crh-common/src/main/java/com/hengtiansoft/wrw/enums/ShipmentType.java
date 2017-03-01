package com.hengtiansoft.wrw.enums;

/**
 * 
 * Class Name: ShippingType Description: 配送方式
 * 
 * @author kangruan
 *
 */
public enum ShipmentType {
    express("1", "邮寄"), delivery("2", "配送"), oneself("3", "自提");

    private String key;

    private String value;

    private ShipmentType(String key, String value) {
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
        for (ShipmentType type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }

    public static String getKey(String value) {
        for (ShipmentType type : values()) {
            if (type.getValue().equals(value)) {
                return type.getKey();
            }
        }
        return null;
    }

    public static ShipmentType getStatusByValue(String key) {
        for (ShipmentType type : values()) {
            if (type.getKey().equals(key)) {
                return type;
            }
        }
        return null;
    }

    public static ShipmentType getStatusByKey(String value) {
        for (ShipmentType type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
}
