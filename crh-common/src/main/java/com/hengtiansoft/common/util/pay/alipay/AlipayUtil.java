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
    private static final Logger log = LoggerFactory.getLogger(AlipayUtil.class);
    /**
     * Alipay message verification URL
     */
    private static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";

    /**
     * Description: signature
     *
     * @param content
     *            Waiting for signatures
     * @return
     */
    public static String sign(String content) {
        return RSA.sign(content, AlipayConfig.privateKey, AlipayConfig.charset);
    }

    /**
     * Verify that the message is a legitimate message sent by Alipay
     * 
     * @param params
     *            An array of parameters returned from the information
     * @return Validation results
     */
    public static boolean vertify(Map<String, String> params) {
        // Verify the source of the notification
        String responseTxt = "false";
        if (null != params.get("notify_id")) {
            String notify_id = params.get("notify_id");
            responseTxt = vertifyResponse(notify_id);
        }

        // Verify the signature
        boolean isSign = false;
        if (null != params.get("sign")) {
            try {
                isSign = getSignVertify(params, params.get("sign"));
            } catch (UnsupportedEncodingException e) {
                log.error("msg", e);
                isSign = false;
            }
        }

        return isSign && responseTxt.equals("true");
    }

    /**
     * Get the remote server ATN result, verify and return the URL
     * 
     * @param notify_id
     * @return Server ATN results Verify the result set： invalid Command parameters are incorrect. If this error occurs,
     *         check whether partner and key are empty in return processing true Return the correct information false
     *         Check the firewall or server to block port issues and verify that the time is more than one minute
     */
    private static String vertifyResponse(String notify_id) {
        // Get the remote server ATN results ,verify that the request was sent by the Alipay server.

        String partner = AlipayConfig.partner;
        String veryfy_url = HTTPS_VERIFY_URL + "partner=" + partner + "&notify_id=" + notify_id;

        return checkUrl(veryfy_url);
    }

    /**
     * Get the remote server ATN results
     * 
     * @param urlvalue
     *            Specifies the URL path address
     * @return Server ATN results Verify the result set： invalid Command parameters are incorrect. If this error occurs,
     *         check whether partner and key are empty in return processing true Return the correct information false
     *         Check the firewall or server to block port issues and verify that the time is more than one minute
     */
    private static String checkUrl(String urlvalue) {
        String inputLine = "";

        try {
            URL url = new URL(urlvalue);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            inputLine = in.readLine().toString();
        } catch (Exception e) {
            log.error("msg", e);
            inputLine = "";
        }

        return inputLine;
    }

    /**
     * According to the feedback back to the information, generate signature results
     * 
     * @param Params
     *            An array of parameters returned from the information
     * @param sign
     *            Comparison of signature results
     * @return Generated signature results
     * @throws UnsupportedEncodingException
     */
    private static boolean getSignVertify(Map<String, String> Params, String sign) throws UnsupportedEncodingException {
        // Filter null, sign and sign_type parameters
        Map<String, String> sParaNew = paraFilter(Params);
        // Get the string to be signed
        String preSignStr = createLinkString(sParaNew);
        // Get signature verification results
        boolean isSign = false;
        if (AlipayConfig.signType.equals("RSA")) {
            isSign = RSA.verify(preSignStr, sign, AlipayConfig.publicKey, AlipayConfig.charset);
        }
        return isSign;
    }

    /**
     * Removes null and signature parameters from the array
     * 
     * @param params
     *            Signature parameters
     * @return
     */
    public static Map<String, String> paraFilter(Map<String, String> params) {
        Map<String, String> result = new HashMap<String, String>();
        if (params == null || params.isEmpty()) {
            return result;
        }

        // Remove the parameter "sign", "sign_type": do not participate in signature or signature verification
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
     * Sort all the elements of the array and concatenate them into a string with the "&" character according to the
     * "parameter = parameter value" pattern
     * 
     * @param params
     *            The parameters that need to be sorted, and the parameters that require character splicing
     * @return The string after splicing
     * @throws UnsupportedEncodingException
     */
    public static String createLinkString(Map<String, String> params) throws UnsupportedEncodingException {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            // When stitching, not including the last character "&"
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
        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + AlipayConfig.gateway
                + "_input_charset=" + AlipayConfig.charset + "\" method=\"" + "post" + "\">");
        for (String key : map.keySet()) {
            sbHtml.append("<input type=\"hidden\" name=\"" + key + "\" value=\"" + map.get(key) + "\"/>");
        }
        // Submit button controls do not include the name attribute
        sbHtml.append("<input type=\"submit\" value=\"" + "submit" + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");
        return sbHtml.toString();
    }
}
