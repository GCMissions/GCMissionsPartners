/*
 * Project Name: wrw-admin
 * File Name: FreightConfigListDto.java
 * Class Name: FreightConfigListDto
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

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hengtiansoft.common.dto.PagingDto;
import com.hengtiansoft.common.util.CustomDoubleSerialize;
import com.hengtiansoft.common.util.WRWUtil;

/**
 * Class Name: FreightConfigListDto
 * Description: TODO
 * 
 * @author chengminmiao
 */
public class FreightConfigListDto extends PagingDto<FreightConfigDto> {

    private static final long serialVersionUID = 5575003711618453813L;

    private Long              freeFreight;

    private Long              startNum;

    private Long              startCost;

    private BigDecimal            startCostYuan;

    private Long              plusNum;

    private Long              plusCost;

    private BigDecimal            plusCostYuan;

    public Long getFreeFreight() {
        return freeFreight;
    }

    public void setFreeFreight(Long freeFreight) {
        this.freeFreight = freeFreight;
    }

    public Long getStartNum() {
        return startNum;
    }

    public void setStartNum(Long startNum) {
        this.startNum = startNum;
    }

    public Long getPlusNum() {
        return plusNum;
    }

    public void setPlusNum(Long plusNum) {
        this.plusNum = plusNum;
    }

    public Long getStartCost() {
        return startCost;
    }

    public Long getPlusCost() {
        return plusCost;
    }

    public void setPlusCost(Long plusCost) {
        this.plusCost = plusCost;
        this.plusCostYuan = WRWUtil.transFenToYuan(plusCost);
    }

    public void setStartCost(Long startCost) {
        this.startCost = startCost;
        this.startCostYuan = WRWUtil.transFenToYuan(startCost);
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
