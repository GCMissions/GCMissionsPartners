/*
 * Project Name: wrw-common
 * File Name: KdProductShiefEntity.java
 * Class Name: KdProductShiefEntity
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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hengtiansoft.common.domain.BaseEntity;


/**
 * Class Name: KdProductSaleEntity
 * Description: 酷袋商品上下架
 * @author zhongyidong
 *
 */
@Entity
@Table(name = "kd_p_sale")
public class KdProductSaleEntity extends BaseEntity{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "PRODUCT_ID")
    private Long productId;
    
    /**
     * 上下架状态
     */
    @Column(name = "SALE_STATUS")
    private String saleStatus;

    /**
     * 上架时间
     */
    @Column(name = "ON_SALE_TIME")
    private Date onTime;
    
    /**
     * 下架时间
     */
    @Column(name = "OFF_SALE_TIME")
    private Date offTime;
    
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

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    public Date getOnTime() {
        return onTime;
    }

    public void setOnTime(Date onTime) {
        this.onTime = onTime;
    }

    public Date getOffTime() {
        return offTime;
    }

    public void setOffTime(Date offTime) {
        this.offTime = offTime;
    }

}
