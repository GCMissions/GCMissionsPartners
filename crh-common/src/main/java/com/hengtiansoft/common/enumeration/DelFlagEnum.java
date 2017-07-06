
package com.hengtiansoft.common.enumeration;

/**
 * 
* Class Name: DelFlagEnum
* Description: Whether to delete
* @author taochen
*
 */
public enum DelFlagEnum {
    DEL("0","Deleted"),UN_DEL("1","Not deleted");
    private String code;
    private String text;
    
    private DelFlagEnum(String code,String text){
        this.code=code;
        this.text=text;
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
    
    public static String getText(String code){
        for(DelFlagEnum delFlagEnum :DelFlagEnum.values()){
            if(delFlagEnum.getCode().equals(code)){
                return delFlagEnum.getText();
            }
        }
        return null;
    }
}
