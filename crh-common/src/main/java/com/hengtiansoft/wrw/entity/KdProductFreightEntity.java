/*
 * Project Name: wrw-common
 * File Name: KPShiefEntity.java
 * Class Name: KPShiefEntity
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
 * Class Name: KPShiefEntity
 * Description: 商品运费
 * @author zhongyidong
 *
 */
@Entity
@Table(name = "kd_p_freight")
public class KdProductFreightEntity extends BaseEntity{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    /**
     * 商品id
     */
    @Column(name = "PRODUCT_ID")
    private Long producId;

    @Column(name = "PRICE")
    private Integer price;

    /**
     * 区域id
     */
    @Column(name = "REGION_ID")
    private Long regionId;

    /**
     * 区域名称
     */
    @Column(name = "REGION_NAME")
    private String regionName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProducId() {
        return producId;
    }

    public void setProducId(Long producId) {
        this.producId = producId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

}
