package com.hengtiansoft.business.service;

public interface RechargePaymentService {

    boolean rechargeConfirm(String paymentType, String orderId, String tsn, Long amount);
}
