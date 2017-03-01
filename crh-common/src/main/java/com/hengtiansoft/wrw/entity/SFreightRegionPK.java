/*
 * Project Name: wrw-common
 * File Name: SFreightRegionPK.java
 * Class Name: SFreightRegionPK
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
 * Class Name: SFreightRegionPK
 * Description: TODO
 * 
 * @author chengminmiao
 */
@Embeddable
public class SFreightRegionPK implements Serializable {

    private static final long serialVersionUID = -7878935916111580692L;

    @Column(name = "CONFIG_ID")
    private Long              configId;

    @Column(name = "REGION_ID")
    private Integer           regionId;

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public SFreightRegionPK() {

    }

    public SFreightRegionPK(Long configId, Integer regionId) {
        super();
        this.configId = configId;
        this.regionId = regionId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((configId == null) ? 0 : configId.hashCode());
        result = prime * result + ((regionId == null) ? 0 : regionId.hashCode());
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
        SFreightRegionPK other = (SFreightRegionPK) obj;
        if (configId == null) {
            if (other.configId != null)
                return false;
        } else if (!configId.equals(other.configId))
            return false;
        if (regionId == null) {
            if (other.regionId != null)
                return false;
        } else if (!regionId.equals(other.regionId))
            return false;
        return true;
    }

  
}
