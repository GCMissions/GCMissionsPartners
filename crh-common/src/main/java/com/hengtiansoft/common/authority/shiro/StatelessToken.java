package com.hengtiansoft.common.authority.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Class Name: StatelessToken Description: no status Token
 * 
 * @author taochen
 *
 */
public class StatelessToken implements AuthenticationToken {

    private static final long serialVersionUID = 1L;

    private String token;

    public StatelessToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

}
