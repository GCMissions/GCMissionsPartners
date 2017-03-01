package com.hengtiansoft.wrw.enums;

/**
 * 
* Class Name: SaleFlagEnum
* Description: 商品的上下架状态
* @author chenghongtu
*
 */
public enum SaleFlagEnum {
    
    UNSHELF("0","未上架"), TO_SHELF("2", "待上架"),SHELF("1", "上架");
    
    private String code;
    
    private String text;

    private SaleFlagEnum(String code, String text) {
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
