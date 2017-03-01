package com.hengtiansoft.wrw.enums;

public enum TeamRecordStatusEnum {

    RECORD_FULL("1","已满员"),RECORD_NOTFULL("0","未满员");
    
    private String key;
    
    private String code;
    
    private TeamRecordStatusEnum(String key,String code){
        
        this.key = key;
        this.code = code;
        
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
