package com.hengtiansoft.business.shopStock.zStock.dto;

import java.util.List;

public class ShipmentDto {

    private String          id;

    private String          orderCode;

    private String          totalNum;

    private String          shipmentDate;

    private String          status;

    List<ShipmentDetailDto> shipmentDetailDtos;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(String shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ShipmentDetailDto> getShipmentDetailDtos() {
        return shipmentDetailDtos;
    }

    public void setShipmentDetailDtos(List<ShipmentDetailDto> shipmentDetailDtos) {
        this.shipmentDetailDtos = shipmentDetailDtos;
    }

}
