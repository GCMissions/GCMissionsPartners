package com.hengtiansoft.church.authority.service;

import java.util.List;

import com.hengtiansoft.church.authority.constant.TreeNodeBean;
import com.hengtiansoft.church.authority.dto.SRoleInfoSaveAndUpdateDto;
import com.hengtiansoft.church.authority.dto.SRoleInfoSearchDto;
import com.hengtiansoft.common.dto.ResultDto;

/**
 * 
* Class Name: SRoleInfoService
* Description: roleService
* @author taochen
*
 */
public interface SRoleInfoService {
    /**
     * 
    * Description: RESEARCH
    *
    * @param dto
     */
    void search(SRoleInfoSearchDto dto);

    /**
     * 
    * Description: Get permission node tree
    *
    * @param functionId
    * @return
     */
    List<TreeNodeBean> getFunctionTree(String functionId);

    /**
     * 
    * Description: ROEL SAVE
    *
    * @param dto
    * @return
     */
    ResultDto<String> save(SRoleInfoSaveAndUpdateDto dto);

    /**
     * 
    * Description: FIND ROLE BY ID 
    *
    * @param id
    * @return
     */
    SRoleInfoSaveAndUpdateDto findById(Long id);

    /**
     * 
    * Description: ROEL UPDATE
    *
    * @param dto
    * @return
     */
    ResultDto<String> update(SRoleInfoSaveAndUpdateDto dto);

    /**
     * 
    * Description: ROEL DELETE
    *
    * @param id
    * @return
     */
    ResultDto<String> delete(Long id);

}
