package com.hengtiansoft.wrw.enums;

/**
 * 
* Class Name: QuickPayMethd
* Description: 商户快捷支付的方法代码枚举
* @author yigesong
*
 */
public enum QuickPayMethd {

    PAY_ONLY("QP0004", "单笔支付"), PAY("QP0005", "签约并支付"), QUERY("QP0006", "单笔查询"),SEND_MSG("QP0001", "下发签约短信"),CARD_BIND("QP0002","绑定卡片"), CARD_BIND_INFO("QP0008", "客户绑卡信息查询"), CARD_TYPE("QP0010", "2.10  银行卡信息查询");

    private QuickPayMethd(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private String key;

    private String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
