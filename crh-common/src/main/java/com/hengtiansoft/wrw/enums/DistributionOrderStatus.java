package com.hengtiansoft.wrw.enums;


public enum DistributionOrderStatus {
    
    WAIT_PAY("0", "待付款"),
    WAIT_TAKE("1", "待接单"),
    WAIT_SHIP("2", "待发货"),
    WAIT_RECEIPT("3", "已发货"),
    WATI_RATE("4", "待评价"),
    COMPLETE("5", "已完成"),
    CANCELED("6", "已取消"),
    WAIT_RETURNGOODS("8", "待退货"),
    WAIT_RETURNMONEY("9", "待退款"),
    COMPLETE_RETURNMONEY("10", "已退款");
    
    private String key;
    
    private String value;
    
    private DistributionOrderStatus(String key, String value) {
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
        for (DistributionOrderStatus type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }
    
    public static String getKey(String value) {
        for (DistributionOrderStatus type : values()) {
            if (type.getValue().equals(value)) {
                return type.getKey();
            }
        }
        return null;
    }

    public static DistributionOrderStatus getOrderStatus(String key){
        for(DistributionOrderStatus type : values()){
            if(type.getKey().equals(key)){
                return type;
            }
        }
        return null;
    }
}
