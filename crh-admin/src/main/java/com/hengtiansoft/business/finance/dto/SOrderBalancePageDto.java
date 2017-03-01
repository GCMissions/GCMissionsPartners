package com.hengtiansoft.business.finance.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class SOrderBalancePageDto extends PagingDto<SOrderBalanceMailDto> {

    /**
     * Variables Name: serialVersionUID
     * Description: TODO
     * Value Description: TODO
     */
    private static final long serialVersionUID = 6042042975845804950L;

    private String            orderId;

    private String            sDate;

    private String            eDate;

    private String            createDate;

    private String            balanceType;                             // 报表类型

    private Long              totalAmount;                             // 订单总额

    private Long              actualAmount;                            // 实付金额

    private Long              couponAmount;                            // 优惠劵总金额

    private Long              totalProfit;                             // 总毛利

    private Long              totalSPProfit;                           // 平台总分利

    private Long              pTotalShipProfit;

    private Long              pTotalProProfit;

    private Long              pTotalSPProfit;                          // 区域平台商总分利

    private Long              zTotalSPProfit;                          // 终端配送商总分利

    private Long              zTotalShipProfit;                        // 终端配送商总分利

    private Long              zTotalProProfit;

    private String            orgId;

    private String            orgType;

    // 罚款总额
    private Long              fineTotal;

    // 奖励总额
    private Long              rewardTotal;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String geteDate() {
        return eDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
    }

    public String getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
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

    public Long getTotalSPProfit() {
        return totalSPProfit;
    }

    public void setTotalSPProfit(Long totalSPProfit) {
        this.totalSPProfit = totalSPProfit;
    }

    public Long getpTotalSPProfit() {
        return pTotalSPProfit;
    }

    public void setpTotalSPProfit(Long pTotalSPProfit) {
        this.pTotalSPProfit = pTotalSPProfit;
    }

    public Long getzTotalSPProfit() {
        return zTotalSPProfit;
    }

    public void setzTotalSPProfit(Long zTotalSPProfit) {
        this.zTotalSPProfit = zTotalSPProfit;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Long getzTotalShipProfit() {
        return zTotalShipProfit;
    }

    public void setzTotalShipProfit(Long zTotalShipProfit) {
        this.zTotalShipProfit = zTotalShipProfit;
    }

    public Long getpTotalShipProfit() {
        return pTotalShipProfit;
    }

    public void setpTotalShipProfit(Long pTotalShipProfit) {
        this.pTotalShipProfit = pTotalShipProfit;
    }

    public Long getpTotalProProfit() {
        return pTotalProProfit;
    }

    public void setpTotalProProfit(Long pTotalProProfit) {
        this.pTotalProProfit = pTotalProProfit;
    }

    public Long getzTotalProProfit() {
        return zTotalProProfit;
    }

    public void setzTotalProProfit(Long zTotalProProfit) {
        this.zTotalProProfit = zTotalProProfit;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Long getFineTotal() {
        return fineTotal;
    }

    public void setFineTotal(Long fineTotal) {
        this.fineTotal = fineTotal;
    }

    public Long getRewardTotal() {
        return rewardTotal;
    }

    public void setRewardTotal(Long rewardTotal) {
        this.rewardTotal = rewardTotal;
    }

}
