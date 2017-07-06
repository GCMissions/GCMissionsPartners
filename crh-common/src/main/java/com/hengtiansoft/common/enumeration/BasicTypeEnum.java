package com.hengtiansoft.common.enumeration;

@Deprecated
public enum BasicTypeEnum {
    POINTS(1, "Configure Points");

    private BasicTypeEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }

    private Integer key;

    private String name;

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
