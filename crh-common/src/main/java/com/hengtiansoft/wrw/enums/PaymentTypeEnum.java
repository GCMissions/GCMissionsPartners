package com.hengtiansoft.wrw.enums;

/**
* Class Name: PaymentTypeEnum
* Description: 支付方式类型
* @author changchen
*
*/
public enum PaymentTypeEnum {
    
    WECHAT_PUBLIC_PAY("0","微信支付"), UNIONPAY("1","连连支付");

    private String code;
    
    private String name;
    
    private PaymentTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public static String getName(String code) {
        for (PaymentTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type.getName();
            }
        }
        return null;
    } 
}
