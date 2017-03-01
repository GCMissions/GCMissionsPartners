/*
 * Project Name: wrw-admin
 * File Name: KProductDto.java
 * Class Name: KProductDto
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
package com.hengtiansoft.business.wrkd.product.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hengtiansoft.business.wrkd.image.dto.KdPImageDto;

/**
 * Class Name: KProductDto
 * Description: 酷袋商品前端交互对象
 * @author zhongyidong
 *
 */
public class KdProductDto {
    
    private Long id;
    
    private String pcode;

    private String pname;

    // 是否在商城显示
    private String isShow;
    
    // VIP商品标识
    private String vip;
    
    private Integer originPrice;
    
    // 价格（可能是区间）
    private String priceRange;

    // 商品介绍
    private String quickDesc;
    
    // 特别说明
    private String specialNote;

    // 商品主图URL
    private String imgUrl;
    
    // 商品图片
    private List<KdPImageDto> listImages;

    // 商品详情介绍
    private String detailDesc;
    
    // 促销活动标签
    private String actTag;
    
    // 是否删除
    private String isDeleted;
    
    // 上下架状态
    private String saleStatus;

    // 上架时间
    private String onTime;
    
    // 下架时间
    private String offTime;
    
    // 运费信息
    private List<KdProductFreightDto> freightInfo;
    
    // 根据价格合并后的运费信息
    private Map<String, List<KdProductFreightDto>> freightMap;
    
    private Date createDate;
    
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

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
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

    public List<KdPImageDto> getListImages() {
        return listImages;
    }

    public void setListImages(List<KdPImageDto> listImages) {
        this.listImages = listImages;
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

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    public String getOnTime() {
        return onTime;
    }

    public void setOnTime(String onTime) {
        this.onTime = onTime;
    }

    public String getOffTime() {
        return offTime;
    }

    public void setOffTime(String offTime) {
        this.offTime = offTime;
    }

    public List<KdProductFreightDto> getFreightInfo() {
        return freightInfo;
    }

    public void setFreightInfo(List<KdProductFreightDto> freightInfo) {
        this.freightInfo = freightInfo;
    }

    public Map<String, List<KdProductFreightDto>> getFreightMap() {
        return freightMap;
    }

    public void setFreightMap(Map<String, List<KdProductFreightDto>> freightMap) {
        this.freightMap = freightMap;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
