package com.hengtiansoft.wrw.enums;

public enum AccountTypeEnum {
	CASH("1","现金账户");
	private String code;
	private String desc;
	public String getCode() {
		return code;
	}
	public String getDesc() {
		return desc;
	}
	private AccountTypeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
}
