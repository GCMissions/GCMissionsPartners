/*
 * Project Name: wrw-admin
 * File Name: StockService.java
 * Class Name: StockService
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
package com.hengtiansoft.business.shopStock.platformStock.service;


import com.hengtiansoft.business.shopStock.platformStock.dto.GoodsStockDetailDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.GoodsStockSearchDto;

/**
* Class Name: StockService
* Description: TODO
* @author xianghuang
*
*/
public interface ProductStockService {
    
    /**
    * Description: 查询商品列表
    *
    * @param searchDto
    * @return
    */
    GoodsStockSearchDto searchProductStock(GoodsStockSearchDto searchDto);
    
    /**
    * Description: 查询单个商品详情
    *
    * @param productId
    * @return
    */
    GoodsStockDetailDto findOrgTreeAndStockNumDetail(Long productId);
}
