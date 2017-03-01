package com.hengtiansoft.business.pay.service;

public interface LlpayRefundService {
    /**
    * Description: 连连支付退款方法
    *
    * @param refundFee 退款金额（不能高于支付金额）
    * @param refundOrderId （退款订单号，不是支付订单号，需要有所区别）
    * @param tsn （支付返回的流水号，m_order_main里的tsn字段）
    * @return "SUCCESS"表示成功，null及其他为失败
    */
    String llpayRefund( String refundFee, String refundOrderId, String tsn);
}
