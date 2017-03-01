package com.hengtiansoft.wrw.enums;


public enum StockTypeEnum {
    
    SPEC("0", "按规格"),
    STOCK("1", "按总库存"),
    WITHOUT("2", "不需要库存");
    
    private String key;
    
    private String value;
    
    private StockTypeEnum(String key, String value) {
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
        for (StockTypeEnum type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }
    
}
