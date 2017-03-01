package com.hengtiansoft.common.dto;
import java.io.Serializable;
public class RequestDto implements Serializable{

    private static final long serialVersionUID = 1L;
    
    /**
    * Variables Name: token
    * Description: 登陆验证token，请求经token验证通过后才能进入方法体
    * Value Description: TODO
    */
    private String token ;

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
