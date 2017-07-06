package com.hengtiansoft.church.authority.service;

import java.util.List;

import com.hengtiansoft.church.authority.constant.TreeNodeBean;
import com.hengtiansoft.church.authority.dto.SRoleInfoSaveAndUpdateDto;
import com.hengtiansoft.church.authority.dto.SRoleInfoSearchDto;
import com.hengtiansoft.common.dto.ResultDto;

/**
 * 
* Class Name: SRoleInfoService
* Description:  role service
* @author tao chen
*
 */
public interface SRoleInfoService {
    /**
     * 
    * Description: search
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
    * Description: save roles
    *
    * @param dto
    * @return
     */
    ResultDto<String> save(SRoleInfoSaveAndUpdateDto dto);

    /**
     * 
    * Description: To find the role information by Id
    *
    * @param id
    * @return
     */
    SRoleInfoSaveAndUpdateDto findById(Long id);

    /**
     * 
    * Description: Update the role
    *
    * @param dto
    * @return
     */
    ResultDto<String> update(SRoleInfoSaveAndUpdateDto dto);

    /**
     * 
    * Description: delete the role
    *
    * @param id
    * @return
     */
    ResultDto<String> delete(Long id);

}
