package com.hengtiansoft.wrw.enums;


public enum AdUrlFlagEnum {

    INNER("0", "内部链接"), OUTTER("1", "外部链接");

    private String code;

    private String text;

    private AdUrlFlagEnum(String code, String text) {
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
    	 for (AdUrlFlagEnum type : values()) {
             if (type.getCode().equals(code)) {
                 return type.getText();
             }
         }
         return null;
    }
    public static String getCodeByText(String text){
   	 for (AdUrlFlagEnum type : values()) {
            if (type.getText().equals(text)) {
                return type.getCode();
            }
        }
        return null;
   }
}
