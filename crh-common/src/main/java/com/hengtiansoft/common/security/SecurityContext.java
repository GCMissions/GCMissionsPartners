package com.hengtiansoft.common.security;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;

import com.hengtiansoft.common.util.AppConfigUtil;
import com.hengtiansoft.common.util.ApplicationContextUtil;
import com.hengtiansoft.common.xmemcached.MemcachedCacheManager;

/**
 * Class Name: SecurityContext Description:
 * 
 * @author taochen
 * 
 */
public class SecurityContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityContext.class);

    private static final ThreadLocal<String> SESSION_ID = new ThreadLocal<String>();

    private static final ThreadLocal<Set<String>> ROLES = new ThreadLocal<>();

    private static String location = null;

    public static boolean canSwitchUser() {
        return "Y".equals(getCache().get("#canSwitchUser#" + SESSION_ID.get(), String.class));
    }

    public static void putCache(String key, Object o) {
        Cache cache = getCache();
        cache.put(key, o);
    }

    public static void evictCache(String key) {
        getCache().evict(key);
    }

    public static Cache getCache() {
        return ApplicationContextUtil.getBean(MemcachedCacheManager.class).getCache("authorization");
    }

    public static void setCurrentSessionId(String sessionId) {
        SESSION_ID.set(sessionId);
    }

    public static String getCurrentSessionId() {
        return SESSION_ID.get();
    }

    public static void setRoles(Set<String> roles) {
        ROLES.set(roles);
    }

    public static Set<String> getRoles() {
        return ROLES.get();
    }

    public static void removeRoles() {
        ROLES.remove();
    }

    public static String getLocation() {
        if (location == null) {
            location = AppConfigUtil.getConfig("host.location");
        }

        return location;
    }

}
