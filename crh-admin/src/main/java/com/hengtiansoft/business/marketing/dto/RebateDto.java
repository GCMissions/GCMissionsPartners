package com.hengtiansoft.business.marketing.dto;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hengtiansoft.common.util.CustomDoubleSerialize;

public class RebateDto implements Serializable {

    private static final long serialVersionUID = 3913277761863318761L;

    private Long              productId;

    private Integer           cityId;

    private String            cityName;

    private Double            pRebate;

    private Double            zRebate;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @JsonSerialize(using = CustomDoubleSerialize.class)
    public Double getpRebate() {
        return pRebate;
    }

    public void setpRebate(Double pRebate) {
        this.pRebate = pRebate;
    }

    @JsonSerialize(using = CustomDoubleSerialize.class)
    public Double getzRebate() {
        return zRebate;
    }

    public void setzRebate(Double zRebate) {
        this.zRebate = zRebate;
    }

}
