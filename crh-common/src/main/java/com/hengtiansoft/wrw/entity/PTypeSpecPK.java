/*
 * Project Name: wrw-common
 * File Name: PTypeSpecPK.java
 * Class Name: PTypeSpecPK
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
 * Class Name: PTypeSpecPK
 * Description: TODO
 * 
 * @author chengminmiao
 */
@Embeddable
public class PTypeSpecPK implements Serializable {

    private static final long serialVersionUID = -8413156834519330619L;

    @Column(name = "TYPE_ID")
    private Long              typeId;

    @Column(name = "SPEC_ID")
    private Long              specId;

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getSpecId() {
        return specId;
    }

    public void setSpecId(Long specId) {
        this.specId = specId;
    }

    public PTypeSpecPK(Long typeId, Long specId) {
        super();
        this.typeId = typeId;
        this.specId = specId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((specId == null) ? 0 : specId.hashCode());
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
        PTypeSpecPK other = (PTypeSpecPK) obj;
        if (specId == null) {
            if (other.specId != null)
                return false;
        } else if (!specId.equals(other.specId))
            return false;
        if (typeId == null) {
            if (other.typeId != null)
                return false;
        } else if (!typeId.equals(other.typeId))
            return false;
        return true;
    }

}
