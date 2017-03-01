/*
 * Project Name: wrw-admin
 * File Name: ReportsSearchDto.java
 * Class Name: ReportsSearchDto
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

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hengtiansoft.common.dto.PagingDto;
import com.hengtiansoft.common.util.CustomLongSerialize;

/**
 * Class Name: ReportsSearchDto
 * Description: TODO
 * 
 * @author chengminmiao
 */

public class ReportSearchDto extends PagingDto<ReportResutlDto> {

    private static final long serialVersionUID = 7223703926500735613L;

    /** 查询条件 **/
    private Date              beginDate;

    private Date              endDate;

    private String            orgType;

    private String            orgName;

    /** 返回结果 **/
    // 订单总额
    private Long              totalAmount;

    // 实付金额
    private Long              actualAmount;

    // 优惠劵总金额
    private Long              couponAmount;

    // 总毛利
    private Long              totalProfit;

    // 平台总分利
    private Long              total1Profit;

    // P总分利
    private Long              totalPProfit;

    // Z总分利
    private Long              totalZProfit;

    // 罚款总额
    private Long              totalFine;

    // 奖励总额
    private Long              totalReward;

    // 推广费用
    private Long              totalReferee;

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @JsonSerialize(using = CustomLongSerialize.class)
    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    @JsonSerialize(using = CustomLongSerialize.class)
    public Long getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Long actualAmount) {
        this.actualAmount = actualAmount;
    }

    @JsonSerialize(using = CustomLongSerialize.class)
    public Long getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Long couponAmount) {
        this.couponAmount = couponAmount;
    }

    @JsonSerialize(using = CustomLongSerialize.class)
    public Long getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(Long totalProfit) {
        this.totalProfit = totalProfit;
    }

    @JsonSerialize(using = CustomLongSerialize.class)
    public Long getTotal1Profit() {
        return total1Profit;
    }

    public void setTotal1Profit(Long total1Profit) {
        this.total1Profit = total1Profit;
    }

    @JsonSerialize(using = CustomLongSerialize.class)
    public Long getTotalPProfit() {
        return totalPProfit;
    }

    public void setTotalPProfit(Long totalPProfit) {
        this.totalPProfit = totalPProfit;
    }

    @JsonSerialize(using = CustomLongSerialize.class)
    public Long getTotalZProfit() {
        return totalZProfit;
    }

    public void setTotalZProfit(Long totalZProfit) {
        this.totalZProfit = totalZProfit;
    }

    @JsonSerialize(using = CustomLongSerialize.class)
    public Long getTotalFine() {
        return totalFine;
    }

    public void setTotalFine(Long totalFine) {
        this.totalFine = totalFine;
    }

    @JsonSerialize(using = CustomLongSerialize.class)
    public Long getTotalReward() {
        return totalReward;
    }

    public void setTotalReward(Long totalReward) {
        this.totalReward = totalReward;
    }

    @JsonSerialize(using = CustomLongSerialize.class)
    public Long getTotalReferee() {
        return totalReferee;
    }

    public void setTotalReferee(Long totalReferee) {
        this.totalReferee = totalReferee;
    }

}
