/**
 * 
 */
package com.hengtiansoft.wrw.enums;

/**
 * @author jiekaihu
 *
 */
public enum MessageModelEnum {
   
    sms_C_getCaptcha("1", "短信模板_获取验证码"),
    sms_C_sendOrderANotify("2", "短信模板_成功下单提醒（有商品验证码）"),
    sms_C_sendOrderBNotify("3", "短信模板_成功下单提醒（无商品验证码）"),
    sms_S_sendNewOrderNotify("4", "短信模板_成功支付提醒"),
    sms_C_refundNotify("5", "短信模板_退款提醒（用户）"),
    sms_C_refundNotify_org("29", "短信模板_退款提醒（商家）"),
    sms_C_sendCouponNoitfy("33", "短信模板_优惠券发送提醒"),
    sms_C_KdProductRefundNotify("36", "短信模板_酷袋退款提醒（普通商品）"),
    sms_C_KdActRefundNotify("37", "短信模板_酷袋活动退款提醒");
    
	private String key;

	private String value;

	private MessageModelEnum(String key, String value) {
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
		for (MessageModelEnum type : values()) {
			if (type.getKey().equals(key)) {
				return type.getValue();
			}
		}
		return null;
	}
}
