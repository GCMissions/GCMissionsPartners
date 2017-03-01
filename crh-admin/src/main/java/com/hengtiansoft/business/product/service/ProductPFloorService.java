package com.hengtiansoft.business.product.service;


import java.util.List;

import com.hengtiansoft.business.product.dto.ProductFloorSaveDto;
import com.hengtiansoft.business.product.dto.ProductFloorSearchDto;
import com.hengtiansoft.business.product.dto.ProductPageSearchDto;
import com.hengtiansoft.common.dto.ResultDto;

/**
 * 
* Class Name: ProductPFloorService
* Description: 楼层管理业务类
* @author zhisongliu
*
 */
public interface ProductPFloorService {
	
	/**
	 * 
	 * 通过 regionId 和楼层ID 来检索该地区在该楼层内的所有上架商品
	 * 
	 * @param dto
	 * @return
	 */
	ProductFloorSaveDto  findByRegionIdAndfloorType(ProductFloorSearchDto dto);
	
	/**
	 * 
	 * 楼层设置保存。
	 * 
	 * @param dto
	 * @return
	 */
	ResultDto<String> save(ProductFloorSaveDto dto);
	
	
	/**
	 * regionID,productCode,productName  检索某个地区内所有上架的商品
	 * 
	 * 
	 * @param dto
	 */
	void search(ProductPageSearchDto dto,List<Long> productIds);
	
	/**
	 * 
	* Description: 查询出某个地区内的所有商品id
	*
	* @param regionId
	* @return
	 */
	List<Long> findByRegionId(Integer regionId);

}
