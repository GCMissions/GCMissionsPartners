/*
 * Project Name: wrw-admin
 * File Name: HttpClientBuilderAdapter.java
 * Class Name: HttpClientBuilderAdapter
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
package com.hengtiansoft.church.common.util;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tencent.service.IServiceRequest;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

/**
 * Class Name: HttpClientBuilderAdapter
 * Description:
 * 
 * @author xiaoluzhou
 */
public class HttpClientBuilderAdapter implements IServiceRequest {

    private static final Logger log = LoggerFactory.getLogger(HttpClientBuilderAdapter.class);

    private CloseableHttpClient httpClient;

    private boolean             hasInit;

    public HttpClientBuilderAdapter() {
        init();
    }

    private void init() {
        synchronized (HttpClientBuilderAdapter.class) {
            if (httpClient == null) {
                httpClient = HttpClientBuilder.create().build();
                hasInit = true;
            }
        }
    }

    @Override
    public String sendPost(String api_url, Object xmlObj) throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException,
            KeyStoreException, IOException {
        if (!hasInit) {
            init();
        }

        String result = null;

        HttpPost httpPost = new HttpPost(api_url);

        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));

        String postDataXML = xStreamForRequestPostData.toXML(xmlObj);

        log.info(postDataXML);

        StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);

        httpPost.setConfig(RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(5000).build());

        log.info("executing request" + httpPost.getRequestLine());

        try {
            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();

            result = EntityUtils.toString(entity, "UTF-8");

            log.info("微信刷卡支付返回：" + result);

        } catch (ConnectionPoolTimeoutException e) {
            log.error("http get throw ConnectionPoolTimeoutException(wait time out)");

        } catch (ConnectTimeoutException e) {
            log.error("http get throw ConnectTimeoutException");

        } catch (SocketTimeoutException e) {
            log.error("http get throw SocketTimeoutException");

        } catch (Exception e) {
            log.error("http get throw Exception");

        } finally {
            httpPost.abort();
        }

        return result;
    }

}
