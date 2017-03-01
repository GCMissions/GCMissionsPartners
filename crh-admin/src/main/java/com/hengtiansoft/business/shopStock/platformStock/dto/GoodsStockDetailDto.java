/*
 * Project Name: wrw-admin
 * File Name: ProductStockDetailDto.java
 * Class Name: ProductStockDetailDto
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

import java.util.List;

/**
* Class Name: ProductStockDetailDto
* Description: 商品库存详情Bean
* @author xianghuang
*
*/
public class GoodsStockDetailDto {
    //物料编码
    private String goodsCode;
    //物料名称
    private String goodsName;
    //销售价
    private String price;
    //库存总量
    private Long stockNum;
    List<OrgEntityTreeDto> treeList;
   
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

    public Long getStockNum() {
        return stockNum;
    }

    public void setStockNum(Long stockNum) {
        this.stockNum = stockNum;
    }

    public List<OrgEntityTreeDto> getTreeList() {
        return treeList;
    }

    public void setTreeList(List<OrgEntityTreeDto> treeList) {
        this.treeList = treeList;
    }

}

