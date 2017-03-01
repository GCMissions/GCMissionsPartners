/*
 * Project Name: wrw-admin
 * File Name: MemberSearchDto.java
 * Class Name: MemberSearchDto
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

import com.hengtiansoft.common.dto.PagingDto;

/**
 * Class Name: MemberSearchDto
 * Description: TODO
 * 
 * @author xianghuang
 */
public class MemberSearchDto extends PagingDto<MemberDto> {

    private static final long serialVersionUID = -4177583361450558245L;

    private String            memberName;

    private String            phone;

    private String            dateStart;

    private String            dateEnd;

    private Double            balanceStart;

    private Double            balanceEnd;

    private Long              pointStart;

    private Long              pointEnd;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Double getBalanceStart() {
        return balanceStart;
    }

    public void setBalanceStart(Double balanceStart) {
        this.balanceStart = balanceStart;
    }

    public Double getBalanceEnd() {
        return balanceEnd;
    }

    public void setBalanceEnd(Double balanceEnd) {
        this.balanceEnd = balanceEnd;
    }

    public Long getPointStart() {
        return pointStart;
    }

    public void setPointStart(Long pointStart) {
        this.pointStart = pointStart;
    }

    public Long getPointEnd() {
        return pointEnd;
    }

    public void setPointEnd(Long pointEnd) {
        this.pointEnd = pointEnd;
    }

}
