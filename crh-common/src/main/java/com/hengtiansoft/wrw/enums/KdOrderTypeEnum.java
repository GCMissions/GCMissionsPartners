package com.hengtiansoft.wrw.enums;

/**
 * 
* Class Name: KdOrderTypeEnum
* Description: 酷袋订单类型
* @author yiminli
*
 */
public enum KdOrderTypeEnum {

    OFFLINE("0", "线下订单"),NORMAL("1", "普通订单"),TEAM_BUY("2", "团购订单"),TWENTY_FOUR("3", "24小时订单");
    
    private String code;
    private String text;
    
    private KdOrderTypeEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }
    
    public static String getText(String code) {
        for (KdOrderTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type.getText();
            }
        }
        return null;
    }
    
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
