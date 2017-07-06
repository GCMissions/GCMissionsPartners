package com.hengtiansoft.church.common.dto;

import java.io.Serializable;

/**
 * 
* Class Name: ImageDetailDto
* Description: Multiple pictures to upload the required parameters to return
* @author taochen
*
 */
public class ImageDetailDto implements Serializable{

    private static final long serialVersionUID = -2908961512537159404L;
    
    private String url;
    
    private String title;
    
    private String state;
    
    private String original;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }
    
    
    
}
