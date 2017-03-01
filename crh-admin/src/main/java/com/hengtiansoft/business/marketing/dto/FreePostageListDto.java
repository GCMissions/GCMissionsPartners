/*
 * Project Name: wrw-admin
 * File Name: FreePostageListDto.java
 * Class Name: FreePostageListDto
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

import com.hengtiansoft.common.dto.PagingDto;

/**
 * Class Name: FreePostageListDto
 * Description: TODO
 * 
 * @author chengminmiao
 */
public class FreePostageListDto extends PagingDto<FreePostageDto> {

    private static final long serialVersionUID = 5588992134882008311L;

    private Long              brandId;

    private Long              cateId;

    private String            productCode;

    private String            productName;

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
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

}
