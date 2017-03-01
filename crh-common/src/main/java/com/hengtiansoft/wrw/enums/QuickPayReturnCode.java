package com.hengtiansoft.wrw.enums;

/**
 * 
* Class Name: QuickPayStatus
* Description: 市民卡支付返回状态码
* @author yigesong
*
 */
public enum QuickPayReturnCode {

    SUCCESS("00", "成功"), FAILED("55", "失败"), SIGN_ERROR("2000", "签名错误"), NO_ORDER("6000", "查无此单"), NO_SUPPORT_CART("8000", "不支持该银行卡"), CART_TYPE_ERROR("8001", "银行卡类型");

    private String code;
    private String desc;

    private QuickPayReturnCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
