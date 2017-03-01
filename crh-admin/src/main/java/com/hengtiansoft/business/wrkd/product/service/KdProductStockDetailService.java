/*
 * Project Name: wrw-admin
 * File Name: KdProductStockDetailService.java
 * Class Name: KdProductStockDetailService
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

import com.hengtiansoft.business.wrkd.product.dto.KdProductStockDetailDto;
import com.hengtiansoft.wrw.entity.KdProductStockDetailEntity;

/**
 * Class Name: KdProductStockDetailService
 * Description: 酷袋商品库存详情
 * @author zhongyidong
 *
 */
public interface KdProductStockDetailService {
    
    /**
     * Description: 查询库存详情
     *
     * @param stockId
     * @return
     */
    List<KdProductStockDetailDto> findByStockId(Long stockId);
    
    
    /**
     * Description: 保存库存详情
     *
     * @param stockId
     * @param stockDetailDtos
     * @return
     */
    List<KdProductStockDetailEntity> saveStockDetail(Long stockId, List<KdProductStockDetailDto> stockDetailDtos);
}
