package com.hengtiansoft.task.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.EncryptUtil;
import com.hengtiansoft.task.service.WechatRefundService;
import com.hengtiansoft.task.util.WechatUtils;

@Service
public class WechatRefundServiceImpl implements WechatRefundService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Value("${env}")
    private String enviroment;
    
    @Value("${wechat.mchid}")
    private String mchid;
    
    @Value("${wechat.apikey}")
    private String apiKey;
    
    @Value("${wechat.appid}")
    private String appid;
    
    /*随机字符串生成办法*/
    private static final String ALLCHAR    = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public WechatRefundServiceImpl() {
        super();
    }

    @SuppressWarnings("deprecation")
    @Override
    public String wechatRefund(String totalFee, String refundFee, String refundOrderId, String tsn) {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse responseEntry = null;
        try {
            // 读取证书
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            FileInputStream instream = new FileInputStream(new File(getClass().getResource("/").getPath()
                    + "wechat_key/" + enviroment + "/apiclient_cert.p12")); // 读取证书

            // 指定PKCS12的密码(商户ID)
            try {
                keyStore.load(instream, mchid.toCharArray());
            } finally {
                instream.close();
            }

            // 信任自己的CA和自签名证书
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchid.toCharArray())
                    .build();

            // 指定TLS版本
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
                    null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

            // 设置httpclient的SSLSocketFactory
            httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

            // 请求退款
            HttpPost httppost = new HttpPost(WechatUtils.REFUND_URL);

            // 参数组成
            Map<String, String> refundMap = new HashMap<String, String>();
            // 公众号ID
            refundMap.put("appid", appid);
            // 商户ID
            refundMap.put("mch_id", mchid);
            // 随机字符串
            refundMap.put("nonce_str", generateString(30));
            // 商户订单号
            refundMap.put("out_trade_no", "");
            // 微信订单号
            refundMap.put("transaction_id", tsn);
            // 商户退款单号
            String currentTime = DateTimeUtil.getCurrentTime(DateTimeUtil.SIMPLE_SECONDS);
            refundMap.put("out_refund_no", refundOrderId + currentTime);
            // 订单金额
            refundMap.put("total_fee", totalFee);
            // 退款金额
            refundMap.put("refund_fee", refundFee);
            // 操作员,默认为商户号
            refundMap.put("op_user_id", mchid);

            // 参数名按字符表排序
            List<String> keys = new ArrayList<String>(refundMap.keySet());
            Collections.sort(keys);

            // 签名
            refundMap.put("sign", sign(refundMap, apiKey));

            // 将Map转换为XML字符串
            String requestXml = getRequestXml(refundMap);

            // 请求
            StringEntity entity = new StringEntity(requestXml);
            httppost.setEntity(entity);
            responseEntry = httpclient.execute(httppost);

            // 解析请求结果
            HttpEntity httpEntity = responseEntry.getEntity();
            String result = EntityUtils.toString(httpEntity, "UTF-8");

            Map<String, String> resultMap = doXMLParse(result);
            logger.debug(resultMap.get("result_code"));
            return resultMap.get("result_code");

        } catch (Exception e) {
            logger.error("微信退款时出错：{}", e);
            return null;
        } finally {
            try {
                responseEntry.close();
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Description: 获取定长的随机字符串（包含大小写字母、数字）
     *
     * @param length 字符串长度
     * @return
     */
    private String generateString(int length) {
        // 随机拿字符
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        
        return sb.toString();
    }
    
    /**
     * Description: 将Map转换为微信要求的XML请求参数格式
     *
     * @param params
     * @return
     */
     private String getRequestXml(Map<String, String> params) {
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
      * Description: 微信公众号支付签名处理
      *
      * @param params
      * @param apiKey
      * @return
      */
      private String sign(Map<String, String> params, String apiKey) {
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
       private String generateSignVertifyString(Map<String, String> params, String apiKey) {
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
        * Description: 将微信返回的XML数据解析成MAP
        *
        * @param xmlStr
        * @return
        */
       private Map<String, String> doXMLParse(String xmlStr) {
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
