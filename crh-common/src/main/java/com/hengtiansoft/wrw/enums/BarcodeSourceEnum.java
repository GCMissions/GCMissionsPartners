package com.hengtiansoft.wrw.enums;

/**
 * 
* Class Name: BarcodeSourceEnum
* Description: 条码流水中的二维码来源
* @author zhisongliu
*
 */
public enum BarcodeSourceEnum {
    PC_SOURCE("0", "PC来源"), MPOS_SOURCE("1", "MPOS来源"),APP_SOURCE("2","APP扫码收货");

    private String code;

    private String text;

    private BarcodeSourceEnum(String code, String text) {
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
         for (BarcodeSourceEnum type : values()) {
             if (type.getCode().equals(code)) {
                 return type.getText();
             }
         }
         return null;
    }
    public static String getCodeByText(String text){
     for (BarcodeSourceEnum type : values()) {
            if (type.getText().equals(text)) {
                return type.getCode();
            }
        }
        return null;
   }
}
