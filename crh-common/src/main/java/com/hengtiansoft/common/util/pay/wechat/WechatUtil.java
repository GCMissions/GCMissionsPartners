package com.hengtiansoft.common.util.pay.wechat;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import com.hengtiansoft.common.util.EncryptUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.common.util.pay.QHttpClientUtil;

public class WechatUtil {

    /**
     * Description: 获取微信统一下单单号
     *
     * @param params
     * @param unifieOrderUrl
     * @returnk
     */
    public static String getPrepayId(Map<String, String> params) {
        String requestXml = getRequestXml(params);
        String resultXml = QHttpClientUtil.httpRequest(WechatConfig.unifiedorderUrl, "POST", requestXml);
        
       return doXMLParse(resultXml).get("prepay_id");
        
    }
    
    /**
     * Description: 微信签名
     *
     * @param params 请求参数
     * @return
     */
    public static String Sign(Map<String, String> params, String apiKey) {
        String signContent = generateSignVertifyString(params);
        
        return EncryptUtil.encryptMd5(signContent).toUpperCase();
    }
    
    /**
     * Description: 组装微信签名参数
     *
     * @param params
     * @param apiKey
     * @return
     */
    private static String generateSignVertifyString(Map<String, String> params) {
        // 参数名按字符表排序
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        // 参数组装
        StringBuffer sb = new StringBuffer();
        for (String key : keys) {
            String value = params.get(key);
            if (WRWUtil.isNotEmpty(value)) {
                sb.append(key).append("=").append(value).append("&");
            }
        }
        
        // 附加密钥
        sb.append("key").append("=").append(WechatConfig.apiKey);
          
        return sb.toString();
    }
    
    /**
     * Description：参数XML转义
     */
    public static String getRequestXml(Map<String, String> params) {
        StringBuffer sb = new StringBuffer();
        
        sb.append("<xml>");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if ("attach".equalsIgnoreCase(key) || "body".equalsIgnoreCase(key) || "sign".equalsIgnoreCase(key)) {
                sb.append("<" + key + ">" + "<![CDATA[" + value + "]]></" + key + ">");
            } else {
                sb.append("<" + key + ">" + value + "</" + key + ">");
            }
        }
        sb.append("</xml>");
       
        return sb.toString();
    }
    
    /**
     * Description: 生成微信回调返回结果
     *
     * @param code
     * @param msg
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
    
    /**
     * Description: xml解析
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
}
