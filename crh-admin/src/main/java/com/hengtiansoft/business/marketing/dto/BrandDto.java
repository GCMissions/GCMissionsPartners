/*
 * Project Name: wrw-admin
 * File Name: BrandDto.java
 * Class Name: BrandDto
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
 * Class Name: BrandDto
 * Description: TODO
 * 
 * @author chengminmiao
 */
public class BrandDto implements Serializable {

    private static final long serialVersionUID = -1145303187515565842L;

    private Long              brandId;

    private String            brandName;

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

}
