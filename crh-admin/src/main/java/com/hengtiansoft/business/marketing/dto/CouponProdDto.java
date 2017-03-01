/*
 * Project Name: wrw-admin
 * File Name: CouponProdDto.java
 * Class Name: CouponProdDto
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
 * Class Name: CouponProdDto
 * Description: TODO
 * 
 * @author chengminmiao
 */
public class CouponProdDto implements Serializable {

    private static final long serialVersionUID = 7602913354798934535L;

    private Long              productId;

    private String            productName;

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

}
