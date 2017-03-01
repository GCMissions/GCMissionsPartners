package com.hengtiansoft.business.order.dto;

import com.hengtiansoft.common.dto.PagingDto;

/**
 * 
 * Class Name: OrderManagerListSearchDto 
 * Description: 订单查询DTO
 * 
 * @author jiafengchen
 *
 */
public class OrderManagerListSearchDto extends PagingDto<OrderManagerListDto>{

    private static final long serialVersionUID = 1L;

    private String            startTime;

    private String            endDate;

    private String            orderId;

    private String            phone;

    private String            productName;

    private String            typeId;

    private String            orgId;

    private String            status;
    
    private String            vip;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

}
