/*
 * Project Name: wrw-admin
 * File Name: KdPSpecService.java
 * Class Name: KdPSpecService
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
import java.util.Map;

import com.hengtiansoft.business.wrkd.product.dto.KdProductSpecDto;
import com.hengtiansoft.business.wrkd.product.dto.KdProductStockDetailDto;

/**
 * Class Name: KdPSpecService
 * Description: 酷袋商品规格
 * @author zhongyidong
 *
 */
public interface KdProductSpecService {

    /**
     * Description: 查询商品规格信息
     *
     * @param productId
     * @return
     */
    List<KdProductSpecDto> findSpecInfo(Long productId);
    
    
    /**
     * Description: 查询商品价格信息
     *
     * @param productId
     * @return
     */
    List<KdProductStockDetailDto> findPriceDetail(Long productId);
    
    /**
     * 
    * Description: 通过商品id获取规格商品对应价格的map数据
    *
    * @param productId
    * @return
    * @author chengchaoyin
     */
    Map<String,Long> findPriceMapDetail(Long productId);
    
    /**
     * 
    * Description: 通过商品id获取规格信息
    *   key:主规格名称；value:子规格集合
    * @param productId
    * @return
    * @author chengchaoyin
     */
    Map<String,List<String>> getProductSpecMapById(Long productId);
    
}
