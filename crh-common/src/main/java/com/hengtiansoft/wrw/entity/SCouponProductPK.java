/*
 * Project Name: wrw-common
 * File Name: SCouponProductPK.java
 * Class Name: SCouponProductPK
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
package com.hengtiansoft.wrw.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Class Name: SCouponProductPK
 * Description: TODO
 * 
 * @author chengminmiao
 */
@Embeddable
public class SCouponProductPK implements Serializable {

    private static final long serialVersionUID = -2652295531236246538L;

    @Column(name = "COUPON_ID")
    private Long              couponId;

    @Column(name = "PRODUCT_ID")
    private Long              productId;

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public SCouponProductPK(Long couponId, Long productId) {
        super();
        this.couponId = couponId;
        this.productId = productId;
    }

    public SCouponProductPK() {}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((couponId == null) ? 0 : couponId.hashCode());
        result = prime * result + ((productId == null) ? 0 : productId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SCouponProductPK other = (SCouponProductPK) obj;
        if (couponId == null) {
            if (other.couponId != null)
                return false;
        } else if (!couponId.equals(other.couponId))
            return false;
        if (productId == null) {
            if (other.productId != null)
                return false;
        } else if (!productId.equals(other.productId))
            return false;
        return true;
    }

}
