package com.hengtiansoft.common.util.pay;

import com.hengtiansoft.common.util.AppConfigUtil;

/**
 * Quick payment configuration
 * 
 * @author taochen
 *
 */
public class QuickPayConfig {
    // version
    private static String version     = AppConfigUtil.getConfig("quickpay.pay.version");

    // trasaction code
    private static String transCode   = AppConfigUtil.getConfig("quickpay.pay.transcode");

    // merchant code
    private static String merCode     = AppConfigUtil.getConfig("quickpay.pay.mercode");

    // channel number
    private static String chainNo     = AppConfigUtil.getConfig("quickpay.pay.chainno");

    // request Url
    private static String requestUrl  = AppConfigUtil.getConfig("quickpay.pay.requesturl");

    // certificate path
    private static String pfxPath     = AppConfigUtil.getConfig("quickpay.pay.pfxpath");

    // certificate password
    private static String pfxPassword = AppConfigUtil.getConfig("quickpay.pay.pfxpassword");

    public static String getVersion() {
        return version;
    }

    public static void setVersion(String version) {
        QuickPayConfig.version = version;
    }

    public static String getTransCode() {
        return transCode;
    }

    public static void setTransCode(String transCode) {
        QuickPayConfig.transCode = transCode;
    }

    public static String getMerCode() {
        return merCode;
    }

    public static void setMerCode(String merCode) {
        QuickPayConfig.merCode = merCode;
    }

    public static String getChainNo() {
        return chainNo;
    }

    public static void setChainNo(String chainNo) {
        QuickPayConfig.chainNo = chainNo;
    }

    public static String getRequestUrl() {
        return requestUrl;
    }

    public static void setRequestUrl(String requestUrl) {
        QuickPayConfig.requestUrl = requestUrl;
    }

    public static String getPfxPath() {
        return pfxPath;
    }

    public static void setPfxPath(String pfxPath) {
        QuickPayConfig.pfxPath = pfxPath;
    }

    public static String getPfxPassword() {
        return pfxPassword;
    }

    public static void setPfxPassword(String pfxPassword) {
        QuickPayConfig.pfxPassword = pfxPassword;
    }

}
