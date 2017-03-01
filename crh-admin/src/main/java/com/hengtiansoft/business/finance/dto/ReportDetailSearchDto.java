/*
 * Project Name: wrw-admin
 * File Name: ReportDetailSearchDto.java
 * Class Name: ReportDetailSearchDto
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

import com.hengtiansoft.common.dto.PagingDto;

/**
 * Class Name: ReportDetailSearchDto
 * Description: TODO
 * 
 * @author chengminmiao
 */
public class ReportDetailSearchDto extends PagingDto<ReportDetailDto> {

    private static final long serialVersionUID = -6752403306007124558L;

    /** 查询条件 **/
    private Date              beginDate;

    private Date              endDate;

    private String            orgType;

    private Long              orgId;

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

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Long actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Long getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Long couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Long getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(Long totalProfit) {
        this.totalProfit = totalProfit;
    }

    public Long getTotal1Profit() {
        return total1Profit;
    }

    public void setTotal1Profit(Long total1Profit) {
        this.total1Profit = total1Profit;
    }

    public Long getTotalPProfit() {
        return totalPProfit;
    }

    public void setTotalPProfit(Long totalPProfit) {
        this.totalPProfit = totalPProfit;
    }

    public Long getTotalZProfit() {
        return totalZProfit;
    }

    public void setTotalZProfit(Long totalZProfit) {
        this.totalZProfit = totalZProfit;
    }

    public Long getTotalFine() {
        return totalFine;
    }

    public void setTotalFine(Long totalFine) {
        this.totalFine = totalFine;
    }

    public Long getTotalReward() {
        return totalReward;
    }

    public void setTotalReward(Long totalReward) {
        this.totalReward = totalReward;
    }

    public Long getTotalReferee() {
        return totalReferee;
    }

    public void setTotalReferee(Long totalReferee) {
        this.totalReferee = totalReferee;
    }

}
