package com.hengtiansoft.wrw.enums;

public enum AppProductTypeEnum {

    LY_PRODUCT("1", "吾儿乐园商品"),
    KD_PRODUCT("2", "吾儿酷袋商品");
    
    private String code;
    
    private String text;

    private AppProductTypeEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
