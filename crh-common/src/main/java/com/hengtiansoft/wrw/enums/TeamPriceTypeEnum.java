package com.hengtiansoft.wrw.enums;

public enum TeamPriceTypeEnum {

    FOLLOW_PRICE("0","按照总的团购信息"),FOLLOW_SPEC("1","按照规格");
    
    private String key;
    
    private String value;
    
    private TeamPriceTypeEnum(String key,String value){
        
        this.key = key;
        this.value= value;
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
