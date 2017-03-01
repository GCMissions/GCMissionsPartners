package com.hengtiansoft.common.util.pay.alipay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.common.util.pay.RSA;

public class AlipayUtil {
    private static final Logger log= LoggerFactory.getLogger(AlipayUtil.class);
    /**
     * 支付宝消息验证地址
     */
    private static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";

    /**
     * Description: 签名
     *
     * @param content 待签名内容
     * @return
     */
    public static String sign(String content) {
        return RSA.sign(content, AlipayConfig.privateKey, AlipayConfig.charset);
    }

    /**
     * 验证消息是否是支付宝发出的合法消息
     * @param params 通知返回来的参数数组
     * @return 验证结果
     */
    public static boolean vertify(Map<String, String> params) {
        // 验证通知来源
        String responseTxt = "false";
        if (null != params.get("notify_id")) {
            String notify_id = params.get("notify_id");
            responseTxt = vertifyResponse(notify_id);
        }

        // 验证签名
        boolean isSign = false;
        if (null != params.get("sign")) {
            try {
                isSign = getSignVertify(params, params.get("sign"));
            } catch (UnsupportedEncodingException e) {
                log.error("msg",e);
                isSign = false;
            }
        }
        
        return isSign && responseTxt.equals("true");
    }

    /**
     * 获取远程服务器ATN结果,验证返回URL
     * @param notify_id 通知校验ID
     * @return 服务器ATN结果
     * 验证结果集：
     * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 
     * true 返回正确信息
     * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
     */
    private static String vertifyResponse(String notify_id) {
        //获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求

        String partner = AlipayConfig.partner;
        String veryfy_url = HTTPS_VERIFY_URL + "partner=" + partner + "&notify_id=" + notify_id;

        return checkUrl(veryfy_url);
    }

    /**
     * 获取远程服务器ATN结果
     * @param urlvalue 指定URL路径地址
     * @return 服务器ATN结果
     * 验证结果集：
     * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 
     * true 返回正确信息
     * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
     */
    private static String checkUrl(String urlvalue) {
        String inputLine = "";

        try {
            URL url = new URL(urlvalue);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection
                    .getInputStream()));
            inputLine = in.readLine().toString();
        } catch (Exception e) {
            log.error("msg",e);
            inputLine = "";
        }

        return inputLine;
    }

    /**
     * 根据反馈回来的信息，生成签名结果
     * @param Params 通知返回来的参数数组
     * @param sign 比对的签名结果
     * @return 生成的签名结果
     * @throws UnsupportedEncodingException 
     */
    private static boolean getSignVertify(Map<String, String> Params, String sign) throws UnsupportedEncodingException {
        //过滤空值、sign与sign_type参数
        Map<String, String> sParaNew = paraFilter(Params);
        //获取待签名字符串
        String preSignStr = createLinkString(sParaNew);
        //获得签名验证结果
        boolean isSign = false;
        if (AlipayConfig.signType.equals("RSA")) {
            isSign = RSA.verify(preSignStr, sign, AlipayConfig.publicKey, AlipayConfig.charset);
        }
        return isSign;
    }

    /** 
     * 除去数组中的空值和签名参数
     * @param params 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> params) {
        Map<String, String> result = new HashMap<String, String>();
        if (params == null || params.isEmpty()) {
            return result;
        }

        // 去除参数"sign","sign_type"：不参与签名或签名验证
        for (String key : params.keySet()) {
            if ("sign".equalsIgnoreCase(key) || "sign_type".equalsIgnoreCase(key)) {
                continue;
            }

            String value = params.get(key);
            if (WRWUtil.isNotEmpty(value)) {
                result.put(key, value);
            }
        }
        return result;
    }

    /** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     * @throws UnsupportedEncodingException 
     */
    public static String createLinkString(Map<String, String> params) throws UnsupportedEncodingException {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            //拼接时，不包括最后一个&字符
            if (i == keys.size() - 1) {
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        
        return prestr;
    }
    

    public static String generateAlipayForm(Map<String, String> map) {
        StringBuffer sbHtml = new StringBuffer();
        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + AlipayConfig.gateway + "_input_charset=" + AlipayConfig.charset
                + "\" method=\"" + "post" + "\">");
        for (String key : map.keySet()) {
            sbHtml.append("<input type=\"hidden\" name=\"" + key + "\" value=\"" + map.get(key) + "\"/>");
        }
        // submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"" + "提交" + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");
        return sbHtml.toString();
    }
}
