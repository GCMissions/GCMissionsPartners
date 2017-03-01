/*
 * Project Name: wrw-admin
 * File Name: KPStockService.java
 * Class Name: KPStockService
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
package com.hengtiansoft.business.wrkd.product.service;

import com.hengtiansoft.business.wrkd.product.dto.KdProductStockDto;
import com.hengtiansoft.common.dto.ResultDto;

/**
 * Class Name: KPStockService
 * Description: 酷袋商品库存
 * @author zhongyidong
 *
 */
public interface KdProductStockService {
    
    /**
     * Description: 查询商品库存总体信息
     *
     * @param productId
     * @return
     */
    KdProductStockDto findByProductId(Long productId);
    
    /**
     * Description: 查询商品库存详细信息
     *
     * @param productId
     * @return
     */
    KdProductStockDto findDetailByProductId(Long productId);
    
    /**
     * Description: 保存商品库存信息
     *
     * @param stockDto
     * @return
     */
    ResultDto<Long> saveStock(KdProductStockDto stockDto);
    
}
