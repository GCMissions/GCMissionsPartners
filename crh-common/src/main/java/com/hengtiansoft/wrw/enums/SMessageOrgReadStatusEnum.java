package com.hengtiansoft.wrw.enums;

/**
 * 
* Class Name: ReadStatusEnum
* Description: 站内信的读取状态
* @author chenghongtu
*
 */
public enum SMessageOrgReadStatusEnum {
    UNREAD("0","未读"),READ("1","已读");
     
    private String code;
    
    private String text;

    private SMessageOrgReadStatusEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
    
    

}
