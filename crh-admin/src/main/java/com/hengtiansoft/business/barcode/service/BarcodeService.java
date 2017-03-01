package com.hengtiansoft.business.barcode.service;

import java.util.List;

import com.hengtiansoft.business.barcode.dto.BarcodeCheckDto;
import com.hengtiansoft.business.barcode.dto.BarcodeDto;
import com.hengtiansoft.business.barcode.dto.BarcodePageDto;
import com.hengtiansoft.business.barcode.dto.BarcodeStorageDto;
import com.hengtiansoft.common.dto.ResultDto;

public interface BarcodeService {

    ResultDto<String> storageCode(BarcodeStorageDto sdto);

    /**
     * Description: 通过订单ID查询出该订单的物料信息及总数
     *
     * @param orderId
     * @return
     */
    ResultDto<BarcodePageDto> findByOrderId(String orderId, String status);

    /**
     * Description: 验证单条URL是否正确
     *
     * @param dto
     * @return
     */
    ResultDto<BarcodeCheckDto> checkUrl(BarcodeDto dto);

    ResultDto<Integer> checkCode(String prefixCode);

    void saveBarcodeCycle(List<String> sbList, BarcodeStorageDto sdto);

}
