/*
 * Project Name: wrw-common
 * File Name: PCategoryBrandPK.java
 * Class Name: PCategoryBrandPK
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
 * Class Name: PCategoryBrandPK
 * Description: TODO
 * 
 * @author chengminmiao
 */
@Embeddable
public class PCateBrandPK implements Serializable {

    private static final long serialVersionUID = 4569811251750930543L;

    @Column(name = "CATE_ID")
    private Long              cateId;

    @Column(name = "BRAND_ID")
    private Long              brandId;

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public PCateBrandPK(Long cateId, Long brandId) {
        this.cateId = cateId;
        this.brandId = brandId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((brandId == null) ? 0 : brandId.hashCode());
        result = prime * result + ((cateId == null) ? 0 : cateId.hashCode());
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
        PCateBrandPK other = (PCateBrandPK) obj;
        if (brandId == null) {
            if (other.brandId != null)
                return false;
        } else if (!brandId.equals(other.brandId))
            return false;
        if (cateId == null) {
            if (other.cateId != null)
                return false;
        } else if (!cateId.equals(other.cateId))
            return false;
        return true;
    }

}
