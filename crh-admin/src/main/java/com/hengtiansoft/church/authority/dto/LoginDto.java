package com.hengtiansoft.church.authority.dto;

import java.io.Serializable;

/**
 * 
* Class Name: LoginDto
* Description: Login Dto 
* @author tao chen
*
 */
public class LoginDto implements Serializable {

    private static final long serialVersionUID = 3934170150336347402L;

    private String loginId;

    private String password;

    private String captcha;

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
