/*
 * Project Name: wrw-admin
 * File Name: ConfirmReturnAmountDetailDto.java
 * Class Name: ConfirmReturnAmountDetailDto
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
package com.hengtiansoft.business.order.dto;

/**
* Class Name: ConfirmReturnAmountDetailDto
* Description: 
* @author xianghuang
*
*/
public class ConfirmReturnAmountDetailDto {
    
    //订单号
    private String orderId;
    
    //支付方式
    private String paymentType;
    
    //账号
    private String accountNo;
    
    //订单金额
    private String orderAmount;
    
    //优惠券金额
    private String couponAmount;
    
    //运费
    private String shipAmount;
    
    //退款详情
    private String returnPoint;
    
    //订单退款金额
    private String orderReturnAmount;
    
    //优惠券退款
    private String couponReturnAmount;
    
    //运费退款
    private String shipReturnAmount;
    
    //退款总额
    private String totalAmount;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String getShipAmount() {
        return shipAmount;
    }

    public void setShipAmount(String shipAmount) {
        this.shipAmount = shipAmount;
    }

    public String getReturnPoint() {
        return returnPoint;
    }

    public void setReturnPoint(String returnPoint) {
        this.returnPoint = returnPoint;
    }

    public String getOrderReturnAmount() {
        return orderReturnAmount;
    }

    public void setOrderReturnAmount(String orderReturnAmount) {
        this.orderReturnAmount = orderReturnAmount;
    }

    public String getCouponReturnAmount() {
        return couponReturnAmount;
    }

    public void setCouponReturnAmount(String couponReturnAmount) {
        this.couponReturnAmount = couponReturnAmount;
    }

    public String getShipReturnAmount() {
        return shipReturnAmount;
    }

    public void setShipReturnAmount(String shipReturnAmount) {
        this.shipReturnAmount = shipReturnAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    

}
