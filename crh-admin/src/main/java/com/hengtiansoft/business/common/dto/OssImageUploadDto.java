package com.hengtiansoft.business.common.dto;

import java.io.Serializable;

public class OssImageUploadDto implements Serializable{

    /**
    * Variables Name: serialVersionUID
    * Description: TODO
    * Value Description: TODO
    */
    private static final long serialVersionUID = 1L;
    
    private String key;
    
    private String url;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    
}
