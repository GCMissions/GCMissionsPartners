/*
 * Project Name: wrw-admin
 * File Name: ProductStockSearchDto.java
 * Class Name: ProductStockSearchDto
 * Copyright 2014 Hengtian Software Inc
 * Licensed under the Hengtiansoft
 * http://www.hengtiansoft.com
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.business.shopStock.platformStock.dto;

import com.hengtiansoft.common.dto.PagingDto;

/**
 * Class Name: ProductStockSearchDto
 * Description: TODO
 * 
 * @author xianghuang
 */
public class GoodsStockSearchDto extends PagingDto<GoodsStockDto> {

    private static final long serialVersionUID = 1325346734937635856L;

    // 物料Id
    private Long              goodsId;

    // 物料名称
    private String            goodsName;

    // 物料编码
    private String            goodsCode;

    // 销售价
    private String            price;

    // 商家Id
    private Long              orgId;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

}
