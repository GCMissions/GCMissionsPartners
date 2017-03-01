package com.hengtiansoft.task.util;

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

public class WechatUtils {

public static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    
    // APP ID
    public static String appid = AppConfigUtil.getConfig("wechat.appid");
    
    // API 密钥
    public static String apiKey = AppConfigUtil.getConfig("wechat.apikey");
    
    // 微信商户号
    public static String mchid = AppConfigUtil.getConfig("wechat.mchid");
    
    /*随机字符串生成办法*/
    private static final String ALLCHAR    = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    

    /**
     * Description: 获取定长的随机字符串（包含大小写字母、数字）
     *
     * @param length 字符串长度
     * @return
     */
    public static String generateString(int length) {
        // 随机拿字符
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        
        return sb.toString();
    }
    
    
    /**
    * Description: 微信公众号支付签名处理
    *
    * @param params
    * @param apiKey
    * @return
    */
    public static String sign(Map<String, String> params, String apiKey) {
        // 字符串拼接
        String signContent = generateSignVertifyString(params, apiKey);
        
        // MD5加密并返回
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
        // 参数名按字符表排序
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        // 参数组装
        StringBuffer sb = new StringBuffer();
        for (String key : keys) {
            String value = params.get(key);
            if (StringUtils.isNotBlank(value)) {
                sb.append(key).append("=").append(value).append("&");
            }
        }
        
        // 商户自定义密钥
        sb.append("key").append("=").append(apiKey);
          
        return sb.toString();
    }
    
    
    /**
    * Description: 将Map转换为微信要求的XML请求参数格式
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
     * Description: 将微信返回的XML数据解析成MAP
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
     * Description: 微信支付结果通知签名验证
     *
     * @return
     */
//    public static boolean validateSign(Map<String, String> params) {
//        // 排除签名后的数据
//        Map<String, String> map = new HashMap<String, String>();
//        for (String key : params.keySet()) {
//            if (!"sign".equals(key)) {
//                map.put(key, params.get(key));
//            }
//        }
//
//        // MD5加密
//        String newSign = WechatUtils.sign(map, WechatConfig.apiKey);
//
//        // 验证签名
//        String sign = params.get("sign");
//        return sign.equals(newSign);
//    }

    /**
    * Description: 微信支付通知结果
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
