/*
 * Project Name: wrw-admin
 * File Name: KPShiefDto.java
 * Class Name: KPShiefDto
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

/**
 * Class Name: KPShiefDto
 * Description: 酷袋商品运费前端交互dto
 * @author zhongyidong
 *
 */
public class KdProductFreightDto {

    private Long id;
    
    /**
     * 商品id
     */
    private Long producId;

    private Integer price;

    /**
     * 区域id
     */
    private Long regionId;
    
    /**
     * 区域名称
     */
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
