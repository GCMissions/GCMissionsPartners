package com.hengtiansoft.common.enumeration;

/**
 * Class Name: ImageEnum
 * Description: upload picture
 * @author taochen
 *
 */
public enum ImageEnum {
    PLATFORM("0","Platform System","/wrw-admin/static/upload/image"),
    MOBILE("1","Mobile Terminal System","/mobile/static/upload/image");
    
    private String key;
    private String name;
    private String value;
    
    private ImageEnum(String key ,String name, String value){
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
        for(ImageEnum status:values()){
            if(status.getKey().equals(key)){
                return status.getValue();
            }
        }
        return null;
       
    }
}
