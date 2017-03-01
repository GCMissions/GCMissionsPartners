package com.hengtiansoft.wrw.enums;

public enum PAttributeSearchableEnum {
	searchable("1"), notSearchable("0");
	private String code;

	public String getCode() {
		return code;
	}

	private PAttributeSearchableEnum(String code) {
		this.code = code;
	}
	
}
