package com.hengtiansoft.common.lbs.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Class Name: BaiduResultBean 
 * Description: Baidu update data to return information
 * 
 * @author taochen
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)  
public class BaiduResultBean {

    private String status;

    private String id;

    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
