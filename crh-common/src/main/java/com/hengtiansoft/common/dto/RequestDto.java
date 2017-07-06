package com.hengtiansoft.common.dto;

import java.io.Serializable;

public class RequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Variables Name: token Description:Login authentication token, request token authentication before passing into
     * the method body Value Description:
     */
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "RequestDto [token=" + token + "]";
    }
}
