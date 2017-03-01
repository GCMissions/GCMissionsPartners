/*
 * Project Name: wrw-common
 * File Name: KPStockRepositoy.java
 * Class Name: KPStockRepositoy
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

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.KdProductStockEntity;

/**
 * Class Name: KPStockRepositoy
 * Description: 酷袋商品库存
 * @author zhongyidong
 *
 */
public interface KdProductStockDao extends JpaRepository<KdProductStockEntity, Long> {

    /**
     * Description: 根据商品id查询库存
     *
     * @param productId
     * @return
     */
    @Query(value = "select * from kd_p_stock where product_id = ?1", nativeQuery = true)
    KdProductStockEntity findByProductId(Long productId);
    
    /**
     * Description: 修改库存量
     *
     * @param productId
     * @param count
     * @param userId
     * 
     */
    @Modifying
    @Query(value = "update kd_p_stock set REST_AMOUNT = REST_AMOUNT + ?2,MODIFY_DATE=now(),MODIFY_ID=?3 where product_id = ?1", nativeQuery = true)
    void updateRestAmount(Long productId, Integer count, Long userId);
}
