package com.hengtiansoft.common.enumeration;

/**
 * Class Name: ImagePathEnum
 * Description: Picture upload secondary directory
 * @author taochen
 *
 */
public enum ImagePathEnum {
 ADMIN_PRODUCT("10","Platform Systems Goods","/product"),
 
 ADMIN_BRAND("11","Platform System Brand","/brand"),
 
 ADMIN_AD("12","Platform System Ads","/da"),
 
 PFLOOR_PRODUCT("13","Platform System Floor Goods","/fproduct"),
 
 PRODUCT_DETAIL("14","Platform System Product Details","/productdetail"),
 
 APP_IMAGE("15","Platform System APP Message Picture","/appImage"),
 
 ADMIN_APP_MESSAGE("20","Platform System APP Message","/appmessage"),
 
 ADMIN_TEAM("16","Platform system high-end product team to buy the picture","/adminteam"),
 
 ADMIN_MEDIA("21","Kd Picture management","/kd");
 
    private String key;
    private String name;
    private String value;
    
    private ImagePathEnum(String key ,String name, String value){
        this.key=key;
        this.name=name;
        this.value=value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getValue(String key){
        for(ImagePathEnum status:values()){
            if(status.getKey().equals(key)){
                return status.getValue();
            }
        }
        return null;
       
    }

}
