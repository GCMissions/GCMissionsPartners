package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.PFloor;

public interface PFloorDao extends JpaRepository<PFloor, Long>, JpaSpecificationExecutor<PFloor> {

    @Query(value = "SELECT a.FLOOR_TYPE,a.PRODUCT_ID from P_FLOOR a LEFT JOIN P_FLOOR b ON a.FLOOR_TYPE = b.FLOOR_TYPE AND a.PRODUCT_ID >= b.PRODUCT_ID GROUP BY a.FLOOR_TYPE,a.PRODUCT_ID HAVING COUNT(b.PRODUCT_ID) <= 8", nativeQuery = true)
    List<Object[]> findIndexFloorProduct();

    @Query("select p from PFloor p where p.productId =?1 and p.regionId in (?2)")
    List<PFloor> findByProductIdAndRegionIds(Long productId, List<Integer> regionIds);

    @Query("select p from PFloor p where p.regionId =?1 and p.floorType =?2 and p.status =?3 ")
    List<PFloor> findProductIdByRegionIdAndFloorType(Integer regionId, String floorType, String status);

    @Modifying
    @Query("delete PFloor p  where p.floorType =?1 and p.regionId =?2")
    void updateByFloorTypeAndRegionId(String floorType, Integer regionId);

    /**
     * 
    * Description: 通过楼层ID和区域ID来筛选商品
    *
    * @param code
    * @param floor
    * @param productIds
    * @param regionId
    * @return
     */
    @Query("select p.productId from PFloor p where p.status =?1 and p.floorType =?2 and p.regionId =?4 and p.productId in (?3) order by p.sort asc ")
    List<Long> findByFloorTypeAndRegionIdAndProductIds(String code, String floor, List<Long> productIds, Integer regionId);

}
