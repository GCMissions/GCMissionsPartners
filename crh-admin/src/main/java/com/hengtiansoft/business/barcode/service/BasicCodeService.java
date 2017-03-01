package com.hengtiansoft.business.barcode.service;

import java.io.InputStream;
import java.util.List;

import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.wrw.entity.SBasicCodeEntity;

/**
 * 
* Class Name: BasicCodeService
* Description: TODO
* @author hongqi
*
 */
public interface BasicCodeService {
    /**
     * 
    * Description: 导入二维码数据
    *
    * @param inputStream execl 的输入流
    * @return 返回导入结果
     */
    ResultDto<List<SBasicCodeEntity>> importBarcode(InputStream inputStream);
}
