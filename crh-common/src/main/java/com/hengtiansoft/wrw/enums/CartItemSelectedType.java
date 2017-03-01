package com.hengtiansoft.wrw.enums;

public enum CartItemSelectedType {
	notSelected("0"), selected("1");
	String code;

	
	private CartItemSelectedType(String code) {
		this.code = code;
	}


	public String getCode() {
		return code;
	}

}
