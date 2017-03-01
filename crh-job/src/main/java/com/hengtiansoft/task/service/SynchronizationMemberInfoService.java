package com.hengtiansoft.task.service;

import java.util.List;

import com.hengtiansoft.task.dto.SimpleMemberInfoDto;

/**
* Class Name: SynchronizationMemberInfoService
* Description: TODO
* @author changchen
*
*/
public interface SynchronizationMemberInfoService {
    
    
    /**
    * Description: 获取原始数据
    *
    * @return
    */
    List<SimpleMemberInfoDto> findSourceData(long begin);
    
    

    /**
    * Description: 批量保存用户信息
    *
    * @param list
    * @return
    */
    int saveMemberInfo(List<SimpleMemberInfoDto> list);

    
    
    /**
    * Description: 同步用户信息
    *
    */
    void synchronizationMember();
    
}
