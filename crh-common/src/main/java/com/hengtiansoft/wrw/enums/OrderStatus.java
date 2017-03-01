package com.hengtiansoft.wrw.enums;


public enum OrderStatus {
    
    WAIT_PAY("0", "待支付"),
    COMPLETE_PAY("1", "已支付"),
    WAIT_START("2", "待出行"),
    WATI_RATE("2_1", "待点评"),
    COMPLETE("3", "已点评"),
    CANCELED("4", "已取消"),
    COMPLETE_RETURNMONEY("6", "已退款"),
    RETURN_TOTAL("6.1", "全部退款"),
    RETURN_APART("6.2", "部分退款");
    
    private String key;
    
    private String value;
    
    private OrderStatus(String key, String value) {
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
        for (OrderStatus type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }
    
    public static String getKey(String value) {
        for (OrderStatus type : values()) {
            if (type.getValue().equals(value)) {
                return type.getKey();
            }
        }
        return null;
    }

    public static OrderStatus getOrderStatus(String key){
        for(OrderStatus type : values()){
            if(type.getKey().equals(key)){
                return type;
            }
        }
        return null;
    }
    
    public static OrderStatus[] getOrderStatusValues(){
        return new OrderStatus[]{WAIT_PAY,
                WATI_RATE,
                COMPLETE,
                CANCELED,
                COMPLETE_RETURNMONEY};
    }
  
}
