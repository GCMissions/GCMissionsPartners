package com.hengtiansoft.wrw.enums;

/**
 * 
* Class Name: KdOrderOperTypeEnum
* Description: 酷袋订单操作类型
* @author yiminli
*
 */
public enum KdOrderOperTypeEnum {
    
    DELIVERY("0", "确认发货"),CANCEL("1", "取消订单"),REFUND("2", "退款"),EDITOR("3", "修改订单金额"),ADD_COMMENT("4", "添加备注"),AUTO_RECEIVE("5", "自动确认收货");

    private String code;
    private String text;
    
    private KdOrderOperTypeEnum(String code, String text) {
        this.code = code;
        this.text = text;
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
