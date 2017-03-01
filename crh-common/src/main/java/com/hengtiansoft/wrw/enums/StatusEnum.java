package com.hengtiansoft.wrw.enums;


public enum StatusEnum {
    NORMAL("1", "启用"), DELETE("0", "禁用"),UNENABLED("2","禁用"),REMOVED("3","已删除");

    private String code;

    private String text;

    private StatusEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
    
    public static String getTextByCode(String code){
    	 for (StatusEnum type : values()) {
             if (type.getCode().equals(code)) {
                 return type.getText();
             }
         }
         return null;
    }
    public static String getCodeByText(String text){
   	 for (StatusEnum type : values()) {
            if (type.getText().equals(text)) {
                return type.getCode();
            }
        }
        return null;
   }
}
