/*
 * Project Name: wrw-admin
 * File Name: RechargeConfDto.java
 * Class Name: RechargeConfDto
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hengtiansoft.common.util.CustomDoubleSerialize;
import com.hengtiansoft.common.util.WRWUtil;

/**
 * Class Name: RechargeConfDto
 * Description: TODO
 * 
 * @author chengminmiao
 */
public class RechargeConfDto implements Serializable {

    private static final long       serialVersionUID = 8361823045737183106L;

    private Long                    configId;

    private Long                    amount;

    private BigDecimal                  amountYuan;

    private String                  status;

    private String                  note;

    private Date                    createDate;

    private Long                    createId;

    private Date                    modifyDate;

    private Long                    modifyId;

    private List<RechargeCouponDto> couponDtos;

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

    @JsonSerialize(using = CustomDoubleSerialize.class)
    public BigDecimal getAmountYuan() {
        return amountYuan;
    }

    public void setAmountYuan(BigDecimal amountYuan) {
        this.amountYuan = amountYuan;
        this.amount = WRWUtil.transYuanToFen(amountYuan);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Long getModifyId() {
        return modifyId;
    }

    public void setModifyId(Long modifyId) {
        this.modifyId = modifyId;
    }

    public List<RechargeCouponDto> getCouponDtos() {
        return couponDtos;
    }

    public void setCouponDtos(List<RechargeCouponDto> couponDtos) {
        this.couponDtos = couponDtos;
    }

    public void addCoupon(RechargeCouponDto counponDto) {
        if (this.couponDtos == null) {
            this.couponDtos = new ArrayList<RechargeCouponDto>();
        }
        this.couponDtos.add(counponDto);
    }
}
