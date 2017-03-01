package com.hengtiansoft.wrw.enums;

public enum ActTypeEnum {
    
    GROUP_BUY("0", "团购"),
    TWENTY_FOUR("1", "24小时"),
    CHARITY("2", "公益");

    private String key;
    private String value;
    
    private ActTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }
    
    public static String getValue(String key) {
        for(ActTypeEnum type : values()) {
            if(type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
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
