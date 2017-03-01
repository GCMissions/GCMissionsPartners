/*
 * Project Name: kd-wechat
 * File Name: KdTeamActRecordResponseDto.java
 * Class Name: KdTeamActRecordResponseDto
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
package com.hengtiansoft.business.wrkd.activity.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Class Name: KdTeamActRecordResponseDto
 * Description: 团购参团记录Dto
 * @author zhisongliu
 *
 */
public class KdTeamActRecordResponseDto implements Serializable{

    private static final long serialVersionUID = -9098449873337470156L;
    
    private Long recordId;
    
    private Long createTeamId;
    
    private String createTeamName;
    
    private Integer lessNum;
    
    private String custImage;
    
    private double percentage;//百分比
    
    private Date  effectiveEndDate ;    // 活动结束时间

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getCreateTeamId() {
        return createTeamId;
    }

    public void setCreateTeamId(Long createTeamId) {
        this.createTeamId = createTeamId;
    }

    public String getCreateTeamName() {
        return createTeamName;
    }

    public void setCreateTeamName(String createTeamName) {
        this.createTeamName = createTeamName;
    }

    public Integer getLessNum() {
        return lessNum;
    }

    public void setLessNum(Integer lessNum) {
        this.lessNum = lessNum;
    }

    public String getCustImage() {
        return custImage;
    }

    public void setCustImage(String custImage) {
        this.custImage = custImage;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public Date getEffectiveEndDate() {
        return effectiveEndDate;
    }

    public void setEffectiveEndDate(Date effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }
    
    
}

