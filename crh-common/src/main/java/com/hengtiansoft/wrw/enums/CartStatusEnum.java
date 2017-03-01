package com.hengtiansoft.wrw.enums;

/**
 * @author huizhuang
 */
public enum CartStatusEnum {

    VALID("1", "生效"), BOUGHT("2", "已购买");

    private String key;

    private String value;

    private CartStatusEnum(String key, String value) {
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
