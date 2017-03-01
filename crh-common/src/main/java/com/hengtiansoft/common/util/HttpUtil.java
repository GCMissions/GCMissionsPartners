/*
 * Project Name: zc-collect-common
 * File Name: HttpRequestUtil.java
 * Class Name: HttpRequestUtil
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
package com.hengtiansoft.common.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hengtiansoft.common.constant.ApplicationConstant;

/**
 * Class Name: HttpRequestUtil Description: 发送http请求
 * 
 * @author jialiangli
 *
 */
public class HttpUtil {
    public static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static Object doPostForJson(String requestUrl, String params) {
        String result = doPost(requestUrl, ApplicationConstant.JSON_CONTENT_TYPE, params);
        return BizUtil.parseStringToObj(result, Object.class);
    }

    public static <T> T doPostForJson(String requestUrl, Object obj, Class<T> valueType) {
        String result = doPost(requestUrl, ApplicationConstant.JSON_CONTENT_TYPE, BizUtil.parseObjTOString(obj));
        return BizUtil.parseStringToObj(result, valueType);
    }

    public static Object doPostForForm(String requestUrl, String params) {
        String result = doPost(requestUrl, ApplicationConstant.FORM_CONTENT_TYPE, params);
        return BizUtil.parseStringToObj(result, Object.class);
    }

    public static <T> T doPostForForm(String requestUrl, String params, Class<T> valueType) {
        String result = doPost(requestUrl, ApplicationConstant.FORM_CONTENT_TYPE, params);
        return BizUtil.parseStringToObj(result, valueType);
    }

    public static String doPost(String requestUrl, String contentType, String params) {
        logger.info("Use Post Method to request for \"{}\" , params: {}", requestUrl, params);
        StringBuffer buffer = new StringBuffer();
        HttpURLConnection httpUrlConn = null;
        try {
            URL url = new URL(requestUrl);
            httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setRequestProperty(ApplicationConstant.CONTENT_TYPE, contentType);
            httpUrlConn.setRequestMethod(ApplicationConstant.POST);
            // 当有数据需要提交时
            if (null != params) {
                try (OutputStream outputStream = httpUrlConn.getOutputStream()) {
                    // 注意编码格式，防止中文乱码
                    outputStream.write(params.getBytes(ApplicationConstant.ENCODING));
                    outputStream.flush();
                }
            }
            // 将返回的输入流转换成字符串
            try (InputStream inputStream = httpUrlConn.getInputStream(); InputStreamReader inputStreamReader = new InputStreamReader(inputStream, ApplicationConstant.ENCODING); BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                String str = null;
                while ((str = bufferedReader.readLine()) != null) {
                    buffer.append(str);
                }
            }

        } catch (Exception e) {
            logger.error("msg",e);
            throw new RuntimeException(e);
        } finally {
            if (null != httpUrlConn) {
                httpUrlConn.disconnect();
            }
        }
        String response = buffer.toString();
        logger.info("Use Post Method to request for \"{}\" , response: {}", requestUrl, response);
        return response;
    }

    public static String doGet(String requestUrl) {
        StringBuffer buffer = new StringBuffer();
        HttpURLConnection httpUrlConn = null;
        try {
            URL url = new URL(requestUrl);
            httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoOutput(false);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setRequestMethod(ApplicationConstant.GET);
            httpUrlConn.connect();
            // 将返回的输入流转换成字符串
            try (InputStream inputStream = httpUrlConn.getInputStream(); InputStreamReader inputStreamReader = new InputStreamReader(inputStream, ApplicationConstant.ENCODING); BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                String str = null;
                while ((str = bufferedReader.readLine()) != null) {
                    buffer.append(str);
                }
            }
        } catch (Exception e) {
            logger.error("msg",e);
        } finally {
            if (null != httpUrlConn) {
                httpUrlConn.disconnect();
            }
        }
        String response = buffer.toString();
        logger.info("Use Get Method to request for \"{}\" , get response: {}", requestUrl, response);
        return response;
    }
}
