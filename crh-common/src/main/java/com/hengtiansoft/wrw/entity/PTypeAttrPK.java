/*
 * Project Name: wrw-common
 * File Name: PTypeAttrPK.java
 * Class Name: PTypeAttrPK
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
 * Class Name: PTypeAttrPK
 * Description: TODO
 * 
 * @author chengminmiao
 */
@Embeddable
public class PTypeAttrPK implements Serializable {

    private static final long serialVersionUID = 36898012131676927L;

    @Column(name = "TYPE_ID")
    private Long           typeId;

    @Column(name = "ATTR_ID")
    private Long           attrId;

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getAttrId() {
        return attrId;
    }

    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }

    public PTypeAttrPK(Long long1, Long long2) {
        super();
        this.typeId = long1;
        this.attrId = long2;
    }
    
    public PTypeAttrPK() {
	}
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((attrId == null) ? 0 : attrId.hashCode());
        result = prime * result + ((typeId == null) ? 0 : typeId.hashCode());
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
        PTypeAttrPK other = (PTypeAttrPK) obj;
        if (attrId == null) {
            if (other.attrId != null)
                return false;
        } else if (!attrId.equals(other.attrId))
            return false;
        if (typeId == null) {
            if (other.typeId != null)
                return false;
        } else if (!typeId.equals(other.typeId))
            return false;
        return true;
    }

}
