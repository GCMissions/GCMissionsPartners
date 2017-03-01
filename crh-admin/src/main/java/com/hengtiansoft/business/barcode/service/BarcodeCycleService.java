package com.hengtiansoft.business.barcode.service;

import com.hengtiansoft.business.barcode.dto.BarcodeCycleSearchDto;

/**
 * 
* Class Name: BarcodeCycleService
* Description: TODO
* @author hongqi
*
 */
public interface BarcodeCycleService {


    /**
     * 查询二维码周期,通过prefixCode
     * @param dto 查询的对象
     */
    void searchByPrefixCode(final BarcodeCycleSearchDto dto);
    /**
     * 查询二维码周期,通过Url
     * @param dto 查询的对象
     */
    void searchByUrl(final BarcodeCycleSearchDto dto);

}
