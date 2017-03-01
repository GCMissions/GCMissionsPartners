package com.hengtiansoft.business.wrkd.activity.dto;

import java.io.Serializable;
import java.util.List;

/**
* Class Name: TwentyfourInfo
* Description: 24小时活动信息
* @author changchen
*
*/
public class TwentyfourInfo implements Serializable{
    

    private static final long serialVersionUID = 1L;
    

    /**
     * 活动名称
     */
    private String name;
    
    /**
     * 活动简介
     */
    private String shortDesc;
    
    /**
     * 活动特殊说明
     */
    private String specialDesc;
    
    /**
     * 活动详情
     */
    private String detailDesc;
    
    /**
     * 剩余库存
     */
    private int remainingAmount;
    
    /**
     * 参与人数
     */
    private int participantsNum;
    
    /**
     * 活动轮播图片
     */
    private List<String> imgUrls;
    
    /**
     * 活动说明图片
     */
    private String explainInfoPic;
    
    /**
     * 活动状态
     */
    private String actStatus;
    
    /**
     * 活动开始日期
     */
    private String beginDate;
    
    /**
     * 活动结束日期
     */
    private String endDate;
    
    /**
     * 活动商品ID
     */
    private Long productId;
    
    /**
     * 商品原价
     */
    private String productPrice;
    
    /**
     * 发起人昵称
     */
    private String originName;
    
    /**
     * 商品描述
     */
    private String productDesc;
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getSpecialDesc() {
        return specialDesc;
    }

    public void setSpecialDesc(String specialDesc) {
        this.specialDesc = specialDesc;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public int getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(int remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public int getParticipantsNum() {
        return participantsNum;
    }

    public void setParticipantsNum(int participantsNum) {
        this.participantsNum = participantsNum;
    }

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getExplainInfoPic() {
        return explainInfoPic;
    }

    public void setExplainInfoPic(String explainInfoPic) {
        this.explainInfoPic = explainInfoPic;
    }

    public String getActStatus() {
        return actStatus;
    }

    public void setActStatus(String actStatus) {
        this.actStatus = actStatus;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

}
