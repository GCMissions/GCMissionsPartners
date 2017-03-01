package com.hengtiansoft.wrw.enums;


/**
* Class Name: ReturnOrderTypeEnum
* Description: 退货类型
* @author xianghuang
*
*/
public enum ReturnOrderTypeEnum {
    NORECEIVE__RETURNGOODS("0", "Z发货C未收货申请退款"),
    RECEIVED__RETURNGOODS("1", "收货后申请退货"),
    CANCELED_RETURNMONEY("2", "Z已分配申请退款"),
    RETURNMONEY("3", "申请退款");
    
    private String key;
    
    private String value;
    
    private ReturnOrderTypeEnum(String key, String value) {
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
        for (ReturnOrderTypeEnum type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }
    
    public static String getKey(String value) {
        for (ReturnOrderTypeEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type.getKey();
            }
        }
        return null;
    }

    public static ReturnOrderTypeEnum getOrderStatus(String key){
        for(ReturnOrderTypeEnum type : values()){
            if(type.getKey().equals(key)){
                return type;
            }
        }
        return null;
    }
}
