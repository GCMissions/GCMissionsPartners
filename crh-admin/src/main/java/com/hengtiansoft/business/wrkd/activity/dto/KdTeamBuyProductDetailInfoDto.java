/*
 * Project Name: kd-wechat
 * File Name: KdTeamBuyProductDetailInfoDto.java
 * Class Name: KdTeamBuyProductDetailInfoDto
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
import java.util.List;

/**
 * Class Name: KdTeamBuyProductDetailInfoDto
 * Description: 团购活动详情基本信息详情DTO
 * @author yiminli
 *
 */
public class KdTeamBuyProductDetailInfoDto implements Serializable{

    private static final long serialVersionUID = -5012755259550794558L;
    
    private Long            id;                  //活动ID
    
    private Long            productId;           //商品ID
    
    private String          teamName;            //团购名称
    
    private String          description;         //数据库名称为活动详情，对应前端为商品详情
    
    private String          status;              //团购状态
    
    private String          type;                //团购商品类型
    
    private Date            effectiveStartDate;  //活动开始时间
    
    private Date            effectiveEndDate;    //活动结束时间
    
    private String          image;               //活动说明图片
    
    private Long            teamPrice;           //团购价格     --- 分
    
    private Integer         startCount;          //成团数量
        
    private Integer         limitCount;          //单个用户购买限制数量
    
    private String          specialDesc;         //活动特别说明
    
    private Long            productPrice;        //商品价格                        ---分
    
    private String          formatTeamPrice;     //团购价格       --- 元
    
    private String          formatProductPrice ;  //商品价格     ---元
    
    private String          priceType;
    
    private List<KdTeamBuySpecListDto>          listSpec;             //规格信息

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getEffectiveStartDate() {
        return effectiveStartDate;
    }

    public void setEffectiveStartDate(Date effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    public Date getEffectiveEndDate() {
        return effectiveEndDate;
    }

    public void setEffectiveEndDate(Date effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getTeamPrice() {
        return teamPrice;
    }

    public void setTeamPrice(Long teamPrice) {
        this.teamPrice = teamPrice;
    }

    public String getFormatTeamPrice() {
        return formatTeamPrice;
    }

    public void setFormatTeamPrice(String formatTeamPrice) {
        this.formatTeamPrice = formatTeamPrice;
    }

    public String getFormatProductPrice() {
        return formatProductPrice;
    }

    public void setFormatProductPrice(String formatProductPrice) {
        this.formatProductPrice = formatProductPrice;
    }

    public Integer getStartCount() {
        return startCount;
    }

    public void setStartCount(Integer startCount) {
        this.startCount = startCount;
    }

    public Integer getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(Integer limitCount) {
        this.limitCount = limitCount;
    }

    public String getSpecialDesc() {
        return specialDesc;
    }

    public void setSpecialDesc(String specialDesc) {
        this.specialDesc = specialDesc;
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Long productPrice) {
        this.productPrice = productPrice;
    }

    public List<KdTeamBuySpecListDto> getListSpec() {
        return listSpec;
    }

    public void setListSpec(List<KdTeamBuySpecListDto> listSpec) {
        this.listSpec = listSpec;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }
    
}
