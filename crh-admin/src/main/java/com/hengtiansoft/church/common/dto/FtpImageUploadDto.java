package com.hengtiansoft.church.common.dto;

import java.io.Serializable;

public class FtpImageUploadDto implements Serializable {

    /**
     * Variables Name: serialVersionUID Description: Value Description:
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
