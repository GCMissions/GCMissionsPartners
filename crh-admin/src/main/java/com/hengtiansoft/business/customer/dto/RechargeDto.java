/*
 * Project Name: wrw-admin
 * File Name: RechargeDto.java
 * Class Name: RechargeDto
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
package com.hengtiansoft.business.customer.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hengtiansoft.common.util.CustomDoubleSerialize;
import com.hengtiansoft.common.util.WRWUtil;

/**
 * Class Name: RechargeDto
 * Description: TODO
 * 
 * @author chengminmiao
 */
public class RechargeDto implements Serializable {

    private static final long serialVersionUID = -8794476210341357882L;

    private String            rechargeId;

    private Long              acctId;

    private Long              memberId;

    private String            memberName;

    private String            memberPhone;

    private Long              configId;

    private Long              amount;

    private BigDecimal            amountYuan;

    private Long              actualAmount;

    private BigDecimal            actualAmountYuan;

    private String            paymentType;

    private String            payStatus;

    private Date              payDate;

    public String getRechargeId() {
        return rechargeId;
    }

    public void setRechargeId(String rechargeId) {
        this.rechargeId = rechargeId;
    }

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
        this.amountYuan = WRWUtil.transFenToYuan(amount);
    }

    public Long getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Long actualAmount) {
        this.actualAmount = actualAmount;
        this.actualAmountYuan = WRWUtil.transFenToYuan(actualAmount);
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    @JsonSerialize(using = CustomDoubleSerialize.class)
    public BigDecimal getAmountYuan() {
        return amountYuan;
    }

    public void setAmountYuan(BigDecimal amountYuan) {
        this.amountYuan = amountYuan;
        this.amount = WRWUtil.transYuanToFen(amountYuan);
    }

    @JsonSerialize(using = CustomDoubleSerialize.class)
    public BigDecimal getActualAmountYuan() {
        return actualAmountYuan;
    }

    public void setActualAmountYuan(BigDecimal actualAmountYuan) {
        this.actualAmountYuan = actualAmountYuan;
        this.actualAmount = WRWUtil.transYuanToFen(actualAmountYuan);
    }

}
