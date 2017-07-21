package com.hengtiansoft.common.xmemcached;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;

import net.rubyeye.xmemcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.AbstractCacheManager;

/**
 * @author taochen
 *
 */
public class MemcachedCacheManager extends AbstractCacheManager implements DisposableBean, CacheManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemcachedCacheManager.class);

    private CacheManager cacheManager;

    @Override
    public Cache getCache(String name) {
        if (name == null) {
            name = "";
        }
        // Get from cache
        Cache cache = super.getCache(name);
        if (cache == null) {
            // Check the cache again (in case the cache was added at runtime)
            SimpleMemcachedCache simpleMemcachedCache = (SimpleMemcachedCache) getCacheManager().getCache(name);
            if (simpleMemcachedCache != null) {
                addCache(simpleMemcachedCache);
                cache = super.getCache(name);
            }
        }
        return cache;
    }

    private CacheManager getCacheManager() {
        return cacheManager;
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        Collection<Cache> caches = new LinkedHashSet<Cache>();
        for (String cacheName : getCacheManager().getCacheNames()) {
            Cache cache = getCacheManager().getCache(cacheName);
            caches.add(cache);
        }
        return caches;
    }

    public void addCache(SimpleMemcachedCache cache) {
        super.addCache(cache);
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void destroy() {
        LOGGER.info("Destroy Cache List");

        for (String name : super.getCacheNames()) {
            LOGGER.info("Destroy Cache: {}", name);
            try {
                ((MemcachedClient) getCache(name).getNativeCache()).shutdown();
            } catch (IOException e) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.error("shut down error", e);
                }
            }
        }

    }
}
