/*
 * Project Name: zc-collect-datasync
 * File Name: HttpsUtil.java
 * Class Name: HttpsUtil
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
package com.hengtiansoft.task.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * Class Name: HttpsUtil Description: TODO
 * 
 * @author yigesong
 *
 */
public class HttpsUtil {
    private static HttpClient client = HttpClientBuilder.create().build();

    public static String sendPost(String url, String json) {
        String result = "";
        HttpPost httpRequst = new HttpPost(url);// 创建HttpPost对象
        try {
            StringEntity params = new StringEntity(json);
            params.setContentType("application/json;charset=UTF-8");
            params.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));

            httpRequst.setEntity(params);
            HttpResponse httpResponse = client.execute(httpRequst);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                result = EntityUtils.toString(httpEntity);// 取出应答字符串
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = e.getMessage().toString();
        }
        return result;
    }

    public static void main(String[] args) {
        sendPost("http://zcckjpda.hengtiansoft.com:15058/zc-collect-web-supplier/web/pda/login",
                "{\"username\" : \"123\",\"password\" :\"test\"}");
    }
}
