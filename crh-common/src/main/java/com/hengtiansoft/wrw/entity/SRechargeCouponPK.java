/*
 * Project Name: wrw-common
 * File Name: SRechargeCouponPK.java
 * Class Name: SRechargeCouponPK
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
 * Class Name: SRechargeCouponPK
 * Description: TODO
 * 
 * @author chengminmiao
 */
@Embeddable
public class SRechargeCouponPK implements Serializable {

    private static final long serialVersionUID = -3876163220621225045L;

    @Column(name = "CONFIG_ID")
    private Long              configId;

    @Column(name = "COUPON_ID")
    private Long              couponId;

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public SRechargeCouponPK() {}

    public SRechargeCouponPK(Long configId, Long couponId) {
        super();
        this.configId = configId;
        this.couponId = couponId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((configId == null) ? 0 : configId.hashCode());
        result = prime * result + ((couponId == null) ? 0 : couponId.hashCode());
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
        SRechargeCouponPK other = (SRechargeCouponPK) obj;
        if (configId == null) {
            if (other.configId != null)
                return false;
        } else if (!configId.equals(other.configId))
            return false;
        if (couponId == null) {
            if (other.couponId != null)
                return false;
        } else if (!couponId.equals(other.couponId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SRechargeCouponPK [configId=" + configId + ", couponId=" + couponId + "]";
    }
}
