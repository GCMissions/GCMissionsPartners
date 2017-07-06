package com.hengtiansoft.common.xmemcached.constant;

/**
 * Class Name: CacheType Description:Defines the system cache type The cache type defined here is consistent with the
 * key of the cacheManager parameter configMap defined in the xmemcached-context.xml file
 * 
 * @author taochen
 *
 */
public class CacheType {
    /**
     * Variable name: DEFAULT Description: The default cache type, time Memcached cache is 12 hours
     */
    public static final String DEFAULT = "DEFAULT";

    /**
     * Variable name: THREAD_LOCAL Description: only in the current ThreadLocal cache, will not be synchronized to
     * Memcached, at the end of the request, the cache will be cleared
     */
    public static final String THREAD_LOCAL = "THREAD_LOCAL";

    /**
     * Variable name: APPLICATIONAL Description: Cache with maximum time to Memcached
     */
    public static final String APPLICATIONAL = "APPLICATIONAL";

    /**
     * Variable name: MEMCACHED_ONLY Description: Do not use the local cache
     */
    public static final String MEMCACHED_ONLY = "MEMCACHED_ONLY";

    /**
     * Variable name: MEMCACHED_FOR_SMS Description: Cached SMS verification code
     */
    public static final String MEMCACHED_FOR_SMS = "MEMCACHED_FOR_SMS";

    /**
     * Variable name: MEMCACHED_WECHAT Description: cache WeChat authentication information
     */
    public static final String MEMCACHED_WECHAT = "MEMCACHED_WECHAT";

    /**
     * Order cache
     */
    public static final String ORDERCACHE = "ORDERCACHE";

    /**
     * recommended caching
     */
    public static final String WELYHOTCACHE = "welyHotCache";

    /**
     * category cache
     */
    public static final String WELY_CATEGORY_CACHE = "categoryCache";

    /**
     * Carousel buffer.
     */
    public static final String WELY_AD_CACHE = "adCache";

    /**
     * Home product cache
     */
    public static final String WELY_HOMEPAGE_CACHE = "homepageCache";

    /**
     * member cache
     */
    public static final String MEMBER_CACHE = "MEMCACHED_MEMBER";

}
