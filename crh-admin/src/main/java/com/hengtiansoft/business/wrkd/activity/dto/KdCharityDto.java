/*
 * Project Name: wrw-admin
 * File Name: KdCharityDto.java
 * Class Name: KdCharityDto
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

import java.util.Date;
import java.util.List;

import com.hengtiansoft.business.wrkd.image.dto.KdPImageDto;

/**
 * Class Name: KdCharityDto
 * Description: 酷袋公益活动
 * @author zhongyidong
 *
 */
public class KdCharityDto {

    private Long id;
    
    private String name;
    
    /**
     * 开始时间
     */
    private String startTime;
    
    /**
     * 开始时间
     */
    private String endTime;
    
    /**
     * 专辑
     */
    private Long featureId;
    
    /**
     * 专辑名称
     */
    private String featureName;
    
    /**
     * 感谢图片
     */
    private String imgUrl;
    
    /**
     * 活动说明
     */
    private String explainNote;
    
    /**
     * 活动详情
     */
    private String detailDesc;
    
    /**
     * 反馈详情
     */
    private String feedback;
    
    /**
     * 活动状态（未开始、进行中、已结束）
     */
    private String status;
    
    /**
     * 参与人数
     */
    private Integer involveNum;
    
    /**
     * 活动图片
     */
    private List<KdPImageDto> listImages;
    
    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Long featureId) {
        this.featureId = featureId;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getExplainNote() {
        return explainNote;
    }

    public void setExplainNote(String explainNote) {
        this.explainNote = explainNote;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getInvolveNum() {
        return involveNum;
    }

    public void setInvolveNum(Integer involveNum) {
        this.involveNum = involveNum;
    }

    public List<KdPImageDto> getListImages() {
        return listImages;
    }

    public void setListImages(List<KdPImageDto> listImages) {
        this.listImages = listImages;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
}
