/*
 * Project Name: wrw-common
 * File Name: SFreightRegion.java
 * Class Name: SFreightRegion
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

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Class Name: SFreightRegion
 * Description: TODO
 * 
 * @author chengminmiao
 */
@Entity
@Table(name = "S_FREIGHT_REGION")
public class SFreightRegionEntity implements Serializable {

    private static final long serialVersionUID = -5045614857347219958L;

    @EmbeddedId
    private SFreightRegionPK  id;

    public SFreightRegionPK getId() {
        return id;
    }

    public SFreightRegionEntity() {
        super();
    }

    public void setId(SFreightRegionPK id) {
        this.id = id;
    }

    public SFreightRegionEntity(Long configId, Integer regionId) {
        super();
        this.id = new SFreightRegionPK(configId, regionId);
    }

}
