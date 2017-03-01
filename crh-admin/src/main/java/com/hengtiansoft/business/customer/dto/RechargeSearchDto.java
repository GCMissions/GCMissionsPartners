/*
 * Project Name: wrw-admin
 * File Name: RechargeSearchDto.java
 * Class Name: RechargeSearchDto
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

import java.util.Date;

import com.hengtiansoft.common.dto.PagingDto;

/**
 * Class Name: RechargeSearchDto
 * Description: TODO
 * 
 * @author chengminmiao
 */
public class RechargeSearchDto extends PagingDto<RechargeDto> {

    private static final long serialVersionUID = 7285226238016841461L;

    private String            memberPhone;

    private String            memberName;

    private Long              amount;

    private String            paymentType;

    private Date              payDateBegin;

    private Date              payDateEnd;

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        if (null != amount) {
            this.amount = amount * 100;
        }
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Date getPayDateBegin() {
        return payDateBegin;
    }

    public void setPayDateBegin(Date payDateBegin) {
        this.payDateBegin = payDateBegin;
    }

    public Date getPayDateEnd() {
        return payDateEnd;
    }

    public void setPayDateEnd(Date payDateEnd) {
        this.payDateEnd = payDateEnd;
    }

}
