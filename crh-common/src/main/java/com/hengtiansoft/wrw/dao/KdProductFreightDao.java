/*
 * Project Name: wrw-common
 * File Name: KPShiefRepository.java
 * Class Name: KPShiefRepository
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

import com.hengtiansoft.wrw.entity.KdProductFreightEntity;

/**
 * Class Name: KPShiefRepository
 * Description: 酷袋商品运费
 * @author zhongyidong
 *
 */
public interface KdProductFreightDao extends JpaRepository<KdProductFreightEntity, Long> {
    
    /**
     * Description: 查询商品所有地区的运费信息
     *
     * @param productId
     * @param regionId
     * @return
     */
    @Query(value = "select * from kd_p_freight where product_id = ?1", nativeQuery = true)
    List<KdProductFreightEntity> findByProductId(Long productId);
    
    /**
     * Description: 查询商品某个地区的运费信息
     *
     * @param productId
     * @param regionId
     * @return
     */
    @Query(value = "select * from kd_p_freight where product_id = ?1 and region_id = ?2", nativeQuery = true)
    KdProductFreightEntity findByRegionId(Long productId, Long regionId);
    
    /**
     * Description: 查询商品某个地区的运费信息
     *
     * @param productId
     * @return
     */
    @Modifying
    @Query(value = "delete from kd_p_freight where product_id = ?1", nativeQuery = true)
    int deleteByProductId(Long productId);

}
