package com.hengtiansoft.business.service;

public interface OrderPaymentService {
    
    /**
     * Description: 支付宝支付确认
     *
     * @param orderId
     * @param tsn 交易流水号
     * @param amount
     * @return
     */
    boolean aliPayPaidConfirm(String orderId, String tsn, Long amount);
    
    /**
     * Description: 微信支付确认
     *
     * @param orderId
     * @param tsn 交易流水号
     * @param amount
     * @return
     */
    boolean wechatPaidConfirm(String orderId, String tsn, Long amount);
    
    /**
     * 
    * Description: 连亿家支付完成
    *
    * @param orderId
    * @param tsn
    * @param amount
    * @return
     */
    public boolean lianyijiaPaidConfirm(String orderId, String tsn, Long amount);
    
    /**
     * 
     * Description: 根据订单id和终端配送商id 减库存
     *
     * @param orderId
     * @param orgId
     */
    void decStock(String orderId, Long orgId);
    
   /**
    * 
   * Description: 兴业银行支付确认
   *
   * @param orderId
   * @param tsn
   * @param amount
   * @return
    */
   boolean ibPayPaidConfirm(String orderId, String tsn, Long amount);
}
