package com.hengtiansoft.business.order.servicer;

import com.hengtiansoft.business.order.dto.RegisterRequestDto;
import com.hengtiansoft.wrw.entity.MMemberEntity;

/**
* Class Name: RegisterService
* Description: 注册相关业务
* @author changchen
*
*/
public interface RegisterService {

    /**
    * Description: 注册进系统
    *
    * @param requestDto
     * @return 
    */
    MMemberEntity register(RegisterRequestDto requestDto);
    
}
