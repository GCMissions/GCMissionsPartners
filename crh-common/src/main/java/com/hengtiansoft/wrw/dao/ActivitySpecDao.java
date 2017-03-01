/*
 * Project Name: wrw-common
 * File Name: ActivitySpecDao.java
 * Class Name: ActivitySpecDao
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
package com.hengtiansoft.wrw.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.ActivitySpec;

/**
 * Class Name: ActivitySpecDao
 * Description: 活动规格
 * @author zhongyidong
 *
 */
public interface ActivitySpecDao extends JpaRepository<ActivitySpec, Long> {
    
    
    /**
     * Description: 查询商品库存对应的规格信息
     *
     * @param actStockId
     * @return
     */
    @Query(value = "select * from act_spec where act_stock_id = ?1", nativeQuery = true)
    List<ActivitySpec> findByStockId(Long actStockId);
    
    /**
     * Description: 查询商品库存对应的记录id
     *
     * @param actStockId
     * @return
     */
    @Query(value = "select * from act_spec where act_stock_id = ?1 and sub_spec =?2", nativeQuery = true)
    Long findIdByStockIdAndSpec(Long actStockId, String subSpec);
    
    /**
     * Description: 删除库存对应的商品规格库存记录
     *
     * @param actStockId
     * @param subSpec
     * @return
     */
    @Modifying
    @Query(value = "delete from act_spec where act_stock_id in ?1", nativeQuery = true)
    int deleteByStockId(List<BigInteger> actStockIdList);
    
    /**
     * Description: 统计需要删除的规格对应的商品规格库存记录
     *
     * @param actStockId
     * @param subSpecList
     * @return
     */
    @Query(value = "select count(*) from act_spec where act_stock_id = ?1 and sub_spec not in ?2", nativeQuery = true)
    int countByStockIdAndSpec(Long actStockId, List<String> subSpecList);
    
    /**
     * Description: 删除规格对应的商品规格库存记录
     *
     * @param actStockId
     * @param subSpecList
     * @return
     */
    @Modifying
    @Query(value = "delete from act_spec where act_stock_id = ?1 and sub_spec not in ?2", nativeQuery = true)
    int deleteByStockIdAndSpec(Long actStockId, List<String> subSpecList);
    
    @Modifying
    @Query(value = "update act_spec set TOTAL =(TOTAL + ?2) where  id = ?1 " ,nativeQuery=true)
    void updateActSpecStock(Long specId,Integer totalNum);
    
    @Query(value = "select * from act_spec where act_stock_id = ?1 and main_spec=?2 and sub_spec=?3", nativeQuery = true)
    ActivitySpec getSubSpecStock(Long actStockId,String mainSpec,String subSpec);
    
    /**
     * Description:查询人数主规格下的子规格（去重）
     *
     * @param productId
     * @return
     */
    @Query(value = "select a.* from act_spec as a left join act_stock as b on a.act_stock_id = b.id "
            + "where b.product_id = ?1 group by a.sub_spec", nativeQuery = true)
    List<ActivitySpec> findFirstSubspecs(Long productId);
}
