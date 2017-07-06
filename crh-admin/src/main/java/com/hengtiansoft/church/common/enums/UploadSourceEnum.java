package com.hengtiansoft.church.common.enums;
/**
 * Class Name: UploadSourceEnum
 * Description: Upload the source of the picture
 * @author taochen
 *
 */
public enum UploadSourceEnum {

    PRODUCT("10", "WELY_PRODUCT", "Platform Systems Goods"), BRAND("11", "", "Platform System brand"), 
    AD("12", "WELY_AD", "Platform System Advertising"), FPRODUCT("13", "", "Platform System Floor Goods"), 
    UEDITOR("14", "", "ueeditor"), APPMESSAGEIMG("15", "", "App Message Picture"), 
    GROUP("16", "", "Group Purchase Picture"), APPMESSAGE("20", "", "Platform System APP Message"), 
    COOLBAG("21", "", "Kd Picture"), MYACTIVITY("22", "WELY_MY_ACTIVITY", "My activity picture"), 
    KDADVERTISE("23", "WELY_CONTEXT", "Advertising picture"), IMAGEMATERIAL("24", "", "My material library picture"),APPSTARTUP("26","","App start page");
    
    private String key;
    private String flag;
    private String desc;
    
    UploadSourceEnum(String key, String flag, String desc) {
        this.key = key;
        this.flag = flag;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public static String getFlag(String key) {
        for (UploadSourceEnum source : UploadSourceEnum.values()) {
            if (source.getKey().equals(key)) {
                return source.getFlag();
            }
        }
        return null;
    }
}
