package com.hengtiansoft.common.authority.shiro;

import java.util.Collection;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* Class Name: ModularRealmAuthenticator
* Description: Rewrite ModularRealmAuthenticator to short circuit mode.
*              If the authentication method is FirstSuccessfulStrategy, 
*              return directly to the first verification is successful, no longer continue to verify.
* @author taochen
*
*/
public class MyModularRealmAuthenticator extends ModularRealmAuthenticator {
    private static final Logger log = LoggerFactory.getLogger(MyModularRealmAuthenticator.class);

    @Override
    protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) {

        AuthenticationStrategy strategy = getAuthenticationStrategy();

        AuthenticationInfo aggregate = strategy.beforeAllAttempts(realms, token);

        if (log.isTraceEnabled()) {
            log.trace("Iterating through {} realms for PAM authentication", realms.size());
        }

        for (Realm realm : realms) {

            aggregate = strategy.beforeAttempt(realm, token, aggregate);

            if (realm.supports(token)) {

                log.trace("Attempting to authenticate token [{}] using realm [{}]", token, realm);

                AuthenticationInfo info = null;
                Throwable t = null;
                try {
                    info = realm.getAuthenticationInfo(token);
                } catch (Throwable throwable) {
                    t = throwable;
                    if (log.isDebugEnabled()) {
                        String msg = "Realm [" + realm + "] threw an exception during a multi-realm authentication attempt:";
                        log.debug(msg, t);
                    }
                }

                aggregate = strategy.afterAttempt(realm, token, info, aggregate, t);

                // If the authentication method is FirstSuccessfulStrategy, 
                // return directly to the first verification is successful, no longer continue to verify.
                if (strategy.getClass() == FirstSuccessfulStrategy.class && aggregate != null && !CollectionUtils.isEmpty(aggregate.getPrincipals())) {
                    return aggregate;
                }

            } else {
                log.debug("Realm [{}] does not support token {}.  Skipping realm.", realm, token);
            }
        }

        aggregate = strategy.afterAllAttempts(token, aggregate);

        return aggregate;
    }

}
