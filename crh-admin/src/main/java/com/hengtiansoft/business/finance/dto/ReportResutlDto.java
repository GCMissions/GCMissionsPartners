/*
 * Project Name: wrw-admin
 * File Name: ReportResutlDto.java
 * Class Name: ReportResutlDto
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
package com.hengtiansoft.business.finance.dto;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hengtiansoft.common.util.CustomLongSerialize;

/**
 * Class Name: ReportResutlDto
 * Description: TODO
 * 
 * @author chengminmiao
 */
public class ReportResutlDto implements Serializable {

    private static final long serialVersionUID = 5750866318718868795L;

    private Long              orgId;

    private String            orgName;

    private String            orgType;

    private Long              shipProfit;

    private Long              prodProfit;

    // 罚款总额
    private Long              fineTotal;

    // 奖励总额
    private Long              rewardTotal;

    // 推广费用
    private Long              refereeTotal;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    @JsonSerialize(using = CustomLongSerialize.class)
    public Long getShipProfit() {
        return shipProfit;
    }

    public void setShipProfit(Long shipProfit) {
        this.shipProfit = shipProfit;
    }

    @JsonSerialize(using = CustomLongSerialize.class)
    public Long getProdProfit() {
        return prodProfit;
    }

    public void setProdProfit(Long prodProfit) {
        this.prodProfit = prodProfit;
    }

    @JsonSerialize(using = CustomLongSerialize.class)
    public Long getFineTotal() {
        return fineTotal;
    }

    public void setFineTotal(Long fineTotal) {
        this.fineTotal = fineTotal;
    }

    @JsonSerialize(using = CustomLongSerialize.class)
    public Long getRewardTotal() {
        return rewardTotal;
    }

    public void setRewardTotal(Long rewardTotal) {
        this.rewardTotal = rewardTotal;
    }

    @JsonSerialize(using = CustomLongSerialize.class)
    public Long getRefereeTotal() {
        return refereeTotal;
    }

    public void setRefereeTotal(Long refereeTotal) {
        this.refereeTotal = refereeTotal;
    }

}
