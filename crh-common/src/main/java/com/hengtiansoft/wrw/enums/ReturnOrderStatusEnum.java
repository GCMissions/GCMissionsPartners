package com.hengtiansoft.wrw.enums;


public enum ReturnOrderStatusEnum {
    
    WAIT_RETURNGOODS("1", "待退货"),
    WAIT_RETURNMONEY("2", "待退款"),
    COMPLETE_RETURNMONEY("3", "已退款");
    
    private String key;
    
    private String value;
    
    private ReturnOrderStatusEnum(String key, String value) {
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
        for (ReturnOrderStatusEnum type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }
    
    public static String getKey(String value) {
        for (ReturnOrderStatusEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type.getKey();
            }
        }
        return null;
    }

    public static ReturnOrderStatusEnum getOrderStatus(String key){
        for(ReturnOrderStatusEnum type : values()){
            if(type.getKey().equals(key)){
                return type;
            }
        }
        return null;
    }
}
