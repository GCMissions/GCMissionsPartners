package com.hengtiansoft.common.util.pay;

import com.hengtiansoft.common.util.AppConfigUtil;

/**
 * 快捷支付（市民卡支付）配置
 * 
 * @author yigesong
 *
 */
public class QuickPayConfig {
    // 版本号
    private static String version     = AppConfigUtil.getConfig("quickpay.pay.version");

    // 交易代码
    private static String transCode   = AppConfigUtil.getConfig("quickpay.pay.transcode");

    // 商户号
    private static String merCode     = AppConfigUtil.getConfig("quickpay.pay.mercode");

    // 渠道号
    private static String chainNo     = AppConfigUtil.getConfig("quickpay.pay.chainno");

    // 请求地址
    private static String requestUrl  = AppConfigUtil.getConfig("quickpay.pay.requesturl");

    // 证书地址
    private static String pfxPath     = AppConfigUtil.getConfig("quickpay.pay.pfxpath");

    // 证书密码
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
