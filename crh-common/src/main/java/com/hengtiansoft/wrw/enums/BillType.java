package com.hengtiansoft.wrw.enums;

public enum BillType {
    
    SHIP_AMOUNT("0", "运费"),
    COUPON("1", "优惠券"),
    BALANCE("2", "余额"),
    ALIPAY("3", "支付宝"),
    WECHAT("4", "微信支付"),
    UNION_PAY("5", "银联"),
    QUICK_PAY("6","市民卡"),
    IB_PAY("7","兴业银行"),
    LIANYIJIA("8", "连亿家");
    
    private String key;
    
    private String value;
    
    private BillType(String key, String value) {
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
        for (BillType type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }
    
    public static String getKey(String value) {
        for (BillType type : values()) {
            if (type.getValue().equals(value)) {
                return type.getKey();
            }
        }
        return null;
    }
}
