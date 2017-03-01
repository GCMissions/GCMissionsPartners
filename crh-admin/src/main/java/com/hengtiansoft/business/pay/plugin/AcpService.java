/*
 * Project Name: kd-wechat
 * File Name: AcpService.java
 * Class Name: AcpService
 *
 * Copyright 2014 Hengtian Software Inc
 *
 * Licensed under the Hengtiansoft
 *
 * http://www.hengtiansoft.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.business.pay.plugin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hengtiansoft.business.pay.constant.PayConstants;
import com.unionpay.acp.sdk.CertUtil;
import com.unionpay.acp.sdk.HttpClient;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SDKUtil;
import com.unionpay.acp.sdk.SecureUtil;

/**
 * Class Name: AcpService
 * Description: 银联支付
 * @author zhongyidong
 *
 */
public class AcpService {
    
    private static final int TIME_LIMIT1 = 30000;
    private static final int TIME_LIMIT2 = 300000;
    private static final int STATUS_200 = 200;
    private static final Logger LOGGER = LoggerFactory.getLogger(AcpService.class);

    /**
     * 请求报文签名(使用配置文件中配置的私钥证书加密)<br>
     * 功能：对请求报文进行签名,并计算赋值certid,signature字段并返回<br>
     * @param reqData 请求报文map<br>
     * @param encoding 上送请求报文域encoding字段的值<br>
     * @return　签名后的map对象<br>
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> signData(Map<String, String> contentData) {
        Entry<String, String> obj = null;
        Map<String, String> submitFromData = new HashMap<String, String>();
        for (Iterator<?> it = contentData.entrySet().iterator(); it.hasNext();) {
            obj = (Entry<String, String>) it.next();
            String value = obj.getValue();
            if (StringUtils.isNotBlank(value)) {
                submitFromData.put(obj.getKey(), value.trim());
            }
        }
        SDKUtil.sign(submitFromData, PayConstants.UNION_ENCODING); // 签名
        return submitFromData;
    }
    
    /**
     * Description: 后台交易提交请求报文并接收同步应答报文
     * @param reqData 请求报文
     * @param reqUrl  请求地址
     * 
     * @return 
     */
    public static Map<String, String> post(Map<String, String> reqData, String reqUrl) {
        Map<String, String> rspData = new HashMap<String, String>();
        // 获取连接
        HttpClient hc = new HttpClient(reqUrl, TIME_LIMIT1, TIME_LIMIT2);
        try {
            int status = hc.send(reqData, PayConstants.UNION_ENCODING);
            if (STATUS_200 == status) {
                String resultString = hc.getResult();
                // 验证签名
                if (StringUtils.isNotBlank(resultString)) {
                    // 将返回结果转换为map
                    Map<String, String> tmpRspData = SDKUtil.convertResultStringToMap(resultString);
                    rspData.putAll(tmpRspData);
                }
                LOGGER.error("[返回报文]：" + rspData.toString());
            } else {
                LOGGER.error("返回http状态码[{}]，请检查请求报文或者请求地址是否正确", status);
            }
        } catch (Exception e) {
            LOGGER.error("银联支付请求失败！{}", e);
        }

        return rspData;
    }
    
    /**
     * Description: 获取请求参数中所有的信息.
     * 
     * @param request
     * @return
     */
    public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
        Map<String, String> reqParams = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                reqParams.put(en, value);
                // 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                if (reqParams.get(en) == null || "".equals(reqParams.get(en))) {
                    reqParams.remove(en);
                }
            }
        }
        return reqParams;
    }
    
    /**
     * Description: 获取验签数据
     *
     * @param reqParams
     * @param encoding
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String> getValideData(Map<String, String> reqParams, String encoding) 
            throws UnsupportedEncodingException{
        Map<String, String> valideData = null;
        if (null != reqParams && !reqParams.isEmpty()) {
            Iterator<Entry<String, String>> it = reqParams.entrySet().iterator();
            valideData = new HashMap<String, String>(reqParams.size());
            while (it.hasNext()) {
                Entry<String, String> e = it.next();
                String key = (String) e.getKey();
                String value = (String) e.getValue();
                value = StringEscapeUtils.unescapeHtml(new String(value.getBytes(encoding), encoding));
                valideData.put(key, value);
            }
        }
        return valideData;
    }
    
    /**
     * Description: 构造HTTP POST交易表单.
     *
     * @param action
     *            表单提交地址
     * @param hiddens
     *            以MAP形式存储的表单键值
     * @return
     */
    public static String createHtml(String action, Map<String, String> hiddens) {
        StringBuffer sf = new StringBuffer();
        sf.append("<form id = \"pay_form\" action=\"" + action + "\" method=\"post\">");
        if (null != hiddens && 0 != hiddens.size()) {
            Set<Entry<String, String>> set = hiddens.entrySet();
            Iterator<Entry<String, String>> it = set.iterator();
            while (it.hasNext()) {
                Entry<String, String> ey = it.next();
                sf.append("<input type=\"hidden\" name=\"" + ey.getKey() + "\" id=\"" + ey.getKey() + "\" value=\""
                        + ey.getValue() + "\"/>");
            }
        }
        sf.append("</form>");
        sf.append("<script type=\"text/javascript\">" + "document.all.pay_form.submit();" + "</script>");
        return sf.toString();
    }
    
    
    /**
     * Description: 持卡人信息域customerInfo构造<br>
     * 说明：不勾选对敏感信息加密权限使用旧的构造customerInfo域方式，不对敏感信息进行加密（对 phoneNo，cvn2， expired不加密），但如果送pin的话则加密<br>
     * @param customerInfoMap 信息域请求参数 key送域名value送值,必送<br>
     *        例如：customerInfoMap.put("certifTp", "01");                 //证件类型<br>
                  customerInfoMap.put("certifId", "341126197709218366");    //证件号码<br>
                  customerInfoMap.put("customerNm", "互联网");             //姓名<br>
                  customerInfoMap.put("phoneNo", "13552535506");            //手机号<br>
                  customerInfoMap.put("smsCode", "123456");                 //短信验证码<br>
                  customerInfoMap.put("pin", "111111");                     //密码（加密）<br>
                  customerInfoMap.put("cvn2", "123");                       //卡背面的cvn2三位数字（不加密）<br>
                  customerInfoMap.put("expired", "1711");                   //有效期 年在前月在后（不加密)<br>
     * @param accNo  customerInfoMap送了密码那么卡号必送,如果customerInfoMap未送密码pin，此字段可以不送<br>
     * @param encoding 上送请求报文域encoding字段的值<br>                
     * @return base64后的持卡人信息域字段<br>
     */
    public static String getCustomerInfo(Map<String,String> customerInfoMap, String accNo,String encoding) {
        if (customerInfoMap.isEmpty()) {
            return "{}";
        }
        StringBuffer sf = new StringBuffer("{");
        for (Iterator<String> it = customerInfoMap.keySet().iterator(); it.hasNext();) {
            String key = it.next();
            String value = customerInfoMap.get(key);
            if (key.equals("pin")) {
                if (null == accNo || "".equals(accNo.trim())) {
                    throw new RuntimeException("加密PIN没送卡号无法后续处理");
                } else {
                    value = encryptPin(accNo, value, encoding);
                }
            }
            sf.append(key).append(SDKConstants.EQUAL).append(value);
            if (it.hasNext()) {
                sf.append(SDKConstants.AMPERSAND);
            }
        }
        String customerInfo = sf.append("}").toString();
        try {
            return new String(SecureUtil.base64Encode(sf.toString().getBytes(encoding)), encoding);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("不支持的编码格式！{}", e);
        } catch (IOException e) {
            LOGGER.error("加密失败！{}", e);
        }
        return customerInfo;
    }
    
    /**
     * Description: 持卡人信息域customerInfo构造，勾选对敏感信息加密权限 适用新加密规范，对pin和phoneNo，cvn2，expired加密 <br>
     * 适用到的交易： <br>
     * @param customerInfoMap 信息域请求参数 key送域名value送值,必送 <br>
     *        例如：customerInfoMap.put("certifTp", "01");                 //证件类型 <br>
                  customerInfoMap.put("certifId", "341126197709218366");    //证件号码 <br>
                  customerInfoMap.put("customerNm", "互联网");             //姓名 <br>
                  customerInfoMap.put("smsCode", "123456");                 //短信验证码 <br>
                  customerInfoMap.put("pin", "111111");                     //密码（加密） <br>
                  customerInfoMap.put("phoneNo", "13552535506");            //手机号（加密） <br>
                  customerInfoMap.put("cvn2", "123");                       //卡背面的cvn2三位数字（加密） <br>
                  customerInfoMap.put("expired", "1711");                   //有效期 年在前月在后（加密） <br>
     * @param accNo  customerInfoMap送了密码那么卡号必送,如果customerInfoMap未送密码PIN，此字段可以不送<br>
     * @param encoding 上送请求报文域encoding字段的值
     * @return base64后的持卡人信息域字段 <br>
     */
    public static String getCustomerInfoWithEncrypt(Map<String,String> customerInfoMap,String accNo,String encoding) {
        if(customerInfoMap.isEmpty()) {
            return "{}";
        }
        StringBuffer sf = new StringBuffer("{");
        //敏感信息加密域
        StringBuffer encryptedInfoSb = new StringBuffer("");
        for (Iterator<String> it = customerInfoMap.keySet().iterator(); it.hasNext();) {
            String key = it.next();
            String value = customerInfoMap.get(key);
            if (key.equals("phoneNo") || key.equals("cvn2") || key.equals("expired")) {
                encryptedInfoSb.append(key).append(SDKConstants.EQUAL).append(value).append(SDKConstants.AMPERSAND);
            } else {
                if (key.equals("pin")) {
                    if (null == accNo || "".equals(accNo.trim())) {
                        LOGGER.error("送了密码（PIN），必须在getCustomerInfoWithEncrypt参数中上传卡号");
                        throw new RuntimeException("加密PIN没送卡号无法后续处理");
                    } else {
                        value = encryptPin(accNo, value, encoding);
                    }
                }
                sf.append(key).append(SDKConstants.EQUAL).append(value).append(SDKConstants.AMPERSAND);
            }
        }
        
        if (!encryptedInfoSb.toString().equals("")) {
            encryptedInfoSb.setLength(encryptedInfoSb.length() - 1);// 去掉最后一个&符号
            sf.append("encryptedInfo").append(SDKConstants.EQUAL).append(encryptData(encryptedInfoSb.toString(), encoding));
        } else {
            sf.setLength(sf.length() - 1);
        }
        
        String customerInfo = sf.append("}").toString();
        try {
            return new String(SecureUtil.base64Encode(sf.toString().getBytes(encoding)), encoding);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("不支持的编码格式！{}", e);
        } catch (IOException e) {
            LOGGER.error("加密失败！{}", e);
        }
        return customerInfo;
    }
    
    /**
     * Description: 解析返回报文（后台通知）中的customerInfo域：解base64,如果带敏感信息加密 encryptedInfo 则将其解密并将 encryptedInfo中的域放到customerInfoMap返回
     * @param customerInfo
     * @param encoding
     * @return
     */
    public static Map<String,String> parseCustomerInfo(String customerInfo,String encoding){
        Map<String,String> customerInfoMap = null;
        try {
            byte[] b = SecureUtil.base64Decode(customerInfo.getBytes(encoding));
            String customerInfoNoBase64 = new String(b, encoding);
            // 去掉前后的{}
            customerInfoNoBase64 = customerInfoNoBase64.substring(1, customerInfoNoBase64.length() - 1);
            customerInfoMap = parseQString(customerInfoNoBase64);
            if (customerInfoMap.containsKey("encryptedInfo")) {
                String encInfoStr = customerInfoMap.get("encryptedInfo");
                customerInfoMap.remove("encryptedInfo");
                String encryptedInfoStr = decryptData(encInfoStr, encoding);
                Map<String, String> encryptedInfoMap = parseQString(encryptedInfoStr);
                customerInfoMap.putAll(encryptedInfoMap);
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("不支持的编码格式！{}", e);
        } catch (IOException e) {
            LOGGER.error("加密失败！{}", e);
        }
        return customerInfoMap;
    }
    
    /**
     * Description: 将形如key=value&key=value的字符串转换为相应的Map对象
     * 
     * @param result
     * @return
     */
    public static Map<String, String> convertResultStringToMap(String result) {
        Map<String, String> map =null;
        try {
            
            if(StringUtils.isNotBlank(result)){
                if(result.startsWith("{") && result.endsWith("}")){
                    System.out.println(result.length());
                    result = result.substring(1, result.length()-1);
                }
                 map = parseQString(result);
            }
            
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("不支持的编码格式！{}", e);
        }
        return map;
    }
    
    /**
     * Description: 解析应答字符串，生成应答要素
     * 
     * @param str
     *            需要解析的字符串
     * @return 解析的结果map
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String> parseQString(String str) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<String, String>();
        int len = str.length();
        if (0 < len) {
            char curChar;
            char openName = 0;
            boolean isKey = true;
            // 值里有嵌套
            boolean isOpen = false;
            String key = null;
            StringBuilder temp = new StringBuilder();
            for (int i = 0; i < len; i++) {
                curChar = str.charAt(i);
                if (isKey) {
                    // 如果当前生成的是key
                    if (curChar == '=') {
                        key = temp.toString();
                        temp.setLength(0);
                        isKey = false;
                    } else {
                        temp.append(curChar);
                    }
                } else {
                    // 如果当前生成的是value
                    if (isOpen) {
                        if (curChar == openName) {
                            isOpen = false;
                        }
                    } else {
                        // 如果没开启嵌套
                        if (curChar == '{') {
                            isOpen = true;
                            openName = '}';
                        }
                        if (curChar == '[') {
                            isOpen = true;
                            openName = ']';
                        }
                    }
                    if (curChar == '&' && !isOpen) {
                        // 如果读取到&分割符,同时这个分割符不是值域，这时将map里添加
                        putKeyValueToMap(temp, isKey, key, map);
                        temp.setLength(0);
                        isKey = true;
                    } else {
                        temp.append(curChar);
                    }
                }
            }
            putKeyValueToMap(temp, isKey, key, map);
        }
        return map;
    }

    private static void putKeyValueToMap(StringBuilder temp, boolean isKey, String key, Map<String, String> map)
            throws UnsupportedEncodingException {
        if (isKey) {
            key = temp.toString();
            if (key.length() == 0) {
                throw new RuntimeException("QString format illegal");
            }
            map.put(key, "");
        } else {
            if (key.length() == 0) {
                throw new RuntimeException("QString format illegal");
            }
            map.put(key, temp.toString());
        }
    }

    /**
     * Description: 密码加密并做base64<br>
     * @param accNo 卡号<br>
     * @param pwd 密码<br>
     * @param encoding<br>
     * @return 加密的内容<br>
     */
    public static String encryptPin(String accNo, String pwd, String encoding) {
        return SecureUtil.EncryptPin(pwd, accNo, encoding, CertUtil.getEncryptCertPublicKey());
    }
    
    /**
     * Description: 敏感信息加密并做base64(卡号，手机号，cvn2,有效期）<br>
     * @param data 送 phoneNo,cvn2,有效期<br>
     * @param encoding<br>
     * @return 加密的密文<br>
     */
    public static String encryptData(String data, String encoding) {
        return SecureUtil.EncryptData(data, encoding, CertUtil.getEncryptCertPublicKey());
    }
    
    /**
     * Description: 敏感信息解密
     * @param base64EncryptedInfo
     * @param encoding
     * @return 解密后的明文
     */
    public static String decryptData(String base64EncryptedInfo, String encoding) {
        return SecureUtil.DecryptedData(base64EncryptedInfo, encoding, CertUtil
                .getSignCertPrivateKey());
    }
    
    /**
     * Description: 获取敏感信息加密证书的物理序列号<br>
     * @return
     */
    public static String getEncryptCertId(){
        return CertUtil.getEncryptCertId();
    }
    
    /**
     * Description: 对字符串做base64
     * @param rawStr
     * @param encoding
     * @return
     * @throws IOException
     */
    public static String base64Encode(String rawStr,String encoding) throws IOException{
        byte [] rawByte = rawStr.getBytes(encoding);
        return new String(SecureUtil.base64Encode(rawByte),encoding);
    }
    /**
     * Description: 对base64的字符串解base64
     * @param base64Str
     * @param encoding
     * @return
     * @throws IOException
     */
    public static String base64Decode(String base64Str,String encoding) throws IOException{
        byte [] rawByte = base64Str.getBytes(encoding);
        return new String(SecureUtil.base64Decode(rawByte),encoding);   
    }

    
}
