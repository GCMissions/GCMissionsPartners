/*
 * Project Name: wrw-admin
 * File Name: FreePostageDto.java
 * Class Name: FreePostageDto
 * Copyright 2014 Hengtian Software Inc
 * Licensed under the Hengtiansoft
 * http://www.hengtiansoft.com
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.business.marketing.dto;

import java.io.Serializable;

/**
 * Class Name: FreePostageDto
 * Description: TODO
 * 
 * @author chengminmiao
 */
public class FreePostageDto implements Serializable {

    private static final long serialVersionUID = 6568220980504831011L;

    private Long              productId;

    private String            brandName;

    private String            cateName;

    private String            productCode;

    private String            productName;

    private String            shipfeeConfig;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShipfeeConfig() {
        return shipfeeConfig;
    }

    public void setShipfeeConfig(String shipfeeConfig) {
        this.shipfeeConfig = shipfeeConfig;
    }

}
