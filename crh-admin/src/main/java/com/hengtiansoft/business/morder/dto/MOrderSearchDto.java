package com.hengtiansoft.business.morder.dto;

import com.hengtiansoft.common.dto.PagingDto;
/**
 * 
 * Class Name: MOrderSearchDto Description: 订单查询DTO
 * 
 * @author jiafengchen
 *
 */
public class MOrderSearchDto extends PagingDto<MOrderDto>{
    
    /**
    * Variables Name: serialVersionUID
    * Description: TODO
    * Value Description: TODO
    */
    private static final long serialVersionUID = 1L;

    private String createDate;
    
    private String orderId;
    
    private String  phone;
    
    private String productName;
    
    private String typeId;
    
    private String orgId;
    
    private String status;

    

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    

}
