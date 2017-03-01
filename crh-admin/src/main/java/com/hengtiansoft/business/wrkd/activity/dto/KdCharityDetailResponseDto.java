/*
 * Project Name: kd-wechat
 * File Name: KdCharityDetailResponseDto.java
 * Class Name: KdCharityDetailResponseDto
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
import java.util.List;

/**
 * Class Name: KdCharityDetailResponseDto
 * Description: 公益活动信息详情DTO
 * @author zhisongliu
 *
 */
public class KdCharityDetailResponseDto implements Serializable{

    private static final long serialVersionUID = -6886089394780155280L;

    private Long actId;
    
    private String actName;
    
    private Long featureId;     //专享ID
    
    private String thanksImg;   //活动感谢图片
    
    private String explainNote;  //活动说明
    
    private String detailDesc;   //活动详情
    
    private String feedback;     //反馈信息
    
    private String status;       //状态   1.进行中，2-已结束
    
    private String isDeleted;     //是否删除  0--未删除，1--已删除
    
    private List<String>  imageList;   //轮播图片
    
    private String featureName;   //专享名称
    
    private Integer actNum ;     // 公益参与人数

    public Long getActId() {
        return actId;
    }

    public void setActId(Long actId) {
        this.actId = actId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public Long getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Long featureId) {
        this.featureId = featureId;
    }

    public String getThanksImg() {
        return thanksImg;
    }

    public void setThanksImg(String thanksImg) {
        this.thanksImg = thanksImg;
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

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public Integer getActNum() {
        return actNum;
    }

    public void setActNum(Integer actNum) {
        this.actNum = actNum;
    }
    
    
    

}
