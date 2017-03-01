/*
 * Project Name: wrw-admin
 * File Name: RechargeCounponDto.java
 * Class Name: RechargeCounponDto
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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hengtiansoft.common.util.CustomDoubleSerialize;
import com.hengtiansoft.common.util.WRWUtil;

/**
 * Class Name: RechargeCounponDto
 * Description: TODO
 * 
 * @author chengminmiao
 */
public class RechargeCouponDto implements Serializable {

    private static final long serialVersionUID = -5206140720511973263L;

    private Long              couponId;

    private String            couponName;

    private Integer           num;

    private Long              value;

    private BigDecimal            valueYuan;

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
        this.valueYuan = WRWUtil.transFenToYuan(value);
    }

    @JsonSerialize(using = CustomDoubleSerialize.class)
    public BigDecimal getValueYuan() {
        return valueYuan;
    }

    public void setValueYuan(BigDecimal valueYuan) {
        this.valueYuan = valueYuan;
        this.value = WRWUtil.transYuanToFen(valueYuan);
    }

}
