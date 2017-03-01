/*
 * Project Name: wrw-admin
 * File Name: KPStockDto.java
 * Class Name: KPStockDto
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

import java.util.List;

/**
 * Class Name: KPStockDto
 * Description: 酷袋商品库存
 * @author zhongyidong
 *
 */
public class KdProductStockDto {

    private Long id;
    
    private Long productId;
    
    private String stockType;
    
    private Integer originAmount;
    
    private Integer restAmount;
    
    private Integer limitAmount;
    
    /**
     * 规格信息
     */
    private String specInfo;
    
    /**
     * 价格列表
     */
    private List<Integer> priceList;
    
    /**
     * VIP价格列表
     */
    private List<Integer> vipPriceList;
    
    /**
     * 库存详情
     */
    private List<KdProductStockDetailDto> stockDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
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

    public String getSpecInfo() {
        return specInfo;
    }

    public void setSpecInfo(String specInfo) {
        this.specInfo = specInfo;
    }

    public List<Integer> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<Integer> priceList) {
        this.priceList = priceList;
    }

    public List<Integer> getVipPriceList() {
        return vipPriceList;
    }

    public void setVipPriceList(List<Integer> vipPriceList) {
        this.vipPriceList = vipPriceList;
    }

    public List<KdProductStockDetailDto> getStockDetails() {
        return stockDetails;
    }

    public void setStockDetails(List<KdProductStockDetailDto> stockDetails) {
        this.stockDetails = stockDetails;
    }
    
}
