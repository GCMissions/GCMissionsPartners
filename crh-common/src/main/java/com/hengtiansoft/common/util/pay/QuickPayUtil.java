package com.hengtiansoft.common.util.pay;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.hengtiansoft.common.util.EncryptUtil;
import com.hengtiansoft.common.util.pay.ib.IbPayConfig;

public class QuickPayUtil {

    /** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     * @throws UnsupportedEncodingException 
     */
    public static String createLinkString(LinkedHashMap<String, Object> params) throws UnsupportedEncodingException {

        List<String> keys = new ArrayList<String>(params.keySet());

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = String.valueOf(params.get(key));

            // 拼接时，不包括最后一个&字符
            if (i == keys.size() - 1) {
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
    
    public static String createSign(LinkedHashMap<String, Object> parameters,  String key) {
        Set<String> keyArr = parameters.keySet();
        StringBuffer sb = new StringBuffer();
        for (String k : keyArr) {
            Object v = parameters.get(k);
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"secret".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("secret=" + key);
        return EncryptUtil.encryptMd5(sb.toString()).toUpperCase();
    }
    
    public static String generateIbpayForm(LinkedHashMap<String, String> map) {
        StringBuffer sbHtml = new StringBuffer();
        sbHtml.append("<form id=\"ibpaysubmit\" name=\"ibpaysubmit\" action=\"" + IbPayConfig.getGateway() 
                + "\" method=\"" + "post" + "\">");
        for (String key : map.keySet()) {
            sbHtml.append("<input type=\"hidden\" name=\"" + key + "\" value=\"" + map.get(key) + "\"/>");
        }
        // submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"" + "提交" + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['ibpaysubmit'].submit();</script>");
        return sbHtml.toString();
    }
    
    public static void main(String[] args) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("custom_no", "linkea.pro");
        map.put("merchant_no", "C000001");
        map.put("orderid", "SL2943029993637888_1468466236098");
        map.put("order_amount", "0.01");
        System.out.println(createSign(map, "752eb71b1ba3413e883fd567e5b6d369"));
    }
}
