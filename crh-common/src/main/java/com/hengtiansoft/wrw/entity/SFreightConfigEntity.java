/*
 * Project Name: wrw-common
 * File Name: SFreightConfig.java
 * Class Name: SFreightConfig
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hengtiansoft.common.domain.BaseEntity;

/**
 * Class Name: SFreightConfig
 * Description: TODO
 * 
 * @author chengminmiao
 */
@Entity
@Table(name = "S_FREIGHT_CONFIG")
public class SFreightConfigEntity extends BaseEntity {

    private static final long serialVersionUID = 4636042968448844431L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONFIG_ID")
    private Long              configId;

    @Column(name = "FREIGHT_TYPE", insertable = true, updatable = false)
    private String            freightType;

    @Column(name = "START_NUM")
    private Long              startNum;

    @Column(name = "START_COST")
    private Long              startCost;

    @Column(name = "PLUS_NUM")
    private Long              plusNum;

    @Column(name = "PLUS_COST")
    private Long              plusCost;

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public String getFreightType() {
        return freightType;
    }

    public void setFreightType(String freightType) {
        this.freightType = freightType;
    }

    public Long getStartNum() {
        return startNum;
    }

    public void setStartNum(Long startNum) {
        this.startNum = startNum;
    }

    public Long getStartCost() {
        return startCost;
    }

    public void setStartCost(Long startCost) {
        this.startCost = startCost;
    }

    public Long getPlusNum() {
        return plusNum;
    }

    public void setPlusNum(Long plusNum) {
        this.plusNum = plusNum;
    }

    public Long getPlusCost() {
        return plusCost;
    }

    public void setPlusCost(Long plusCost) {
        this.plusCost = plusCost;
    }

}
