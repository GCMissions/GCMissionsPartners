package com.hengtiansoft.common.util;


import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @Title: HttpUtils.java
 * 
 * @Package com.zcckj.common.util
 * 
 * 
 * @Description: Http tool class (including get real ip, add and cancel cookies, send http request post or get)
 * 
 * @author taochen
 * 
 * @date 
 * 
 * @version V1.0
 */

public class HttpUtils {
    private static final Logger log= LoggerFactory.getLogger(HttpUtils.class);
	private HttpUtils() {
	}

	/**
	 * cancel cookie
	 * 
	 * @author mapengwei
	 * @param request
	 * @param response
	 * @param name
	 * @param domain
	 */
	public static void cancleCookie(HttpServletRequest request, HttpServletResponse response, String name, String domain) {
		Cookie cookie = new Cookie(name, "");
		cookie.setMaxAge(0);
		String ctx = request.getContextPath();
		cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
		if (StringUtils.isNotBlank(domain)) {
			cookie.setDomain(domain);
		}
		response.addCookie(cookie);
	}

	/**
	 * set cookie
	 * 
	 * @param response
	 * @param cookieName
	 *            cookie name
	 * @param cookieValue
	 *            cookie value
	 * @param time
	 *            cookie effective time
	 */
	public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue, int time) {
		try {
			if (cookieValue != null)
				cookieValue = URLEncoder.encode(cookieValue, "UTF-8");

		} catch (Exception ex) {
		    log.error("msg",ex);
		}

		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setMaxAge(time);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	/**
	 * set cookie
	 * 
	 * @param response
	 * @param domain
	 *            save cookie domain 
	 * @param path
	 *            Save the cookie path
	 * @param cookieName
	 *            cookie name
	 * @param cookieValue
	 *            cookie value
	 * @param time
	 *            cookie effective time
	 */
	public static void addCookie(HttpServletResponse response, String domain, String path, String cookieName, String cookieValue, int time) {
		try {
			cookieValue = URLEncoder.encode(cookieValue, "UTF-8");
		} catch (Exception ex) {
		}
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setMaxAge(time);
		if (null != domain) {
			cookie.setDomain(domain);
		}
		cookie.setPath(path);
		response.addCookie(cookie);
	}

	public static void addCookie1(HttpServletResponse response, String cookieName, String cookieValue, int time) {

		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setMaxAge(time);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	/**
	 * Get the cookie value by cookie name
	 * 
	 * @param request
	 * @param cookieName
	 * @param domain
	 * @param path
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request, String cookieName, String domain, String path) {
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (domain.equals(cookies[i].getDomain()) && path.equals(cookies[i].getPath()) && cookieName.equals(cookies[i].getName())) {
					return cookies[i].getValue();
				}
			}
		}
		return null;
	}

	/**
	 * Get the value of the cookie based on the cookie name
	 * 
	 * @param HttpServletRequest
	 *            request request object
	 * @param name
	 *            cookie name
	 * @return string cookie value , Return null when fail to get a cookie value
	 */
	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {

				if (cookieName.equals(cookies[i].getName())) {
					return cookies[i].getValue();
				}
			}
		}
		return null;
	}

	/**
	 * POST Request method
	 * 
	 * @param url
	 *            URL
	 * @param parameterMap
	 *            Request parameter
	 * @return 
	 */
	public static String post(String url, Map<String, Object> parameterMap) {
		Assert.hasText(url);
		String siteUrl = StringUtils.lowerCase(url);
		if (siteUrl.startsWith("https")) {
			return postSSL(siteUrl, parameterMap);
		}
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			if (parameterMap != null) {
				for (Entry<String, Object> entry : parameterMap.entrySet()) {
					String name = entry.getKey();
					String value = ConvertUtils.convert(entry.getValue());
					if (StringUtils.isNotEmpty(name)) {
						nameValuePairs.add(new BasicNameValuePair(name, value));
					}
				}
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			result = EntityUtils.toString(httpEntity);
			EntityUtils.consume(httpEntity);
		} catch (ClientProtocolException e) {
		    log.error("msg",e);
		} catch (IOException e) {
		    log.error("msg",e);
		} 
		return result;
	}

	private static String postSSL(String url, Map<String, Object> parameterMap) {
		Assert.hasText(url);
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(1000).build();
		HttpPost httpPost = null;
		try {
			SSLContext sslContext = SSLContexts.createDefault();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new String[] { "TLSv1" }, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			if (parameterMap != null) {
				for (Entry<String, Object> entry : parameterMap.entrySet()) {
					String name = entry.getKey();
					String value = ConvertUtils.convert(entry.getValue());
					if (StringUtils.isNotEmpty(name)) {
						nameValuePairs.add(new BasicNameValuePair(name, value));
					}
				}
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			result = EntityUtils.toString(httpEntity);
			EntityUtils.consume(httpEntity);
		} catch (Exception e) {
		    log.error("msg",e);
		} finally {
			httpPost.abort();
		}
		return result;
	}

	/**
	 * GET Request method
	 * 
	 * @param url
	 *            URL
	 * @param parameterMap
	 *            Request parameter
	 * @return 
	 */
	public static String get(String url, Map<String, Object> parameterMap) {
		Assert.hasText(url);
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			if (parameterMap != null) {
				for (Entry<String, Object> entry : parameterMap.entrySet()) {
					String name = entry.getKey();
					String value = ConvertUtils.convert(entry.getValue());
					if (StringUtils.isNotEmpty(name)) {
						nameValuePairs.add(new BasicNameValuePair(name, value));
					}
				}
			}
			HttpGet httpGet = new HttpGet(url + (StringUtils.contains(url, "?") ? "&" : "?") + EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs, "UTF-8")));
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			result = EntityUtils.toString(httpEntity);
			EntityUtils.consume(httpEntity);
		} catch (ClientProtocolException e) {
		    log.error("msg",e);
		} catch (IOException e) {
		    log.error("msg",e);
		} 
		return result;
	}
	
	public static String getUserIP(HttpServletRequest request){
        return request.getHeader("X-Real-IP") == null ? getPublicIP(request) : request.getHeader("X-Real-IP");
    }
	
	public static String getPublicIP(HttpServletRequest request) {
        String ip = request.getRemoteAddr();

        if ("0:0:0:0:0:0:0:1".equals(ip) || isInner(ip)) {
            InputStream ins = null;
            try {
                String webContent = HttpUtil.doGet("http://www.ip138.com/ips1388.asp");
                if (StringUtils.isNotBlank(webContent)) {
                    webContent = webContent.substring(webContent.indexOf("<BODY>"));
                    int start = webContent.indexOf("[") + 1;
                    int end = webContent.indexOf("]");
                    ip = webContent.substring(start, end);
                }
            } catch (Exception e) {
                ip = null;
            } finally {
                if (ins != null) {
                    try {
                        ins.close();
                    } catch (IOException e) {
                        //
                    }
                }
            }
        }
        return ip;
    }

    private static boolean isInner(String ip) {
        String reg = "(10|172|192)\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})";
        Pattern p = Pattern.compile(reg);
        Matcher matcher = p.matcher(ip);
        return matcher.find();
    }
	
	public static void main(String[] args) {
		System.out.println(get("http://1.jiajiag.com/lottery/buy_history.cgi?time=1430363119327&pid=1981&_=1430363119327", null));
	}
}
