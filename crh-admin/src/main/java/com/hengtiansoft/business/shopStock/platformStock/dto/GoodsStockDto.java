/*
 * Project Name: wrw-admin
 * File Name: ProductStockSearchRecordDto.java
 * Class Name: ProductStockSearchRecordDto
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
package com.hengtiansoft.business.shopStock.platformStock.dto;

import java.io.Serializable;

/**
* Class Name: ProductStockSearchRecordDto
* Description: TODO
* @author xianghuang
*
*/
public class GoodsStockDto implements Serializable {
    
    private static final long serialVersionUID = 2739535790856028250L;

    //库存Id
    private Long stockId;

    //物料Id
    private Long goodsId;
    
    //物料编号
    private String goodsCode;
    
    //物料名称
    private String goodsName;
    
    //销售价
    private String price;
    
    //现有库存
    private Long productStockNum;
    
    //物料创建时间
    private String createDate;
    
    //安全库存设置
    private Long safeStockSetting;
    
    //标准库存设置
    private Long standardStockSetting;
    
    //发货数量
    private Long shipmentNum;
    
    //库存设置
    private Long stockSetting;
    
    //库存状态
    private String orgState;

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getProductStockNum() {
        return productStockNum;
    }

    public void setProductStockNum(Long productStockNum) {
        this.productStockNum = productStockNum;
    }

    public Long getSafeStockSetting() {
        return safeStockSetting;
    }

    public void setSafeStockSetting(Long safeStockSetting) {
        this.safeStockSetting = safeStockSetting;
    }

    public Long getStandardStockSetting() {
        return standardStockSetting;
    }

    public void setStandardStockSetting(Long standardStockSetting) {
        this.standardStockSetting = standardStockSetting;
    }

    public Long getShipmentNum() {
        return shipmentNum;
    }

    public void setShipmentNum(Long shipmentNum) {
        this.shipmentNum = shipmentNum;
    }

    public Long getStockSetting() {
        return stockSetting;
    }

    public void setStockSetting(Long stockSetting) {
        this.stockSetting = stockSetting;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getOrgState() {
        return orgState;
    }

    public void setOrgState(String orgState) {
        this.orgState = orgState;
    }
    
    
}
