/**
 * 
 */
package com.hengtiansoft.wrw.enums;

/**
 * @author jiekaihu
 *
 */
public enum ParaSettingEnum {
	c_hotline("1", "客户端_服务热线"),
	c_orderTime("2", "客户端_配送时间_时间范围"),
	c_timeSpace("3", "客户端_配送时间_间隔分钟数"),
	c_orderDay("4", "客户端_配送时间_下单日范围"),
	remindTime("5", "平台系统_订单超时时间"),
	c_consume_poionts("6","购买获得积分"),
	c_serviceQQ("7","客户端_客服QQ"),
	overTimeOrderInterval("8","平台系统_超时订单轮询时间"),
	z_newOrderInterval("9","终端配送商_新订单轮询时间"),
	c_authServiceHint("10", "认证服务提示"),
    overTimeOrderTwiceInterval("11", "平台系统_催单后未接单时间"),
    overTimePunishAmount("12", "平台系统_未接单惩罚金额");
	
	private String key;

	private String value;

	private ParaSettingEnum(String key, String value) {
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
		for (ParaSettingEnum type : values()) {
			if (type.getKey().equals(key)) {
				return type.getValue();
			}
		}
		return null;
	}
}
