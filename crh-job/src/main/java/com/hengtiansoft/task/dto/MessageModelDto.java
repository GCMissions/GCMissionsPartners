/**
 * 
 */
package com.hengtiansoft.task.dto;

import java.io.Serializable;

/**
 * @author jiekaihu
 *
 */
public class MessageModelDto implements Serializable {

	private static final long serialVersionUID = 3002567221456059256L;

	/**
	 * 获取验证码（短信模板）
	 */
	private String sms_C_getCaptcha;                 
	/**
     * 成功下单提醒-会员（有商品验证码）
     */
	private String sms_C_sendOrderANotify;  
	/**
     * 成功下单提醒-会员（无商品验证码）
     */
    private String sms_C_sendOrderBNotify;  
    /**
     * 成功支付提醒-服务商
     */
    private String sms_S_sendNewOrderNotify;  
    /**
     * 退款提醒-会员
     */
    private String sms_C_refundNotify;  
    /**
     * 退款提醒-服务商
     */
    private String sms_S_refundNotify;  
    /**
     * 短信模板_优惠券发送提醒
     */
    private String sms_C_sendCouponNoitfy;  
	
    public String getSms_C_getCaptcha() {
        return sms_C_getCaptcha;
    }
    public void setSms_C_getCaptcha(String sms_C_getCaptcha) {
        this.sms_C_getCaptcha = sms_C_getCaptcha;
    }
    public String getSms_C_sendOrderANotify() {
        return sms_C_sendOrderANotify;
    }
    public void setSms_C_sendOrderANotify(String sms_C_sendOrderANotify) {
        this.sms_C_sendOrderANotify = sms_C_sendOrderANotify;
    }
    public String getSms_C_sendOrderBNotify() {
        return sms_C_sendOrderBNotify;
    }
    public void setSms_C_sendOrderBNotify(String sms_C_sendOrderBNotify) {
        this.sms_C_sendOrderBNotify = sms_C_sendOrderBNotify;
    }
    public String getSms_S_sendNewOrderNotify() {
        return sms_S_sendNewOrderNotify;
    }
    public void setSms_S_sendNewOrderNotify(String sms_S_sendNewOrderNotify) {
        this.sms_S_sendNewOrderNotify = sms_S_sendNewOrderNotify;
    }
    public String getSms_C_refundNotify() {
        return sms_C_refundNotify;
    }
    public void setSms_C_refundNotify(String sms_C_refundNotify) {
        this.sms_C_refundNotify = sms_C_refundNotify;
    }
    public String getSms_S_refundNotify() {
        return sms_S_refundNotify;
    }
    public void setSms_S_refundNotify(String sms_S_refundNotify) {
        this.sms_S_refundNotify = sms_S_refundNotify;
    }
    public String getSms_C_sendCouponNoitfy() {
        return sms_C_sendCouponNoitfy;
    }
    public void setSms_C_sendCouponNoitfy(String sms_C_sendCouponNoitfy) {
        this.sms_C_sendCouponNoitfy = sms_C_sendCouponNoitfy;
    }

	
}
