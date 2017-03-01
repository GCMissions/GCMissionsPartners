/*
 * Project Name: wrw-admin
 * File Name: KdProductSaleService.java
 * Class Name: KdProductSaleService
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

import java.util.List;

import com.hengtiansoft.business.wrkd.product.dto.KdProductSaleDto;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.wrw.entity.KdProductSaleEntity;

/**
 * Class Name: KdProductSaleService
 * Description: 酷袋商品上下架
 * @author zhongyidong
 *
 */
public interface KdProductSaleService {

    /**
     * Description: 检查商品信息是否完整
     *
     * @param productId
     * @return
     */
    ResultDto<String> checkProductInfo(Long productId);
    
    /**
     * Description: 商品上架
     *
     * @param productDto
     * @return
     */
    ResultDto<String> putOnSale(KdProductSaleDto saleDto);

    /**
     * Description: 商品下架
     *
     * @param productId
     * @return
     */
    ResultDto<String> putOffSale(Long productId);
    
    /**
     * Description: 插入商品信息
     *
     * @param productId
     * @param userId
     * 
     * @return
     */
    KdProductSaleEntity saveSaleInfo(Long productId, Long userId);
    
    /**
     * Description: 检查是否有已上架的商品
     *
     * @param productIds
     * @return
     */
    Boolean checkSaleStatus(List<Long> productIds);
}
