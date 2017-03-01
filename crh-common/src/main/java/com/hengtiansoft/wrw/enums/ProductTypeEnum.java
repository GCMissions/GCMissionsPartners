package com.hengtiansoft.wrw.enums;


public enum ProductTypeEnum {
    SERVICE(0L, "服务类商品"), GOOD(1L, "实物商品");

    private Long key;

    private String value;

    private ProductTypeEnum(Long key, String value) {
        this.key = key;
        this.value = value;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getValue(Long key) {
        for (ProductTypeEnum type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }

    public static Long getKey(String value) {
        for (ProductTypeEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type.getKey();
            }
        }
        return null;
    }

    public static ProductTypeEnum getStatusByValue(Long key) {
        for (ProductTypeEnum type : values()) {
            if (type.getKey().equals(key)) {
                return type;
            }
        }
        return null;
    }

    public static ProductTypeEnum getStatusByKey(String value) {
        for (ProductTypeEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
}
