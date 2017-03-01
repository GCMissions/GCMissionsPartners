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
 * 
 * Class Name: MOrderDetailEntity Description: 会员订单商品明细
 * 
 * @author chengchaoyin
 *
 */
@Entity
@Table(name = "M_ORDER_DETAIL")
public class MOrderDetailEntity implements Serializable {

    private static final long serialVersionUID = -425188932299918009L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DETAIL_ID")
    private Long              detailId;

    @Column(name = "ORDER_ID")
    private String            orderId;

    @Column(name = "GOOD_ID")
    private Long              goodId;

    @Column(name = "PRODUCT_ID")
    private Long              productId;

    @Column(name = "ACT_STOCK_ID")
    private Long              actStockId;
    
    @Column(name = "ACT_SPEC_ID")
    private Long              actSpecId;
    
    @Column(name = "ACT_DATE")
    private Date              actDate;

    @Column(name = "PRODUCT_NAME")
    private String            productName;

    @Column(name = "UNIT")
    private String            unit;

    @Column(name = "PRICE")
    private Long              price;

    @Column(name = "NUM")
    private Integer           num;
    
    @Column(name = "GOOD_NUM")
    private Long              goodNum;

    @Column(name = "AMOUNT")
    private Long              amount;

    @Column(name = "SPEC_INFO")
    private String            specInfo;
    
    @Column(name = "PREFIX_CODE")
    private String            prefixCode;
    
    @Column(name = "VERIFICATION_CODE")
    private String            verificationCode;
    
    @Column(name = "CODE_USED")
    private String            codeUsed;
    
    /**
     * 备注
     */
    @Column(name = "REMARK")
    private String remark;
    
    /**
     * 状态
     */
    @Column(name = "STATUS")
    private String status;

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
    

    public Long getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(Long goodNum) {
        this.goodNum = goodNum;
    }

    
    public String getPrefixCode() {
        return prefixCode;
    }

    public void setPrefixCode(String prefixCode) {
        this.prefixCode = prefixCode;
    }

    @Override
    public String toString() {
        return "MOrderDetailEntity [detailId=" + detailId + ", orderId=" + orderId + ", goodId=" + goodId + ", "
                + "productId=" + productId + ", actStockId=" + actStockId + ", actSpecId=" + actSpecId + ", "
                + "productName=" + productName + ", unit=" + unit + ", price=" + price + ", num=" + num + ", goodNum="
                + goodNum + ", amount=" + amount + ", prefixCode=" + prefixCode + "]";
    }

    public Long getActStockId() {
        return actStockId;
    }

    public void setActStockId(Long actStockId) {
        this.actStockId = actStockId;
    }

    public Long getActSpecId() {
        return actSpecId;
    }

    public void setActSpecId(Long actSpecId) {
        this.actSpecId = actSpecId;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getCodeUsed() {
        return codeUsed;
    }

    public void setCodeUsed(String codeUsed) {
        this.codeUsed = codeUsed;
    }

    public Date getActDate() {
        return actDate;
    }

    public void setActDate(Date actDate) {
        this.actDate = actDate;
    }

    public String getSpecInfo() {
        return specInfo;
    }

    public void setSpecInfo(String specInfo) {
        this.specInfo = specInfo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
