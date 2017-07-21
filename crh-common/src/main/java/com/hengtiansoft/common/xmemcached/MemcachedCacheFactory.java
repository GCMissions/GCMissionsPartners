package com.hengtiansoft.common.xmemcached;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.rubyeye.xmemcached.CommandFactory;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedSessionLocator;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.CacheManager;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;

/**
 * @author taochen
 *
 */
public class MemcachedCacheFactory implements FactoryBean<CacheManager>, InitializingBean, DisposableBean {
    private CacheManager cacheManager;

    private List<CacheConfiguration> cacheConfigurationList;

    private String cacheNames;
    private XMemcachedClientBuilder clientBuilder;
    private MemcachedClient client;
    private Map<String, Object> propertyMap = new HashMap<String, Object>();

    @Override
    public CacheManager getObject() throws Exception {
        return cacheManager;
    }

    @Override
    public Class<?> getObjectType() {
        return CacheManager.class;
    }

    @Override
    public void destroy() throws Exception {
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        cacheManager = new MemcachedCacheManager();
        for (CacheConfiguration cacheConfig : cacheConfigurationList) {
            Map<String, Object> propMap = Maps.newHashMap(propertyMap);
            propMap.putAll(cacheConfig.getPropertyMap());
            cacheConfig.setPropertyMap(propMap);

            SimpleMemcachedCache cache = createCache(cacheConfig);
            ((MemcachedCacheManager) cacheManager).addCache(cache);
        }
        for (String name : cacheNames.split(",")) {
            Map<String, Object> propMap = Maps.newHashMap(propertyMap);
            CacheConfiguration cacheConfig = new CacheConfiguration();
            propMap.putAll(cacheConfig.getPropertyMap());
            cacheConfig.setPropertyMap(propMap);
            Properties nameProp = new Properties();
            nameProp.put("cacheName", name);
            cacheConfig.setProperties(nameProp);
            SimpleMemcachedCache cache = createCache(cacheConfig);
            ((MemcachedCacheManager) cacheManager).addCache(cache);
        }

    }

    public XMemcachedClientBuilder getClientBuilder() {
        return clientBuilder;
    }

    public void setClientBuilder(XMemcachedClientBuilder clientBuilder) {
        this.clientBuilder = clientBuilder;
    }

    private XMemcachedClientBuilder createClientBuilder(CacheConfiguration config) throws Exception {
        XMemcachedClientBuilder builder;

        if (null != config.getClientBuilder()) {
            builder = config.getClientBuilder();
        } else {
            Assert.notNull(config.getAddrs(), "CacheConfiguration parammeter addrs is required.");
            int[] weights = config.getWeightArray();
            if (null == weights) {
                builder = new XMemcachedClientBuilder(config.getAddrs());
            } else {
                builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(config.getAddrs()), weights);
            }

            if (null != config.getConnectionPoolSize()) {
                builder.setConnectionPoolSize(config.getConnectionPoolSize());
            }
            if (null != config.getOperationTimeout()) {
                builder.setOpTimeout(config.getOperationTimeout());
            }
            if (null != config.getCommandFactory()) {
                Class<?> clazz = Class.forName(config.getCommandFactory(), true, this.getClass().getClassLoader());
                CommandFactory commandFactory = (CommandFactory) clazz.newInstance();
                builder.setCommandFactory(commandFactory);
            }
            if (null != config.getSessionLocator()) {
                Class<?> clazz = Class.forName(config.getSessionLocator(), true, this.getClass().getClassLoader());
                MemcachedSessionLocator sessionLocator = (MemcachedSessionLocator) clazz.newInstance();
                builder.setSessionLocator(sessionLocator);

            }
        }
        builder.setName(config.getCacheName());
        return builder;
    }

    protected SimpleMemcachedCache createCache(CacheConfiguration config) throws Exception {
        Assert.notNull(config, "CacheConfiguration cannot be null.");
        Assert.notNull(config.getCacheName(), "CacheConfiguration parammeter cahceName is required.");
        SimpleMemcachedCache cache = new SimpleMemcachedCache();
        cache.setCacheName(config.getCacheName());
        cache.setKeyPrefix(config.getKeyPrefix());
        if (null != config.getExpiration()) {
            cache.setExpiration(config.getExpiration());
        }

        if (config.isUseDefault() && null != clientBuilder) {
            cache.setMemcachedClient(client);
        } else {
            XMemcachedClientBuilder builder = createClientBuilder(config);
            if (client == null) {
                client = builder.build();
            }
            cache.setMemcachedClient(client);
        }

        return cache;
    }

    /**
     * @param defaultProps
     *            the defaultProps to set
     */
    public void setDefaultProps(Properties defaultProps) {
        CollectionUtils.mergePropertiesIntoMap(defaultProps, propertyMap);
    }

    /**
     * @return the cacheConfigurationList
     */
    public List<CacheConfiguration> getCacheConfigurationList() {
        return cacheConfigurationList;
    }

    /**
     * @param cacheConfigurationList
     *            the cacheConfigurationList to set
     */
    public void setCacheConfigurationList(List<CacheConfiguration> cacheConfigurationList) {
        this.cacheConfigurationList = cacheConfigurationList;
    }

    public String getCacheNames() {
        return cacheNames;
    }

    public void setCacheNames(String cacheNames) {
        this.cacheNames = cacheNames;
    }

}
