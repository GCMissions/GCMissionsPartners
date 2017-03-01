package com.hengtiansoft.common.enumeration;

@Deprecated
public enum BasicParaEnum {
    REGISTER_POINTS(1, "注册积分值"), CONSUME_POINTS(2, "消费积分比");

    private BasicParaEnum(Integer key, String name) {
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
