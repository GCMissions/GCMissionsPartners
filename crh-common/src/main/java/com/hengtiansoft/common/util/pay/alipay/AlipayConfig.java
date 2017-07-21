package com.hengtiansoft.common.util.pay.alipay;

import com.hengtiansoft.common.util.AppConfigUtil;

public class AlipayConfig {

    // Interface name
    public static String service = "mobile.securitypay.pay";

    // Payment Type
    public static String paymentType = "1";

    // Order payment callback address
    public static String payNotifyUrl = AppConfigUtil.getConfig("alipay.pay.notify_url");

    // Recharge payment callback address
    public static String rechargeNotifyUrl = AppConfigUtil.getConfig("alipay.recharge.notify_url");

    // partner ID
    public static String partner = AppConfigUtil.getConfig("alipay.partner");

    // The private key of the merchant
    public static String privateKey = AppConfigUtil.getConfig("alipay.private_key");

    // Alipay's public key
    public static String publicKey = AppConfigUtil.getConfig("alipay.public_key");

    // Character encoding
    public static String charset = AppConfigUtil.getConfig("alipay.input_charset");

    // Signature type
    public static String signType = AppConfigUtil.getConfig("alipay.sign_type");

    // Business Alipay account
    public static String sellerId = AppConfigUtil.getConfig("alipay.seller_id");

    // Make payment verification url
    public static String gateway = AppConfigUtil.getConfig("alipay.gateway_url");

    // Alipay pay page display content
    public static String showUrl = AppConfigUtil.getConfig("alipay.pay.show_url");

    // Alipay jumps back to the displayed page
    public static String returnUrl = AppConfigUtil.getConfig("alipay.pay.return_url");

    // Alipay jumps back to the displayed page
    public static String rechargeReturnUrl = AppConfigUtil.getConfig("alipay.recharge.return_url");

    // Transaction timeout
    public static String timeout = AppConfigUtil.getConfig("alipay.pay.timeout");

    // Alipay pay page display content
    public static String rechargeShowUrl = AppConfigUtil.getConfig("alipay.recharge.show_url");

    // Alipay app_id
    public static String appId = AppConfigUtil.getConfig("alipay.pay.app_id");

    // Scan code to pay url
    public static String openGateway = AppConfigUtil.getConfig("alipay.pay.open_gateway");
}
