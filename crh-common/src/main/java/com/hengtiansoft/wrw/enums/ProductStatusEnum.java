package com.hengtiansoft.wrw.enums;

public enum ProductStatusEnum {
    USED("1", "启用"), DELETE("0", "失效");

    private String code;

    private String text;

    private ProductStatusEnum(String code, String text) {
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
         for (ProductStatusEnum type : values()) {
             if (type.getCode().equals(code)) {
                 return type.getText();
             }
         }
         return null;
    }
    public static String getCodeByText(String text){
     for (ProductStatusEnum type : values()) {
            if (type.getText().equals(text)) {
                return type.getCode();
            }
        }
        return null;
   }

}
