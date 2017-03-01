/**
 * 
 */
package com.hengtiansoft.wrw.enums;

/**
 * @author zhisongliu
 *
 */
public enum ShipFlagEnum {
	UNSHELF("0", "未上架"), SHELF("1", "上架");

    private String key;

    private String value;

    private ShipFlagEnum(String key, String value) {
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
        for (ShipFlagEnum type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }

    public static String getKey(String value) {
        for (ShipFlagEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type.getKey();
            }
        }
        return null;
    }

    public static ShipFlagEnum getStatusByValue(String key) {
        for (ShipFlagEnum type : values()) {
            if (type.getKey().equals(key)) {
                return type;
            }
        }
        return null;
    }

    public static ShipFlagEnum getStatusByKey(String value) {
        for (ShipFlagEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
}
