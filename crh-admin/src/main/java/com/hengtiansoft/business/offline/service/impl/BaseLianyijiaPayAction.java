/*
 * Project Name: wrw-admin
 * File Name: BaseLianyijiaPayAction.java
 * Class Name: BaseLianyijiaPayAction
 * Copyright 2014 Hengtian Software Inc
 * Licensed under the Hengtiansoft
 * http://www.hengtiansoft.com
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.business.offline.service.impl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hengtiansoft.business.offline.service.LianyijiaPayService;
import com.hengtiansoft.common.exception.BizServiceException;
import com.hengtiansoft.common.util.EncryptUtil;

/**
 * Class Name: BaseLianyijiaPayAction Description:
 * 
 * @author xiaoluzhou
 */
public abstract class BaseLianyijiaPayAction implements LianyijiaPayService {

    private static final Logger log = LoggerFactory.getLogger(BaseLianyijiaPayAction.class);

    public String sendHttpRequest(String apiUrl, Map<String, String> params, String secret, int connectionRequestTimeout, int connectionTimeout,
            int readTimeout) throws ClientProtocolException, IOException {

        String sign = signature(params, secret);
        params.put("sign", sign);
        String getReqParams = buildReqParams(params, "=", "&");

        return doGet(apiUrl + "?" + getReqParams, connectionRequestTimeout, connectionTimeout, readTimeout);
    }

    /**
     * Description:拼接get请求参数，并对参数值进行urlencode
     *
     * @param params
     * @param string
     * @param string2
     */
    private String buildReqParams(Map<String, String> params, String connector, String separator) {

        StringBuilder sb = new StringBuilder();
        Iterator<String> iterator = params.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            try {
                sb.append(key).append(connector).append(URLEncoder.encode(params.get(key), "utf-8")).append(separator);
            } catch (UnsupportedEncodingException e) {
                throw new BizServiceException("请求参数utf8编码异常，请检查请求参数");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * Description:
     *
     * @param params
     * @return
     */
    private String signature(Map<String, String> params, String secret) {
        List<String> paramNameList = new ArrayList<String>(params.keySet());
        Collections.sort(paramNameList);
        StringBuilder sb = new StringBuilder(secret);
        for (int i = 0; i < paramNameList.size(); i++) {
            sb.append(paramNameList.get(i)).append(params.get(paramNameList.get(i)));
        }
        sb.append(secret);
        return EncryptUtil.encryptMd5(sb.toString()).toUpperCase();
    }

    public String doGet(String url, int connectionRequestTimeout, int connectionTimeout, int readTimeout) throws ClientProtocolException, IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)
                .setConnectTimeout(connectionTimeout).setSocketTimeout(readTimeout).build();
        HttpGet get = new HttpGet(url);
        get.setConfig(requestConfig);
        CloseableHttpResponse response = client.execute(get);

        return EntityUtils.toString(response.getEntity());
    }

    /**
     * Description: 把对象转换为map，key为对象的属性名，值为属性值，只转换对象的String类型属性的。
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public Map<String, String> objectToMap(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if ("class".compareToIgnoreCase(key) == 0) {
                    continue;
                }
                Method getter = property.getReadMethod();
                Object value = getter != null ? getter.invoke(obj) : null;
                if (value instanceof String && (StringUtils.isNotBlank((String) value))) {
                    map.put(key, (String) value);
                }
            }
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            log.error("请求参数转换为map报错", e);
        }
        return map;
    }
    
}
