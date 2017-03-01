/*
 * Project Name: xyl-core
 * File Name: ShiroMemcachedManager.java
 * Class Name: ShiroMemcachedManager
 *
 * Copyright 2014 Hengtian Software Inc
 *
 * Licensed under the Hengtiansoft
 *
 * http://www.hengtiansoft.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.common.xmemcached;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.Destroyable;
import org.apache.shiro.util.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;


/**
 * @author yupengshen
 *
 */
public class ShiroMemcachedManager implements org.apache.shiro.cache.CacheManager, Initializable, Destroyable {

	private static final Logger	LOGGER	= LoggerFactory.getLogger(ShiroMemcachedManager.class);

	/**
	 *
	 */
	protected CacheManager		cacheManager;

	/**
	 * Default no argument constructor.
	 */
	public ShiroMemcachedManager() {
	}

	/**
	 * @return
	 */
	public CacheManager getCacheManager() {
		return cacheManager;
	}

	/**
	 * @param cacheManager
	 */
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	/**
	 * (non-Javadoc).
	 * 
	 * @see org.apache.shiro.cache.CacheManager#getCache(String)
	 */
	@Override
    public final <K, V> Cache<K, V> getCache(String name) throws CacheException {
		LOGGER.trace("Acquiring Memcached instance named {}", name);
		org.springframework.cache.Cache cache = cacheManager.getCache(name);
		if (cache == null) {
			throw new CacheException("Cache with name '" + name + "' does not yet exist.");
		} else {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Using existing Memcached named {}", name);
			}
		}
		return new ShiroMemcache<K, V>(cache);
	}

	/**
	 * (non-Javadoc).
	 *
	 * @see Initializable#init()
	 */
	@Override
    public final void init() throws CacheException {

	}

	/**
	 * (non-Javadoc).
	 *
	 * @see Destroyable#destroy()
	 */
	@Override
    public void destroy() {
		
	}

}
