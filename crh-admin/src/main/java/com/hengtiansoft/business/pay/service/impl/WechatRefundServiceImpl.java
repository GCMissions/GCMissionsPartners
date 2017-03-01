package com.hengtiansoft.business.pay.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.common.util.WechatUtils;
import com.hengtiansoft.business.pay.service.WechatRefundService;
import com.hengtiansoft.common.util.AppConfigUtil;
import com.hengtiansoft.common.util.DateTimeUtil;

@Service
public class WechatRefundServiceImpl implements WechatRefundService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

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
                    + "wechat_key/" + AppConfigUtil.getConfig("env") + "/apiclient_cert.p12")); // 读取证书

            // 指定PKCS12的密码(商户ID)
            try {
                keyStore.load(instream, WechatUtils.mchid.toCharArray());
            } finally {
                instream.close();
            }

            // 信任自己的CA和自签名证书
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, WechatUtils.mchid.toCharArray())
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
            refundMap.put("appid", WechatUtils.appid);
            // 商户ID
            refundMap.put("mch_id", WechatUtils.mchid);
            // 随机字符串
            refundMap.put("nonce_str", WechatUtils.generateString(30));
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
            refundMap.put("op_user_id", WechatUtils.mchid);

            // 参数名按字符表排序
            List<String> keys = new ArrayList<String>(refundMap.keySet());
            Collections.sort(keys);

            // 签名
            refundMap.put("sign", WechatUtils.sign(refundMap, WechatUtils.apiKey));

            // 将Map转换为XML字符串
            String requestXml = WechatUtils.getRequestXml(refundMap);

            // 请求
            StringEntity entity = new StringEntity(requestXml);
            httppost.setEntity(entity);
            responseEntry = httpclient.execute(httppost);

            // 解析请求结果
            HttpEntity httpEntity = responseEntry.getEntity();
            String result = EntityUtils.toString(httpEntity, "UTF-8");

            Map<String, String> resultMap = WechatUtils.doXMLParse(result);
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

}
