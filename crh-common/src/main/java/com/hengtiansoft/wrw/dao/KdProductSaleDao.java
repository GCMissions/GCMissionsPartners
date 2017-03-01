/*
 * Project Name: wrw-common
 * File Name: KdProductSaleDao.java
 * Class Name: KdProductSaleDao
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

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.KdProductSaleEntity;

/**
 * Class Name: KdProductSaleDao
 * Description: 酷袋商品上下架
 * @author zhongyidong
 *
 */
public interface KdProductSaleDao extends JpaRepository<KdProductSaleEntity, Long> {
    
    /**
     * Description: 查询商品上下架信息
     *
     * @param productId
     * @return
     */
    @Query(value = "select * from kd_p_sale where product_id = ?1", nativeQuery = true)
    KdProductSaleEntity findByProductId(Long productId);
    
    /**
     * Description: 修改上下架状态
     *
     * @param productId
     * @param saleStatus
     * @param userId
     * @return
     */
    @Modifying
    @Query(value = "update kd_p_sale set sale_status =?2, on_sale_time = null, off_sale_time = null, "
            + "modify_id=?3, modify_date = now() where product_id = ?1", nativeQuery = true)
    int updateSaleStatus(Long productId, String saleStatus, Long userId);
    
    /**
     * Description: 保存上架操作信息
     *
     * @param productId
     * @param saleStatus
     * @param onDate
     * @param offDate
     * @param userId
     * @return
     */
    @Modifying
    @Query(value = "update kd_p_sale set sale_status =?2, on_sale_time=?3, off_sale_time=?4, modify_id=?5, "
            + "modify_date=now() where product_id = ?1", nativeQuery = true)
    int saveSaleInfo(Long productId, String saleStatus, Date onDate, Date offDate, Long userId);
    
    /**
     * Description: 查询需要上架商品（脚本）
     *
     * @param saleStatus
     * @return
     */
    @Query(value = "SELECT A.* from kd_p_sale AS A INNER JOIN kd_p_product AS B ON A.product_id = B.id "
            + " WHERE B.is_deleted = '0' AND A.sale_status = ?1 "
            + " AND A.on_sale_time IS NOT NULL AND A.on_sale_time <= NOW() ", nativeQuery = true)
    List<KdProductSaleEntity> putOnSale(String saleStatus);

    /**
     * Description: 查询需要下架的商品（脚本）
     *
     * @param saleStatus
     * @return
     */
    @Query(value = "SELECT A.* FROM kd_p_sale AS A INNER JOIN kd_p_product AS B ON A.product_id = B.id "
            + " WHERE B.is_deleted = '0' AND A.sale_status = ?1 "
            + " AND A.off_sale_time IS NOT NULL AND A.off_sale_time <= NOW() ", nativeQuery = true)
    List<KdProductSaleEntity> findOffSaleingProducts(String saleStatus);
    
    /**
     * Description: 商品下架（脚本）
     *
     * @param productIds
     * @param saleStatus
     * @param userId
     * @return
     */
    @Modifying
    @Query(value = "update kd_p_sale set sale_status=?2, modify_id=?3, modify_date=now() where product_id in ?1", nativeQuery = true)
    int putOffSale(List<Long> productIds, String saleStatus, Long userId);
    
    /**
     * Description: 计算某个状态的商品数量
     *
     * @param productIds
     * @param saleStatus
     * @return
     */
    @Query(value = "select count(1) from kd_p_sale where product_id in ?1 and sale_status= ?2", nativeQuery = true)
    int countBySaleStatus(List<Long> productIds, String saleStatus);
    
}
