/*
 * Project Name: wrw-common
 * File Name: KPStockDetailEntity.java
 * Class Name: KPStockDetailEntity
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
 * Class Name: KPStockDetailEntity
 * Description: 酷袋商品库存详情
 * @author zhongyidong
 *
 */
@Entity
@Table(name = "kd_p_stock_detail")
public class KdProductStockDetailEntity extends BaseEntity{

    private static final long serialVersionUID = -6704375751436339184L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "STOCK_ID")
    private Long stockId;
    
    /**
     * 子规格组合
     */
    @Column(name = "SPEC_GROUP")
    private String specGroup;
    
    @Column(name = "PRICE")
    private Long price;
    
    @Column(name = "VIP_PRICE")
    private Long vipPrice;
    
    /**
     * 原有库存量
     */
    @Column(name = "ORIGIN_AMOUNT")
    private Integer originAmount;
    
    /**
     * 剩余库存量
     */
    @Column(name = "REST_AMOUNT")
    private Integer restAmount;
    
    /**
     * 限购数量
     */
    @Column(name = "LIMIT_AMOUNT")
    private Integer limitAmount;
    
    /**
     * 是否删除。0-未删除，1-已删除
     */
    @Column(name = "IS_DELETED")
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
    }

    public Long getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(Long vipPrice) {
        this.vipPrice = vipPrice;
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
