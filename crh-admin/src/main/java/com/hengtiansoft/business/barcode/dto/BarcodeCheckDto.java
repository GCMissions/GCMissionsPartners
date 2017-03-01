package com.hengtiansoft.business.barcode.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Class Name: BarcodeCheckDto
 * Description: 扫码验证完之后DTO信息
 * 
 * @author zhisongliu
 */
public class BarcodeCheckDto implements Serializable {

    private static final long serialVersionUID = 4225910517546799870L;

    private Long              goodId;

    private Integer           goodSelectNum    = 1;                   // 该条码所包含的子码数量，默认当前码为子码，如果客户这边扫码的是母码，则goodSelectNum
                                                                       // = 6;

    private String            goodName;

    private List<String>      prefixCode;                             // 当前二维码中的明码

    private List<String>      codeUrl;                                // 当前二维码中的URL 若是母码，则传6个子码URL。若是子码，则就是子码URL。

    private String            packCode;                               // 如果扫描的是母码，则packCode有值，如果扫描的是子码，则packCode没值

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public Integer getGoodSelectNum() {
        return goodSelectNum;
    }

    public void setGoodSelectNum(Integer goodSelectNum) {
        this.goodSelectNum = goodSelectNum;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public List<String> getPrefixCode() {
        return prefixCode;
    }

    public void setPrefixCode(List<String> prefixCode) {
        this.prefixCode = prefixCode;
    }

    public List<String> getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(List<String> codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getPackCode() {
        return packCode;
    }

    public void setPackCode(String packCode) {
        this.packCode = packCode;
    }

}
