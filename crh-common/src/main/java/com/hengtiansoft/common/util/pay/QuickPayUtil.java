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
     * Sort all the elements of the array and concatenate them into a string with the "&" character according to the
     * "parameter = parameter value" pattern
     * 
     * @param params
     *            Need to be sorted and participate in the character splicing of the combination of parameters
     * @return The string after splicing
     * @throws UnsupportedEncodingException
     */
    public static String createLinkString(LinkedHashMap<String, Object> params) throws UnsupportedEncodingException {

        List<String> keys = new ArrayList<String>(params.keySet());

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = String.valueOf(params.get(key));

            // When splicing, the last "&" character is not included
            if (i == keys.size() - 1) {
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    public static String createSign(LinkedHashMap<String, Object> parameters, String key) {
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
        // The submit button control should not contain the name attribute
        sbHtml.append("<input type=\"submit\" value=\"" + "submit" + "\" style=\"display:none;\"></form>");
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
