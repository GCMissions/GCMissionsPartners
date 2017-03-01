/*
 * Project Name: wrw-common
 * File Name: KProductDao.java
 * Class Name: KProductDao
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

import com.hengtiansoft.wrw.entity.KdProductEntity;

/**
 * Class Name: KProductDao
 * Description: 酷袋商品
 * @author zhongyidong
 *
 */
public interface KdProductDao extends JpaRepository<KdProductEntity, Long> {
    
    /**
     * Description: 根据商品名称查询商品数量
     *
     * @param pname
     * @parem isDeleted
     * @return
     */
    @Query(value = "select count(1) from kd_p_product where pname = ?1 and is_deleted = ?2", nativeQuery = true)
    int findProductByName(String pname, String isDeleted);
    
    /**
     * Description: 删除商品
     *
     * @param productIds
     * @param flag
     * @param userId
     * @return
     */
    @Modifying
    @Query(value = "update kd_p_product set is_deleted = ?2, modify_id = ?3, modify_date=now() where id in ?1", nativeQuery = true)
    int deleteProduct(List<Long> productIds, String isDeleted, Long userId);
    
    /**
     * Description: 添加商品的价格区间
     *
     * @param productIds
     * @param lowPrice
     * @param hignPrice
     * @param lowVipPrice
     * @param highVipPrice
     * @return
     */
    @Modifying
    @Query(value = "update kd_p_product set low_price =?2, high_price=?3, low_vip_price=?4, high_vip_price=?5 where id = ?1", nativeQuery = true)
    int updatePriceRange(Long productId, Integer lowPrice, Integer hignPrice, Integer lowVipPrice, Integer highVipPrice);
    
    /**
     * Description: 保存商品详情
     *
     * @param productId
     * @param detailDesc
     * @return
     */
    @Modifying
    @Query(value = "update kd_p_product set detail_desc =?2 where id = ?1", nativeQuery = true)
    int saveDetailDesc(Long productId, String detailDesc);
    
}
