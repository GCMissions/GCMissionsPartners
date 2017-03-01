package com.hengtiansoft.wrw.enums;

public enum SRegionOpenFlagEnum {
	OPEN("1","开放城市"), NOT_OPEN("0", "非开放城市");
	private String code;
	private String desc;
	private SRegionOpenFlagEnum(String code, String desc) {
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
