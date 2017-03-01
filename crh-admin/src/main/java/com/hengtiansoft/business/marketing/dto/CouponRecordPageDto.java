/*
 * Project Name: wrw-admin
 * File Name: CouponRecordPageDto.java
 * Class Name: CouponRecordPageDto
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

import com.hengtiansoft.common.dto.PagingDto;

/**
 * Class Name: CouponRecordPageDto
 * Description: TODO
 * 
 * @author chengminmiao
 */
public class CouponRecordPageDto extends PagingDto<CouponRecordDto> {

    private static final long serialVersionUID = -5918544267564841586L;

    private Long              couponId;

    private Long              totalNum;

    private Long              sendNum;

    private Long              usedNum;

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    public Long getSendNum() {
        return sendNum;
    }

    public void setSendNum(Long sendNum) {
        this.sendNum = sendNum;
    }

    public Long getUsedNum() {
        return usedNum;
    }

    public void setUsedNum(Long usedNum) {
        this.usedNum = usedNum;
    }

}
