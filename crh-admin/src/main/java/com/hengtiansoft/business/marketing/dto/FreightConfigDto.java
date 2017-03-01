/*
 * Project Name: wrw-admin
 * File Name: FreightConfigDto.java
 * Class Name: FreightConfigDto
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
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hengtiansoft.common.util.CustomDoubleSerialize;
import com.hengtiansoft.common.util.WRWUtil;

/**
 * Class Name: FreightConfigDto
 * Description: TODO
 * 
 * @author chengminmiao
 */
public class FreightConfigDto implements Serializable {

    private static final long serialVersionUID = -4195995845203678935L;

    private Long              configId;

    private String            freightType;

    private Long              startNum;

    private Long              startCost;

    private BigDecimal            startCostYuan;

    private Long              plusNum;

    private Long              plusCost;

    private BigDecimal            plusCostYuan;

    private List<Integer>     regionId;

    private String            regionIdString;

    private String            regionNames;

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
        this.startCostYuan = WRWUtil.transFenToYuan(startCost);
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
        this.plusCostYuan = WRWUtil.transFenToYuan(plusCost);
    }

    public List<Integer> getRegionId() {
        return regionId;
    }

    public void setRegionId(List<Integer> regionId) {
        this.regionId = regionId;
    }

    public String getRegionIdString() {
        return regionIdString;
    }

    public void setRegionIdString(String regionIdString) {
        this.regionIdString = regionIdString;
    }

    public String getRegionNames() {
        return regionNames;
    }

    public void setRegionNames(String regionNames) {
        this.regionNames = regionNames;
    }

    @JsonSerialize(using = CustomDoubleSerialize.class)
    public BigDecimal getStartCostYuan() {
        return startCostYuan;
    }

    public void setStartCostYuan(BigDecimal startCostYuan) {
        this.startCostYuan = startCostYuan;
        this.startCost = WRWUtil.transYuanToFen(startCostYuan);
    }

    @JsonSerialize(using = CustomDoubleSerialize.class)
    public BigDecimal getPlusCostYuan() {
        return plusCostYuan;
    }

    public void setPlusCostYuan(BigDecimal plusCostYuan) {
        this.plusCostYuan = plusCostYuan;
        this.plusCost = WRWUtil.transYuanToFen(plusCostYuan);
    }

}
