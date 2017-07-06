package com.hengtiansoft.church.common.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import com.hengtiansoft.common.util.AppConfigUtil;
import com.hengtiansoft.common.util.EncryptUtil;

/**
 * Class Name: WechatUtils Description: WeChat of the public account development interface tool class
 * 
 * @author taochen
 *
 */
public class WechatUtils {

    public static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    // APP ID
    public static String appid = AppConfigUtil.getConfig("wechat.appid");

    // API Key
    public static String apiKey = AppConfigUtil.getConfig("wechat.apikey");

    // The merchant number of the WeChat
    public static String mchid = AppConfigUtil.getConfig("wechat.mchid");

    /* Generates a random string */
    private static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Description: Get a random string of the specified length (including uppercase and lowercase letters, numbers)
     *
     * @param The
     *            length of the string
     * @return
     */
    public static String generateString(int length) {
        // Randomly get the character
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }

        return sb.toString();
    }

    /**
     * Description: WeChar letter of the public account to pay the signature processing
     *
     * @param params
     * @param apiKey
     * @return
     */
    public static String sign(Map<String, String> params, String apiKey) {
        // Stitching string
        String signContent = generateSignVertifyString(params, apiKey);

        // Returns the result of MD5 encryption
        return EncryptUtil.encryptMd5(signContent).toUpperCase();
    }

    /**
     * Description:
     *
     * @param params
     * @param apiKey
     * @return
     */
    private static String generateSignVertifyString(Map<String, String> params, String apiKey) {
        // Parameter names are sorted by character table
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        // Assembly parameters
        StringBuffer sb = new StringBuffer();
        for (String key : keys) {
            String value = params.get(key);
            if (StringUtils.isNotBlank(value)) {
                sb.append(key).append("=").append(value).append("&");
            }
        }

        // Merchant custom key
        sb.append("key").append("=").append(apiKey);

        return sb.toString();
    }

    /**
     * Description: Converts the Map to the XML request parameter format required by the WeChat
     *
     * @param params
     * @return
     */
    public static String getRequestXml(Map<String, String> params) {
        StringBuffer sb = new StringBuffer();

        sb.append("<xml>");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if ("attach".equalsIgnoreCase(key) || "body".equalsIgnoreCase(key)) {
                sb.append("<" + key + ">" + "<![CDATA[" + value + "]]></" + key + ">");
            } else {
                sb.append("<" + key + ">" + value + "</" + key + ">");
            }
        }
        sb.append("</xml>");

        return sb.toString();
    }

    /**
     * Description: WeChat the XML data into MAP type
     *
     * @param xmlStr
     * @return
     */
    public static Map<String, String> doXMLParse(String xmlStr) {
        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();
        try {
            InputSource source = new InputSource(new StringReader(xmlStr));

            Document doc = reader.read(source);
            Element root = doc.getRootElement();

            @SuppressWarnings("unchecked")
            List<Element> list = root.elements();
            for (Element e : list) {
                map.put(e.getName(), e.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Description:WeChat payment notification results
     *
     * @param string
     * @param string2
     * @return
     */
    public static String notifyReturn(String code, String msg) {
        StringBuffer sb = new StringBuffer();

        sb.append("<xml>");
        sb.append("<return_code><![CDATA[").append(code).append("]]></return_code>");
        sb.append("<return_msg><![CDATA[").append(msg).append("]]></return_msg>");
        sb.append("</xml>");

        return sb.toString();
    }

}
