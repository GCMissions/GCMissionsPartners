package com.hengtiansoft.business.message.service;

import com.hengtiansoft.business.message.dto.MessageViewDto;
import com.hengtiansoft.business.message.dto.PMessageSaveDto;
import com.hengtiansoft.business.message.dto.PMessageSearchDto;
import com.hengtiansoft.business.message.dto.SOrgSearchDto;
import com.hengtiansoft.common.dto.ResultDto;

/**
 * Class Name : PMessageService
 * 
 * @author zhisongliu
 *
 */
public interface PMessageService {

    /**
     * DESC :站内信首页列表
     * 
     * @param dto
     */
    void search(PMessageSearchDto dto);

    /**
     * DESC :根据搜索条件筛选出需要的商家列表
     * 
     * @param dto
     */
    void findMember(SOrgSearchDto dto);

    /**
     * Desc : 保存站内信信息
     * 
     * @param dto
     * @return
     */
    ResultDto<String> save(PMessageSaveDto dto);

    /**
     * DESC : 通过站内信ID来查找出该站内信信息 
     * 
     * @param id
     * @return
     */
    MessageViewDto findById(Long id);

}
