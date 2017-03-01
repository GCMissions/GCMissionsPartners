package com.hengtiansoft.business.product.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
* Class Name: ProductDeliveryShiefDto
* Description: 商品上下架DTO
* @author zhisongliu
*
 */
public class ProductDeliveryShiefDto  implements Serializable{

	private static final long serialVersionUID = 3998405248468286573L;
	
	private Long productId;
	
	private String startDate;
	
    private String endDate;
	
	private String shiefStatus;
	
	private List<ProductDeliveryShiefDetailDto> list;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public List<ProductDeliveryShiefDetailDto> getList() {
		return list;
	}

	public void setList(List<ProductDeliveryShiefDetailDto> list) {
		this.list = list;
	}

	public String getShiefStatus() {
		return shiefStatus;
	}

	public void setShiefStatus(String shiefStatus) {
		this.shiefStatus = shiefStatus;
	}

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
	
	
}
