package com.hengtiansoft.common.xmemcached;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author taochen
 *
 * @version 1.0_beta
 * @param <K>
 * @param <V>
 */
public class ShiroMemcache<K, V> implements Cache<K, V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroMemcache.class);

    private org.springframework.cache.Cache cache;

    /**
	 * 
	 */
    public ShiroMemcache() {
    }

    /**
     * @param cache
     */
    public ShiroMemcache(org.springframework.cache.Cache cache) {
        if (cache == null) {
            throw new IllegalArgumentException("Cache argument cannot be null.");
        }
        this.cache = cache;
    }

    /**
     * (non-Javadoc).
     * 
     * @see Cache#get(Object)
     */
    @Override
    @SuppressWarnings("unchecked")
    public V get(K key) throws CacheException {
        LOGGER.trace("Getting object from cache {} for key {}", key);
        if (key == null) {
            return null;
        } else {
            org.springframework.cache.Cache.ValueWrapper valueWrapper = cache.get(key);

            return valueWrapper == null ? null : (V) valueWrapper.get();
        }
    }

    /**
     * (non-Javadoc).
     *
     * @see Cache#put(Object, Object)
     */
    @Override
    public V put(K key, V value) throws CacheException {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Putting object  for key {}", key);
        }
        V previous = get(key);
        cache.put(key, value);
        return previous;
    }

    /**
     * (non-Javadoc).
     *
     * @see Cache#remove(Object)
     */
    @Override
    public V remove(K key) throws CacheException {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Removing object from cache {} for key {}", key);
        }
        V previous = get(key);
        cache.evict(key.toString());
        return previous;
    }

    /**
     * (non-Javadoc).
     *
     * @see Cache#clear()
     */
    @Override
    public void clear() throws CacheException {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Clearing all objects from cache {} for key {}");
        }
        // clear cache
    }

    /**
     * (non-Javadoc).
     *
     * @see Cache#size()
     */
    @Override
    public int size() {
        return 0;
    }

    /**
     * (non-Javadoc).
     *
     * @see Cache#keys()
     */
    @Override
    public Set<K> keys() {
        return Collections.emptySet();
    }

    /**
     * (non-Javadoc).
     *
     * @see Cache#values()
     */
    @Override
    public Collection<V> values() {
        return Collections.emptyList();
    }

    public void setExpiration(int defaultExpiration) {

    }

    public void delete(Serializable id) {
        cache.evict(id.toString());
    }
}
