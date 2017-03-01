package com.hengtiansoft.wrw.enums;

public enum OrderExceptionEnum {
    
    NORMAL("0", "非异常"),
    ABNORMAL("1", "异常"),
    
    NO_EXIST_COUPON("2","所选优惠券不存在，请重新下单！"),
    TOTAL_INVALID_COUPON("3","所选优惠券已失效，请重新下单！"),
    TO_MUCH_COUPON("4","优惠券只能选择一张，请重新下单！"),
    TIME_INVALID_COUPON("5","优惠券使用时间已失效，请重新下单！"),
    NO_PRODUCT_ORDER("6","订单暂无商品信息，请重新下单！"),
    UN_COMPLETE__ORDER("7","下单信息不完整，请重新下单！"),
    INVALID_STOCK_ORDER("8","订单商品库存信息失效，请重新下单！"),
    RESTRICTION_ERROR_PRODUCT("9","已选商品数量大于商品限购数量，请重新下单！"),
    SHORTAGE_STOCK_ORDER("10","订单商品库存不足，请重新下单！"),
    PRICE_ERROR_PRODUCT("11","订单商品价格失效，请重新下单！");
    
    private String key;
    
    private String value;
    
    private OrderExceptionEnum(String key, String value) {
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
        for (OrderExceptionEnum type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }

}
