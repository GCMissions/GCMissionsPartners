/*
 * Project Name: wrw-common
 * File Name: KPStockEntity.java
 * Class Name: KPStockEntity
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
package com.hengtiansoft.wrw.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hengtiansoft.common.domain.BaseEntity;

/**
 * Class Name: KPStockEntity
 * Description: 酷袋商品库存
 * @author zhongyidong
 *
 */
@Entity
@Table(name = "kd_p_stock")
public class KdProductStockEntity extends BaseEntity{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "PRODUCT_ID")
    private Long productId;
    
    /**
     * 库存类型
     */
    @Column(name = "STOCK_TYPE")
    private String stockType;
    
    /**
     * 原有库存
     */
    @Column(name = "ORIGIN_AMOUNT")
    private Integer originAmount;
    
    /**
     * 剩余库存
     */
    @Column(name = "REST_AMOUNT")
    private Integer restAmount;
    
    /**
     * 限购数量
     */
    @Column(name = "LIMIT_AMOUNT")
    private Integer limitAmount;
    
    /**
     * 规格信息
     */
    @Column(name = "SPEC_INFO")
    private String specInfo;

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

}
