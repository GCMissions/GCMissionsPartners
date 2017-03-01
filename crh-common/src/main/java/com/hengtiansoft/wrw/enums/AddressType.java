package com.hengtiansoft.wrw.enums;

public enum AddressType {
	DEFAULT("1"), ALTERNATIVE("0");
	private String code;

	public String getCode() {
		return code;
	}

	private AddressType(String code) {
		this.code = code;
	}
}
