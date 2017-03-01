package com.hengtiansoft.wrw.enums;
/**
 * 
* Class Name: KdBargainStatusEnum
* Description: 24小时活动状态
* @author chengchaoyin
*
 */
public enum KdBargainStatusEnum {

    ACT_NOSTART("2", "未开始"), ACT_ACTING("3", "进行中"), ACT_ENDACT("4",
            "已结束"),PRODUCT_NOT_SALE("5", "商品已下架");
    
    private String key;
    
    private String value;

    private KdBargainStatusEnum(String key, String value) {
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
        for (KdBargainStatusEnum type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }
    
    public static String getKey(String value) {
        for (KdBargainStatusEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type.getKey();
            }
        }
        return null;
    }
    
}
