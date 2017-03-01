/*
 * Project Name: wrw-common
 * File Name: KProductEntity.java
 * Class Name: KProductEntity
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hengtiansoft.common.domain.BaseEntity;

/**
 * Class Name: KProductEntity
 * Description: 酷袋商品
 * @author zhongyidong
 *
 */
@Entity
@Table(name = "kd_p_product")
public class KdProductEntity extends BaseEntity{

    private static final long serialVersionUID = 4126874867976634458L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    /**
     * 商品编号
     */
    @Column(name = "PCODE")
    private String pcode;

    @Column(name = "PNAME")
    private String pname;
    
    /**
     * 是否在商城显示
     */
    @Column(name = "IS_SHOW")
    private String isShow;
    
    /**
     * 是否是vip商品
     */
    @Column(name = "VIP")
    private String vip;
    
    /**
     * 原价
     */
    @Column(name = "ORIGIN_PRICE")
    private Integer originPrice;

    /**
     * 商品介绍
     */
    @Column(name = "QUICK_DESC")
    private String quickDesc;
    
    /**
     * 特别说明
     */
    @Column(name = "SPECIAL_NOTE")
    private String specialNote;
    
    /**
     * 商品主图URL
     */
    @Column(name = "IMG_URL")
    private String imgUrl;

    /**
     * 商品详情介绍
     */
    @Column(name = "DETAIL_DESC")
    private String detailDesc;
    
    /**
     * 促销活动标签
     */
    @Column(name = "ACT_TAG")
    private String actTag;
    
    /**
     * 最低价
     */
    @Column(name = "LOW_PRICE")
    private Integer lowPrice;
    
    /**
     * 最高价
     */
    @Column(name = "HIGH_PRICE")
    private Integer highPrice;

    /**
     * 是否删除
     */
    @Column(name = "IS_DELETED")
    private String isDeleted;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }
    
    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public Integer getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(Integer originPrice) {
        this.originPrice = originPrice;
    }

    public String getQuickDesc() {
        return quickDesc;
    }

    public void setQuickDesc(String quickDesc) {
        this.quickDesc = quickDesc;
    }
    
    public String getSpecialNote() {
        return specialNote;
    }

    public void setSpecialNote(String specialNote) {
        this.specialNote = specialNote;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public String getActTag() {
        return actTag;
    }

    public void setActTag(String actTag) {
        this.actTag = actTag;
    }

    public Integer getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(Integer lowPrice) {
        this.lowPrice = lowPrice;
    }

    public Integer getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(Integer highPrice) {
        this.highPrice = highPrice;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

}
