package com.hengtiansoft.business.product.service;

import com.hengtiansoft.business.product.dto.PGoodsDto;
import com.hengtiansoft.business.product.dto.PGoodsSearchDto;
import com.hengtiansoft.business.product.dto.ProductSearchDto;
import com.hengtiansoft.common.dto.ResultDto;

public interface GoodsService {

    /**
     * Description: 新增物料信息
     *
     * @param dto
     * @return
     */
    ResultDto<String> saveGoods(PGoodsDto dto);

    /**
     * Description: 更新物料信息
     *
     * @param dto
     * @return
     */
    ResultDto<String> updateGoods(PGoodsDto dto);

    /**
     * Description: 根据物料ID查出关联此物料的所有商品数据
     *
     * @param dto
     * @return
     */
    void findProducts(ProductSearchDto dto);

    /**
     * Description: 根据关键字、名称、日期等条件搜索物料
     *
     * @param searchDto
     * @return
     */
    void searchGoods(PGoodsSearchDto dto);

    /**
     * Description: 根据goodsId查询物料信息
     *
     * @param goodsId
     * @return
     */
    PGoodsDto findByGoodsId(Long goodsId);

}
