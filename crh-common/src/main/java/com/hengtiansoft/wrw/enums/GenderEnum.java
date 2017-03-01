package com.hengtiansoft.wrw.enums;

public enum GenderEnum {
    
    MALE("M","男性"),FEMALE("F","女性"),SECRET("S","保密");
    
    private String code;
    
    private String value;
    
    private GenderEnum(String code,String value){
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public static String getValue(String code) {
        for (GenderEnum gender : values()) {
            if (gender.getCode().equals(code)) {
                return gender.getValue();
            }
        }
        return null;
    }
}
