/*
 * Project Name: wrw-common
 * File Name: SRechargeCouponEntity.java
 * Class Name: SRechargeCouponEntity
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
package com.hengtiansoft.wrw.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Class Name: SRechargeCouponEntity
 * Description: TODO
 * 
 * @author chengminmiao
 */
@Entity
@Table(name = "S_RECHARGE_COUPON")
public class SRechargeCouponEntity implements Serializable {

    private static final long serialVersionUID = 8507462632981912993L;

    @EmbeddedId
    private SRechargeCouponPK id;

    @Column(name = "NUM")
    private Integer           num;

    @Column(name = "COUPON_NAME")
    private String            couponName;

    public SRechargeCouponPK getId() {
        return id;
    }

    public void setId(SRechargeCouponPK id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    @Override
    public String toString() {
        return "SRechargeCouponEntity [id=" + id + ", num=" + num + ", couponName=" + couponName + "]";
    }
}
