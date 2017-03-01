/*
 * Project Name: wrw-common
 * File Name: KPStockDetailRepository.java
 * Class Name: KPStockDetailRepository
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

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.KdProductStockDetailEntity;

/**
 * Class Name: KPStockDetailRepository
 * Description: 酷袋商品库存详情
 * @author zhongyidong
 *
 */
public interface KdProductStockDetailDao extends JpaRepository<KdProductStockDetailEntity, Long> {
    
    /**
     * Description: 查询库存详情
     *
     * @param stockId
     * @param is_deleted
     * @return
     */
    @Query(value = "select * from kd_p_stock_detail where stock_id = ?1 and is_deleted =?2 order by id asc", nativeQuery = true)
    List<KdProductStockDetailEntity> findByStockId(Long stockId, String isDeleted);
    
    /**
     * Description: 删除规格
     *
     * @param stockId
     * @param specGroups
     * @param isDeleted
     * @return
     */
    @Modifying
    @Query(value = "update kd_p_stock_detail set is_deleted =?3 where stock_id = ?1 and spec_group not in ?2", nativeQuery = true)
    int deleteSpecGroup(Long stockId, List<String> specGroups, String isDeleted);
    
    /**
     * Description: 根据stockId和规格查询库存详情
     *
     * @param stockId
     * @param specGroup
     * @param isDeleted
     * @return
     */
    @Query(value = "select * from kd_p_stock_detail where stock_id = ?1 and spec_group = ?2 and is_deleted = ?3", nativeQuery = true)
    KdProductStockDetailEntity findBySpecGroup(Long stockId, String specGroup, String isDeleted);
    
    
    /**
     * Description:
     *
     * @param specGroup
     * @return
     */
    @Query(value = "select detail.* from kd_p_product p " + "join kd_p_stock stock on p.ID=stock.PRODUCT_ID "
            + "join kd_p_stock_detail detail on stock.ID = detail.STOCK_ID "
            + "where p.ID = ?1 and detail.SPEC_GROUP = ?2 and detail.is_deleted = 0 ", nativeQuery = true)
    List<KdProductStockDetailEntity> findBySpecGroup(Long productId, String specGroup);
    
    
    @Query(value = "select * from kd_p_stock_detail where stock_id = ?1 and spec_group = ?2 order by id desc limit 1", nativeQuery = true)
    KdProductStockDetailEntity findByStockIdAndSpecGroup(Long stockId, String specGroup);
    
    @Modifying
    @Query(value = "update kd_p_stock_detail set rest_amount = rest_amount + ?1,modify_date=now(),modify_id=?3 where id = ?2", nativeQuery = true)
    void addRestAmount(Integer count, Long id, Long userId);
    
    @Query(value = "select t.id,max(d.price),min(d.price)  from kd_p_stock s , kd_p_stock_detail d,kd_team_buy_product t where t.id in ?1 and s.id = d.stock_id and d.is_deleted = '0' "
            + "and d.spec_group in ?2 and s.product_id = t.product_id and t.status in ?3 GROUP BY t.id", nativeQuery = true)
    List<Object[]> findAllByProductIdsAndSpecGroup(List<Long> listProductId, List<String> specNewList,List<String> stutas);
    
    /**
     * 
    * Description: 获取该商品库存下，所有规格的库存
    *
    * @param id
    * @return
     */
    @Query(value ="select sum(d.REST_AMOUNT) from kd_p_stock_detail d where d.STOCK_ID  = ?1 and d.IS_DELETED = 0 " ,nativeQuery=true)
    Integer findCountByStockId(Long id);
    
    /**
     * 
    * Description: 获取该商品库存下，部分规格的库存
    *
    * @param id
    * @param productSpec
    * @return
     */
    @Query(value ="select sum(d.REST_AMOUNT) from kd_p_stock_detail d where d.STOCK_ID  = ?1 and d.IS_DELETED = 0 and d.SPEC_GROUP in (?2) ",nativeQuery =true)
    Integer findSumSpecByStockIdAndSomeSpecInfos(Long id, List<String> productSpec);
}
