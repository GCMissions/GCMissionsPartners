package com.hengtiansoft.wrw.enums;

public enum SpecTypeEnums {
    
    
    ALL_SPEC("0", "全规格"), SUB_SPEC("1", "选择规格");
    
    
    private String code;
    
    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private SpecTypeEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
