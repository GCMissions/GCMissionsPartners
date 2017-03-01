/*
 * Project Name: wrw-admin
 * File Name: OrderReturnDto.java
 * Class Name: OrderReturnDto
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
* Class Name: OrderReturnDto
* Description: 
* @author  
*
*/
public class OrderReturnDto {
    // 订单编号
    private String orderId;

    // 订单金额
    private String totalAmount;
 
    private String actualAmount;
     
    private String couponAmount; 
    
    private String shipAmount;

    // 退款
    private String returnOrderAmount;

    // 联系方式
    private String phone;

    // 订单时间
    private String createDate;

    // 订单状态
    private String status;
 
    private String remark;
  
    private String returnType;
    
    // 是否可以退款（0-不可退，1-可退）
    private String returnable;
    
    // 发生退款次数
    private Integer returnTimes;
    
    // 订单来源（OrderSourceEnum）
    private String source; 
    
    private String operType;
    
    private Long productId;
    
    private String orderType;

    private Integer returnCount;
    
    private String specInfo;
    
    private String buyVip;
    
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getReturnOrderAmount() {
        return returnOrderAmount;
    }

    public void setReturnOrderAmount(String returnOrderAmount) {
        this.returnOrderAmount = returnOrderAmount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(String actualAmount) {
        this.actualAmount = actualAmount;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReturnable() {
        return returnable;
    }

    public void setReturnable(String returnable) {
        this.returnable = returnable;
    }

    public Integer getReturnTimes() {
        return returnTimes;
    }

    public void setReturnTimes(Integer returnTimes) {
        this.returnTimes = returnTimes;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(Integer returnCount) {
        this.returnCount = returnCount;
    }

    public String getSpecInfo() {
        return specInfo;
    }

    public void setSpecInfo(String specInfo) {
        this.specInfo = specInfo;
    }

    public String getBuyVip() {
        return buyVip;
    }

    public void setBuyVip(String buyVip) {
        this.buyVip = buyVip;
    }

}
