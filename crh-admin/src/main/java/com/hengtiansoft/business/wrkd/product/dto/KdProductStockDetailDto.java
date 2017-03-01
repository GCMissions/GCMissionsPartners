/*
 * Project Name: wrw-admin
 * File Name: KPStockDetailDto.java
 * Class Name: KPStockDetailDto
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
package com.hengtiansoft.business.wrkd.product.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hengtiansoft.common.util.CustomDoubleSerialize;
import com.hengtiansoft.common.util.WRWUtil;

/**
 * Class Name: KPStockDetailDto
 * Description: 酷袋商品库存详情
 * @author zhongyidong
 *
 */
public class KdProductStockDetailDto {
    
    private Long id;
    
    private Long stockId;
    
    private String specGroup;
    
    private Long price;
    
    private BigDecimal priceYuan;
    
    private Long vipPrice;
    
    /**
     * 上一次设置的库存量
     */
    private Integer originAmount;
    
    /**
     * 剩余库存量
     */
    private Integer restAmount;
    
    /**
     * 限购数量
     */
    private Integer limitAmount;
    
    private String isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public String getSpecGroup() {
        return specGroup;
    }

    public void setSpecGroup(String specGroup) {
        this.specGroup = specGroup;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
        this.priceYuan = WRWUtil.transFenToYuan(price);
    }

    @JsonSerialize(using = CustomDoubleSerialize.class)
    public BigDecimal getPriceYuan() {
        return priceYuan;
    }

    public Long getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(Long vipPrice) {
        this.vipPrice = vipPrice;
    }

    public void setPriceYuan(BigDecimal priceYuan) {
        this.priceYuan = priceYuan;
        this.price = WRWUtil.transYuanToFen(priceYuan);
    }

    public Integer getOriginAmount() {
        return originAmount;
    }

    public void setOriginAmount(Integer originAmount) {
        this.originAmount = originAmount;
    }

    public Integer getRestAmount() {
        return restAmount;
    }

    public void setRestAmount(Integer restAmount) {
        this.restAmount = restAmount;
    }

    public Integer getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(Integer limitAmount) {
        this.limitAmount = limitAmount;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }
    
}
