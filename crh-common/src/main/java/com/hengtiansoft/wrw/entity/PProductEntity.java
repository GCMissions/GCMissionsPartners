package com.hengtiansoft.wrw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hengtiansoft.common.domain.BaseEntity;

@Entity
@Table(name = "P_PRODUCT")
public class PProductEntity extends BaseEntity {

    private static final long serialVersionUID = -7679057512526327670L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long              productId;

    @Column(name = "BRAND_ID")
    private Long              brandId;

    @Column(name = "BRAND_NAME")
    private String            brandName;

    @Column(name = "CATE_ID")
    private Long              cateId;
    
    @Column(name = "ORG_ID")
    private Long orgId;

    @Column(name = "CATE_NAME")
    private String            cateName;

    @Column(name = "TYPE_ID")
    private Long              typeId;

    @Column(name = "GOOD_ID")
    private Long              goodId;

    @Column(name = "GOOD_NUM")
    private Long              goodNum;

    @Column(name = "PRODUCT_CODE")
    private String            productCode;

    @Column(name = "PRODUCT_NAME")
    private String            productName;

    @Column(name = "SORT")
    private Integer           sort;

    @Column(name = "STATUS")
    private String            status;

    @Column(name = "SALE_STATUS")
    private String            saleStatus;

    @Column(name = "UNIT_NAME")
    private String            unitName;

    @Column(name = "SPEC_NUM")
    private Integer           specNum;

    @Column(name = "PRICE")
    private Long              price;
    
    @Column(name = "ORIGINAL_PRICE")
    private Long originalPrice;

    @Column(name = "COST_PRICE")
    private Long              costPrice;

    @Column(name = "WEIGHT")
    private Long              weight;

    @Column(name = "IMAGE")
    private String            image;
    
    @Column(name = "IMAGE_KEY")
    private String imageKey;

    @Column(name = "DESCRIPTION")
    private String            description;

    @Column(name = "URL")
    private String            url;

    @Column(name = "NOTE")
    private String            note;
    
    @Column(name = "REBACK_NOTE")
    private String rebackNote;

    @Column(name = "PROMOTION")
    private String            promotion;

    @Column(name = "PRODUCTION_DATE")
    private Date              productionDate;

    @Column(name = "SALE_NUM")
    private Integer           saleNum;

    @Column(name = "SHIPFEE_CONFIG")
    private String            shipfeeConfig;
    
    @Column(name = "IS_CAPTCHA")
    private String isCaptcha;

    @Column(name = "VIP")
    private String vip;
    
    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public Long getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(Long goodNum) {
        this.goodNum = goodNum;
    }

    public Integer getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(Integer saleNum) {
        this.saleNum = saleNum;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    public Integer getSpecNum() {
        return specNum;
    }

    public void setSpecNum(Integer specNum) {
        this.specNum = specNum;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Long costPrice) {
        this.costPrice = costPrice;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getShipfeeConfig() {
        return shipfeeConfig;
    }

    public void setShipfeeConfig(String shipfeeConfig) {
        this.shipfeeConfig = shipfeeConfig;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Long originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getRebackNote() {
        return rebackNote;
    }

    public void setRebackNote(String rebackNote) {
        this.rebackNote = rebackNote;
    }

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    public String getIsCaptcha() {
        return isCaptcha;
    }

    public void setIsCaptcha(String isCaptcha) {
        this.isCaptcha = isCaptcha;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((brandId == null) ? 0 : brandId.hashCode());
        result = prime * result + ((brandName == null) ? 0 : brandName.hashCode());
        result = prime * result + ((cateId == null) ? 0 : cateId.hashCode());
        result = prime * result + ((cateName == null) ? 0 : cateName.hashCode());
        result = prime * result + ((costPrice == null) ? 0 : costPrice.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((goodId == null) ? 0 : goodId.hashCode());
        result = prime * result + ((goodNum == null) ? 0 : goodNum.hashCode());
        result = prime * result + ((image == null) ? 0 : image.hashCode());
        result = prime * result + ((imageKey == null) ? 0 : imageKey.hashCode());
        result = prime * result + ((isCaptcha == null) ? 0 : isCaptcha.hashCode());
        result = prime * result + ((note == null) ? 0 : note.hashCode());
        result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
        result = prime * result + ((originalPrice == null) ? 0 : originalPrice.hashCode());
        result = prime * result + ((price == null) ? 0 : price.hashCode());
        result = prime * result + ((productCode == null) ? 0 : productCode.hashCode());
        result = prime * result + ((productId == null) ? 0 : productId.hashCode());
        result = prime * result + ((productName == null) ? 0 : productName.hashCode());
        result = prime * result + ((productionDate == null) ? 0 : productionDate.hashCode());
        result = prime * result + ((promotion == null) ? 0 : promotion.hashCode());
        result = prime * result + ((rebackNote == null) ? 0 : rebackNote.hashCode());
        result = prime * result + ((saleNum == null) ? 0 : saleNum.hashCode());
        result = prime * result + ((saleStatus == null) ? 0 : saleStatus.hashCode());
        result = prime * result + ((shipfeeConfig == null) ? 0 : shipfeeConfig.hashCode());
        result = prime * result + ((sort == null) ? 0 : sort.hashCode());
        result = prime * result + ((specNum == null) ? 0 : specNum.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((typeId == null) ? 0 : typeId.hashCode());
        result = prime * result + ((unitName == null) ? 0 : unitName.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        result = prime * result + ((weight == null) ? 0 : weight.hashCode());
        result = prime * result + ((vip == null) ? 0 : vip.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        PProductEntity other = (PProductEntity) obj;
        if (brandId == null) {
            if (other.brandId != null)
                return false;
        } else if (!brandId.equals(other.brandId))
            return false;
        if (brandName == null) {
            if (other.brandName != null)
                return false;
        } else if (!brandName.equals(other.brandName))
            return false;
        if (cateId == null) {
            if (other.cateId != null)
                return false;
        } else if (!cateId.equals(other.cateId))
            return false;
        if (cateName == null) {
            if (other.cateName != null)
                return false;
        } else if (!cateName.equals(other.cateName))
            return false;
        if (costPrice == null) {
            if (other.costPrice != null)
                return false;
        } else if (!costPrice.equals(other.costPrice))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (goodId == null) {
            if (other.goodId != null)
                return false;
        } else if (!goodId.equals(other.goodId))
            return false;
        if (goodNum == null) {
            if (other.goodNum != null)
                return false;
        } else if (!goodNum.equals(other.goodNum))
            return false;
        if (image == null) {
            if (other.image != null)
                return false;
        } else if (!image.equals(other.image))
            return false;
        if (imageKey == null) {
            if (other.imageKey != null)
                return false;
        } else if (!imageKey.equals(other.imageKey))
            return false;
        if (isCaptcha == null) {
            if (other.isCaptcha != null)
                return false;
        } else if (!isCaptcha.equals(other.isCaptcha))
            return false;
        if (note == null) {
            if (other.note != null)
                return false;
        } else if (!note.equals(other.note))
            return false;
        if (orgId == null) {
            if (other.orgId != null)
                return false;
        } else if (!orgId.equals(other.orgId))
            return false;
        if (originalPrice == null) {
            if (other.originalPrice != null)
                return false;
        } else if (!originalPrice.equals(other.originalPrice))
            return false;
        if (price == null) {
            if (other.price != null)
                return false;
        } else if (!price.equals(other.price))
            return false;
        if (productCode == null) {
            if (other.productCode != null)
                return false;
        } else if (!productCode.equals(other.productCode))
            return false;
        if (productId == null) {
            if (other.productId != null)
                return false;
        } else if (!productId.equals(other.productId))
            return false;
        if (productName == null) {
            if (other.productName != null)
                return false;
        } else if (!productName.equals(other.productName))
            return false;
        if (productionDate == null) {
            if (other.productionDate != null)
                return false;
        } else if (!productionDate.equals(other.productionDate))
            return false;
        if (promotion == null) {
            if (other.promotion != null)
                return false;
        } else if (!promotion.equals(other.promotion))
            return false;
        if (rebackNote == null) {
            if (other.rebackNote != null)
                return false;
        } else if (!rebackNote.equals(other.rebackNote))
            return false;
        if (saleNum == null) {
            if (other.saleNum != null)
                return false;
        } else if (!saleNum.equals(other.saleNum))
            return false;
        if (saleStatus == null) {
            if (other.saleStatus != null)
                return false;
        } else if (!saleStatus.equals(other.saleStatus))
            return false;
        if (shipfeeConfig == null) {
            if (other.shipfeeConfig != null)
                return false;
        } else if (!shipfeeConfig.equals(other.shipfeeConfig))
            return false;
        if (sort == null) {
            if (other.sort != null)
                return false;
        } else if (!sort.equals(other.sort))
            return false;
        if (specNum == null) {
            if (other.specNum != null)
                return false;
        } else if (!specNum.equals(other.specNum))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        if (typeId == null) {
            if (other.typeId != null)
                return false;
        } else if (!typeId.equals(other.typeId))
            return false;
        if (unitName == null) {
            if (other.unitName != null)
                return false;
        } else if (!unitName.equals(other.unitName))
            return false;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        if (weight == null) {
            if (other.weight != null)
                return false;
        } else if (!weight.equals(other.weight))
            return false;
        if (vip == null){
            if (other.vip != null)
                return false;
        } else if (!vip.equals(other.vip))
            return false;
        
        return true;
    }


    
}
