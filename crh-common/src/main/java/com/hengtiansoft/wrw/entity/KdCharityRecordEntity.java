/*
 * Project Name: kd-wechat
 * File Name: KdCharityRecordEntity.java
 * Class Name: KdCharityRecordEntity
 *
 * Copyright 2014 Hengtian Software Inc
 *
 * Licensed under the Hengtiansoft
 *
 * http://www.hengtiansoft.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.wrw.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class Name: KdCharityRecordEntity
 * Description: 公益活动用户记录表
 * @author zhisongliu
 *
 */
@Table(name ="kd_charity_record")
@Entity
public class KdCharityRecordEntity implements Serializable{

    private static final long serialVersionUID = 5271518010167999412L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="RECORD_ID")
    private Long recordId;
    
    @Column(name ="CHARITY_ID")
    private Long charityId;
    
    @Column(name ="FEATURE_ID")
    private Long featureId;
    
    @Column(name ="MEMBER_ID")
    private Long memberId;
    
    @Column(name ="STATUS")
    private String status;
    
    @Column(name ="RECORD_DATE")
    private Date  recordDate;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getCharityId() {
        return charityId;
    }

    public void setCharityId(Long charityId) {
        this.charityId = charityId;
    }

    public Long getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Long featureId) {
        this.featureId = featureId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }
    
    
    
}
