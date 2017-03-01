/*
 * Project Name: zc-collect-common
 * File Name: CacheType.java
 * Class Name: CacheType
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
package com.hengtiansoft.common.xmemcached.constant;

/**
 * Class Name: CacheType Description:定义了系统缓存类型<br>
 * 此处定义的缓存类型与xmemcached-context.xml文件中定义的cacheManager参数configMap的key一致
 * 
 * @author jialiangli
 *
 */
public class CacheType {
    /**
     * 变量名: DEFAULT 描述: 默认缓存类型,在Memcached缓存时间为12个小时
     */
    public static final String DEFAULT           = "DEFAULT";

    /**
     * 变量名: THREAD_LOCAL 描述: 仅在当前ThreadLocal缓存,不会同步到Memcached,在Request结束时,该缓存将被清除
     */
    public static final String THREAD_LOCAL      = "THREAD_LOCAL";

    /**
     * 变量名: APPLICATIONAL 描述: 以最大时间缓存到Memcached
     */
    public static final String APPLICATIONAL     = "APPLICATIONAL";

    /**
     * 变量名: MEMCACHED_ONLY 描述: 不使用本地缓存
     */
    public static final String MEMCACHED_ONLY    = "MEMCACHED_ONLY";

    /**
     * 变量名: MEMCACHED_FOR_SMS 描述: 缓存短信验证码
     */
    public static final String MEMCACHED_FOR_SMS = "MEMCACHED_FOR_SMS";

    /**
     * 变量名: MEMCACHED_WECHAT 描述: 缓存微信验证信息
     */
    public static final String MEMCACHED_WECHAT  = "MEMCACHED_WECHAT";
    
    /**
     * 订单缓存
     */
    public static final String ORDERCACHE  = "ORDERCACHE";
    
    /**
     * 微信乐园热门推荐缓存
     */
    public static final String WELYHOTCACHE  = "welyHotCache";
    
    /**
     * 乐园品类缓存
     */
    public static final String  WELY_CATEGORY_CACHE = "categoryCache";
    
    /**
     * 乐园轮播位缓存
     */
    public static final String  WELY_AD_CACHE = "adCache";
    
    /**
     * 乐园首页商品缓存
     */
    public static final String  WELY_HOMEPAGE_CACHE = "homepageCache";
    
    /**
     * 用户缓存
     */
    public static final String MEMBER_CACHE = "MEMCACHED_MEMBER";

}
