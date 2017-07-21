package com.hengtiansoft.common.util.pay;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QHttpClientUtil {

    private static final Logger log = LoggerFactory.getLogger(QHttpClientUtil.class);

    /**
     * @Description Initiate an https request and get the result
     * @author taochen
     * @Date
     * @param requestUrl
     * @param requestMethod
     *            （GET、POST）
     * @param outputStr
     *            Submitted data
     * @return JSONObject (Through JSONObject.get (key) way to obtain json object attribute value)
     */
    public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
        StringBuffer buffer = new StringBuffer();
        try {
            // Create an SSLContext object and initialize it with our specified trust manager
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // Get the SSLSocketFactory object from the SSLContext object described above
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // Set the request mode (GET / POST)
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // When there is data to be submitted
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // Converts the input stream to a string
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // release resources
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();

        } catch (ConnectException ce) {
            log.error("msg", ce);
            // log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("msg", e);
            // log.error("https request error:{}", e);
        }
        return buffer.toString();
    }

    /**
     * @Description Initiate an https request and get the result
     * @author taochen
     * @Date
     * @param requestUrl
     * @param requestMethod
     *            （GET、POST）
     * @param outputStr
     *            Submitted data
     * @return JSONObject (Through JSONObject.get (key) way to obtain json object attribute value)
     */
    public static String httpRequest1(String requestUrl, String requestMethod, String outputStr) {
        StringBuffer buffer = new StringBuffer();
        try {
            // Create an SSLContext object and initialize it with our specified trust manager
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());

            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            // httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // Set the request mode (GET / POST)
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // When there is data to be submitted
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // Note that the encoding format
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // Converts the input stream to a string
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // release resources
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();

        } catch (ConnectException ce) {
            log.error("msg", ce);
            // log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("msg", e);
            // log.error("https request error:{}", e);
        }
        return buffer.toString();
    }

}
