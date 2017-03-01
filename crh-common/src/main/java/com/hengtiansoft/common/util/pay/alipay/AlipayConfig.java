package com.hengtiansoft.common.util.pay.alipay;

import com.hengtiansoft.common.util.AppConfigUtil;

public class AlipayConfig {

    // 接口名称
    public static String service           = "mobile.securitypay.pay";

    // 付款方式
    public static String paymentType       = "1";

    // 订单支付回调地址
    public static String payNotifyUrl      = AppConfigUtil.getConfig("alipay.pay.notify_url");

    // 充值支付回调地址
    public static String rechargeNotifyUrl = AppConfigUtil.getConfig("alipay.recharge.notify_url");

    // 合作者身份ID
    public static String partner           = AppConfigUtil.getConfig("alipay.partner");

    // 商户的私钥
    public static String privateKey        = AppConfigUtil.getConfig("alipay.private_key");

    // 支付宝的公钥
    public static String publicKey         = AppConfigUtil.getConfig("alipay.public_key");

    // 字符编码方式
    public static String charset           = AppConfigUtil.getConfig("alipay.input_charset");

    // 签名方式
    public static String signType          = AppConfigUtil.getConfig("alipay.sign_type");

    // 商户支付宝账号
    public static String sellerId          = AppConfigUtil.getConfig("alipay.seller_id");

    // 支付验证地址
    public static String gateway           = AppConfigUtil.getConfig("alipay.gateway_url");

    // 支付宝支付页面显示内容
    public static String showUrl           = AppConfigUtil.getConfig("alipay.pay.show_url");

    // 支付宝跳回来显示的页面
    public static String returnUrl         = AppConfigUtil.getConfig("alipay.pay.return_url");

    // 支付宝跳回来显示的页面
    public static String rechargeReturnUrl = AppConfigUtil.getConfig("alipay.recharge.return_url");

    // 交易超时时间
    public static String timeout           = AppConfigUtil.getConfig("alipay.pay.timeout");

    // 支付宝支付页面显示内容
    public static String rechargeShowUrl   = AppConfigUtil.getConfig("alipay.recharge.show_url");

    // 支付宝app_id
    public static String appId             = AppConfigUtil.getConfig("alipay.pay.app_id");
    
    // 扫码支付的url
    public static String openGateway       = AppConfigUtil.getConfig("alipay.pay.open_gateway");
}
