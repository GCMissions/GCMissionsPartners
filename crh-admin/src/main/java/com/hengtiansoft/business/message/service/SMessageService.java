package com.hengtiansoft.business.message.service;

import com.hengtiansoft.business.message.dto.SMessagePageDto;
import com.hengtiansoft.wrw.entity.SMessageEntity;

public interface SMessageService {

    /**
     * 
    * Description: 查询所有站内信
    *
    * @param sMessagePageDto
     */
    void search(SMessagePageDto sMessagePageDto);

    /**
     * 
    * Description: 根据id修改站内信
    *
    * @param messageId
    * @return
     */
    SMessageEntity editOne(String messageId);

    /**
     * 
    * Description: 获得未读站内信数量
    *
    * @return
     */
    Integer getUnredMessageNum();

}
