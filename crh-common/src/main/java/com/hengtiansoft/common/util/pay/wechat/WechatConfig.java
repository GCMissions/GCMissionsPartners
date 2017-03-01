
package com.hengtiansoft.common.util.pay.wechat;

import com.hengtiansoft.common.util.AppConfigUtil;

/**
 * Discription：测试暂用
 * 
 * @author huizhuang
 */
public class WechatConfig {

    // 扩展字段
    public static String packageValue      = "Sign=WXPay";

    // 支付类型
    public static String tradeType         = AppConfigUtil.getConfig("wechat.trad_type");

    // 统一下单地址
    public static String unifiedorderUrl   = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    // API 密钥
    public static String apiKey            = AppConfigUtil.getConfig("wechat.apikey");

    // APP ID
    public static String appid             = AppConfigUtil.getConfig("wechat.appid");

    // 商户号
    public static String mchid             = AppConfigUtil.getConfig("wechat.mchid");

    // 订单回调地址
    public static String payNotifyUrl      = AppConfigUtil.getConfig("wechat.pay.notify_url");

    // 充值支付回调地址
    public static String rechargeNotifyUrl = AppConfigUtil.getConfig("wechat.recharge.notify_url");
}
