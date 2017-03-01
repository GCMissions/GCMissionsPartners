package com.hengtiansoft.business.order.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Class Name: OrderManagerListDto Description: 订单列表数据DTO
 * 
 * @author jiafengchen
 *
 */
public class OrderManagerListDto implements Serializable{

    /**
     * Variables Name: serialVersionUID
     * Description: TODO
     * Value Description: TODO
     */
     private static final long serialVersionUID = 1L;

     private String orderId;
     
     private Date createDate;
     
     private String phone;
     
     private String productName;
     
     private String orgName;
     
     private String productType;
     
     private String amount;
     
     private String status;
     
     private String productCode;
     
     // 是否可以退款（0-不可退款， 1-可退款）
     private String returnable;
     
     private String buyVip;
    
     public String getOrderId() {
         return orderId;
     }

     public void setOrderId(String orderId) {
         this.orderId = orderId;
     }

     public Date getCreateDate() {
         return createDate;
     }

     public void setCreateDate(Date createDate) {
         this.createDate = createDate;
     }

     public String getPhone() {
         return phone;
     }

     public void setPhone(String phone) {
         this.phone = phone;
     }

     public String getProductName() {
         return productName;
     }

     public void setProductName(String productName) {
         this.productName = productName;
     }

     public String getOrgName() {
         return orgName;
     }

     public void setOrgName(String orgName) {
         this.orgName = orgName;
     }

     public String getProductType() {
         return productType;
     }

     public void setProductType(String productType) {
         this.productType = productType;
     }

     public String getAmount() {
         return amount;
     }

     public void setAmount(String amount) {
         this.amount = amount;
     }

     public String getStatus() {
         return status;
     }

     public void setStatus(String status) {
         this.status = status;
     }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getReturnable() {
        return returnable;
    }

    public void setReturnable(String returnable) {
        this.returnable = returnable;
    }

    public String getBuyVip() {
        return buyVip;
    }

    public void setBuyVip(String buyVip) {
        this.buyVip = buyVip;
    }

}
