package com.hengtiansoft.common.util.pay.wechat;

import com.hengtiansoft.common.util.AppConfigUtil;

/**
 * Discription：test use
 * 
 * @author taochen
 */
public class WechatConfig {

    // Extended field
    public static String packageValue = "Sign=WXPay";

    // Payment Types
    public static String tradeType = AppConfigUtil.getConfig("wechat.trad_type");

    // unified order url
    public static String unifiedorderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    // API key
    public static String apiKey = AppConfigUtil.getConfig("wechat.apikey");

    // APP ID
    public static String appid = AppConfigUtil.getConfig("wechat.appid");

    // Merchant number
    public static String mchid = AppConfigUtil.getConfig("wechat.mchid");

    // Order callback address
    public static String payNotifyUrl = AppConfigUtil.getConfig("wechat.pay.notify_url");

    // Recharge callback address
    public static String rechargeNotifyUrl = AppConfigUtil.getConfig("wechat.recharge.notify_url");
}
