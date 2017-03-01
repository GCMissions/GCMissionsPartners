/*
 * Project Name: wrw-admin
 * File Name: QueryProductRespDto.java
 * Class Name: QueryProductRespDto
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
package com.hengtiansoft.business.offline.dto;

import java.io.Serializable;

/**
 * Class Name: QueryProductRespDto
 * Description: 
 * @author xiaoluzhou
 *
 */
public class OfflineQueryProductRespDto implements Serializable{

    private static final long serialVersionUID = -4911021353499949083L;
    
    private Long productId;
    
    private String productName;
    
    private String productCode;
    
    private Double salePrice;
    
    private String unit;
    
    private String barcode;
    
    private String prefixCode;
    
    private String hintMsg;
    
    private boolean isAvailable = true;
    
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductCode() {
        return productCode;
    }
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    public Double getSalePrice() {
        return salePrice;
    }
    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public String getBarcode() {
        return barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    public String getPrefixCode() {
        return prefixCode;
    }
    public void setPrefixCode(String prefixCode) {
        this.prefixCode = prefixCode;
    }
    public String getHintMsg() {
        return hintMsg;
    }
    public void setHintMsg(String hintMsg) {
        this.hintMsg = hintMsg;
    }
    public boolean isAvailable() {
        return isAvailable;
    }
    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    
}
