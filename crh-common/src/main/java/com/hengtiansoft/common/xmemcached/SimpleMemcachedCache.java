package com.hengtiansoft.common.xmemcached;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

/**
 * @author taochen
 *
 */
public class SimpleMemcachedCache implements Cache {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMemcachedCache.class);

    private static final ThreadLocal<Map<String, Object>> LOCAL_CACHE = new ThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<String, Object>();
        }
    };

    private MemcachedClient memcachedClient;

    private int expiration;

    private String cacheName;

    private String keyPrefix;

    public SimpleMemcachedCache() {
    }

    @Override
    public String getName() {
        return cacheName;
    }

    @Override
    public Object getNativeCache() {
        return memcachedClient;
    }

    private String getKey(final Object key) {
        return keyPrefix + key.toString();
    }

    private void logException(TimeoutException e) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.error(e.getMessage(), e);
        } else {
            LOGGER.warn(e.getMessage(), e);
        }
    }

    private void logException(InterruptedException e) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.error(e.getMessage(), e);
        } else {
            LOGGER.warn(e.getMessage(), e);
        }
    }

    private void logException(MemcachedException e) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.error(e.getMessage(), e);
        } else {
            LOGGER.warn(e.getMessage(), e);
        }
    }

    private Object getValue(Object key) {
        String keyStr = getKey(key);
        if (LOCAL_CACHE.get().containsKey(keyStr)) {
            return LOCAL_CACHE.get().get(keyStr);
        }
        Object value = null;
        try {
            value = memcachedClient.get(keyStr);
            if (value != null) {
                LOCAL_CACHE.get().put(keyStr, value);
            }
        } catch (InterruptedException e) {
            logException(e);
        } catch (TimeoutException e) {
            logException(e);
        } catch (MemcachedException e) {
            logException(e);
        }
        return value;
    }

    @Override
    public ValueWrapper get(Object key) {
        Object value = getValue(key);
        LOGGER.trace("Get cache value with key {} from cache {}.", key, cacheName);
        if (value == null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.warn("Can not get cache with key {} from cache {}", key, cacheName);
            }
            return null;
        }

        return new SimpleValueWrapper(value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, Class<T> type) {
        return (T) getValue(key);
    }

    @Override
    public void put(Object key, Object value) {
        if (key != null && value != null) {
            try {
                String keyStr = getKey(key);
                memcachedClient.set(keyStr, expiration, value);
                LOCAL_CACHE.get().put(keyStr, value);
                LOGGER.trace("Put value into cache {} with key {}.", cacheName, key);
            } catch (TimeoutException e) {
                logException(e);
            } catch (InterruptedException e) {
                logException(e);
            } catch (MemcachedException e) {
                logException(e);
            }
        } else {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.warn("Cannot put value '{}' to cache {} because key{} or value is null", key, value, cacheName);
            }
        }
    }

    @Override
    public void evict(Object key) {
        if (key != null) {
            try {
                String keyStr = getKey(key);
                memcachedClient.delete(keyStr);
                LOCAL_CACHE.get().remove(keyStr);
                LOGGER.debug("Evict {} from cache {}", key, cacheName);
            } catch (TimeoutException e) {
                logException(e);
            } catch (InterruptedException e) {
                logException(e);
            } catch (MemcachedException e) {
                logException(e);
            }
        } else {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.warn("Cannot evict from cache {} because key is null", cacheName);
            }
        }
    }

    @Override
    public void clear() {
        try {
            memcachedClient.flushAll();
            LOCAL_CACHE.get().clear();
            LOGGER.trace("Clear Cache {}", cacheName);
        } catch (TimeoutException e) {
            logException(e);
        } catch (InterruptedException e) {
            logException(e);
        } catch (MemcachedException e) {
            logException(e);
        }
    }

    public static void removeLocalCache() {
        LOCAL_CACHE.get().clear();
        LOCAL_CACHE.remove();
    }

    public MemcachedClient getMemcachedClient() {
        return memcachedClient;
    }

    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    public int getExpiration() {
        return expiration;
    }

    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

}
