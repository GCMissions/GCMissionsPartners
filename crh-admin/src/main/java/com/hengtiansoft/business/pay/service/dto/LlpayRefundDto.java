package com.hengtiansoft.business.pay.service.dto;

public class LlpayRefundDto {
    private String sign;        // 加密签名
    private String sign_type;   // RSA 或者 MD5
    private String oid_partner; // 商户编号
    private String dt_order;    // 商户订单时间
    private String no_order;    // 商户唯一订单号
    private String oid_paybill; // 连连钱包支付单号
    private String no_refund;
    private String dt_refund;
    private String money_refund;
    private String notify_url;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getOid_partner() {
        return oid_partner;
    }

    public void setOid_partner(String oid_partner) {
        this.oid_partner = oid_partner;
    }

    public String getDt_order() {
        return dt_order;
    }

    public void setDt_order(String dt_order) {
        this.dt_order = dt_order;
    }

    public String getNo_order() {
        return no_order;
    }

    public void setNo_order(String no_order) {
        this.no_order = no_order;
    }

    public String getOid_paybill() {
        return oid_paybill;
    }

    public void setOid_paybill(String oid_paybill) {
        this.oid_paybill = oid_paybill;
    }

    public String getNo_refund() {
        return no_refund;
    }

    public void setNo_refund(String no_refund) {
        this.no_refund = no_refund;
    }

    public String getDt_refund() {
        return dt_refund;
    }

    public void setDt_refund(String dt_refund) {
        this.dt_refund = dt_refund;
    }

    public String getMoney_refund() {
        return money_refund;
    }

    public void setMoney_refund(String money_refund) {
        this.money_refund = money_refund;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

}
