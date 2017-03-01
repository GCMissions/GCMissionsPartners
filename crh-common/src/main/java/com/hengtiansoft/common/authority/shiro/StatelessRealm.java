package com.hengtiansoft.common.authority.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Class Name: StatelessRealm Description: 无状态验证Realm
 *
 * @author jialiangli
 */
public class StatelessRealm extends AuthorizingRealm {

    private StatelessAuthcImpl statelessAuthcImpl;

    public void setStatelessAuthcImpl(StatelessAuthcImpl statelessAuthcImpl) {
        this.statelessAuthcImpl = statelessAuthcImpl;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof StatelessToken || token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return statelessAuthcImpl.doGetAuthorizationInfo(principals);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        SimpleAuthenticationInfo authenticationInfo = statelessAuthcImpl.doGetAuthenticationInfo(token);
        // 已经验证过了，不需要再密码进行验证
        setCredentialsMatcher(new StatelessCredentialsMatcher());
        return authenticationInfo;
    }

}
