package com.hengtiansoft.common.xmemcached;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.rubyeye.xmemcached.XMemcachedClientBuilder;

import org.apache.commons.collections.MapUtils;
import org.springframework.util.CollectionUtils;

/**
 * @author taochen
 *
 */
public class CacheConfiguration {

    private static final String KEY_SEPARATOR = ":";

    private Map<String, Object> propertyMap = new HashMap<String, Object>();

    private XMemcachedClientBuilder clientBuilder;

    /**
     * @param properties
     *            the properties to set
     */
    public void setProperties(Properties properties) {
        CollectionUtils.mergePropertiesIntoMap(properties, this.propertyMap);
    }

    /**
     * @return the keyPrefix
     */
    public String getKeyPrefix() {
        return getCacheName() + getKeySeparator();
    }

    /**
     * @return the keySeparator
     */
    public String getKeySeparator() {
        return MapUtils.getString(propertyMap, "keySeparator", KEY_SEPARATOR);
    }

    /**
     * @return the transcoder
     */
    public String getTranscoder() {
        return MapUtils.getString(propertyMap, "transcoder");
    }

    /**
     * @return the bufferAllocator
     */
    public String getBufferAllocator() {
        return MapUtils.getString(propertyMap, "bufferAllocator");
    }

    /**
     * @return the cacheName
     */
    public String getCacheName() {
        return MapUtils.getString(propertyMap, "cacheName");
    }

    /**
     * @return the namespace
     */
    public String getNamespace() {
        return MapUtils.getString(propertyMap, "namespace");
    }

    /**
     * @return the useDefault
     */
    public Boolean isUseDefault() {
        return MapUtils.getBoolean(propertyMap, "useDefault", Boolean.TRUE);
    }

    /**
     * @return the useNamespace
     */
    public Boolean isUseNamespace() {
        return MapUtils.getBoolean(propertyMap, "useNamespace", Boolean.FALSE);
    }

    /**
     * @return the expiration
     */
    public Integer getExpiration() {
        return MapUtils.getInteger(propertyMap, "expiration", 0);
    }

    /**
     * @return the addrs
     */
    public String getAddrs() {
        return MapUtils.getString(propertyMap, "addrs");
    }

    /**
     * @return the weights
     */
    public int[] getWeightArray() {
        String weight = getWeights();
        if (null == weight) {
            return null;
        } else {
            String[] strs = weight.split(" ");
            int[] weigths = new int[] {};
            for (String s : strs) {
                weigths[weigths.length] = Integer.parseInt(s);
            }
            return weigths;
        }
    }

    /**
     * @return the weights
     */
    public String getWeights() {
        return MapUtils.getString(propertyMap, "weights");
    }

    /**
     * @return the commandFactory
     */
    public String getCommandFactory() {
        return MapUtils.getString(propertyMap, "commandFactory");
    }

    /**
     * @return the sessionLocator
     */
    public String getSessionLocator() {
        return MapUtils.getString(propertyMap, "sessionLocator");
    }

    /**
     * @return the connectionPoolSize
     */
    public Integer getConnectionPoolSize() {
        return MapUtils.getInteger(propertyMap, "connectionPoolSize");
    }

    /**
     * @return the operationTimeout
     */
    public Long getOperationTimeout() {
        return MapUtils.getLong(propertyMap, "operationTimeout");
    }

    /**
     * @return the propertyMap
     */
    public Map<String, Object> getPropertyMap() {
        return propertyMap;
    }

    /**
     * @param propertyMap
     *            the propertyMap to set
     */
    public void setPropertyMap(Map<String, Object> propertyMap) {
        this.propertyMap = propertyMap;
    }

    /**
     * @return the clientBuilder
     */
    public XMemcachedClientBuilder getClientBuilder() {
        return clientBuilder;
    }

    /**
     * @param clientBuilder
     *            the clientBuilder to set
     */
    public void setClientBuilder(XMemcachedClientBuilder clientBuilder) {
        this.clientBuilder = clientBuilder;
    }

}
