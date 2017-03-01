/*
 * Project Name: wrw-admin
 * File Name: ActivitySpecService.java
 * Class Name: ActivitySpecService
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
package com.hengtiansoft.business.activity.service;

import java.math.BigInteger;
import java.util.List;

import com.hengtiansoft.business.activity.dto.ActivityStockDto;
import com.hengtiansoft.wrw.entity.ActivitySpec;
import com.hengtiansoft.wrw.entity.ActivityStock;

/**
 * Class Name: ActivitySpecService
 * Description: 活动规格
 * @author zhongyidong
 *
 */
public interface ActivitySpecService {
    
    /**
     * Description: 保存活动规格
     *
     * @param actStockDto
     * @param subSpecList
     * @param actStock
     * @param userId
     * @return
     */
    public List<ActivitySpec> saveStock(ActivityStockDto actStockDto, List<String> subSpecList, ActivityStock actStock, Long userId);
    
    /**
     * Description: 查询规格信息
     *
     * @param actStockId
     * @return
     */
    public List<ActivitySpec> findByStockId(Long actStockId);
    
    /**
     * Description: 查询商品库存以及子规格（第一个主规格下的子规格）对应的记录id
     *
     * @param actStockId
     * @param subSpec
     * @return
     */
    public Long findIdByStockIdAndSpec(Long actStockId, String subSpec);
    
    /**
     * Description: 删除库存对应的商品规格库存记录
     *
     * @param actStockIdList
     * @return
     */
    public Integer deleteByStockId(List<BigInteger> actStockIdList);
    
    /**
     * Description: 删除规格对应的商品库存记录
     *
     * @param actStockId
     * @param subSpecList
     * @return
     */
    public void deleteByStockIdAndSpec(Long actStockId, List<String> subSpecList);
    
    /**
     * Description: 查询人数主规格下的子规格（去重）
     *
     * @param productId
     * @return
     */
    public List<ActivitySpec> findFirstSubspecs(Long productId);
    
}
