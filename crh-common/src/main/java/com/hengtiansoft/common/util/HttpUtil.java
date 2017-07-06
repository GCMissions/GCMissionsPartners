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
 * Class Name: HttpRequestUtil 
 * Description: Send http request
 * 
 * @author taochen
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
            // When there is data to be submitted
            if (null != params) {
                try (OutputStream outputStream = httpUrlConn.getOutputStream()) {
                    // Note that the encoding format
                    outputStream.write(params.getBytes(ApplicationConstant.ENCODING));
                    outputStream.flush();
                }
            }
            // Converts the returned input stream to a string
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
            // Converts the returned input stream to a string
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
