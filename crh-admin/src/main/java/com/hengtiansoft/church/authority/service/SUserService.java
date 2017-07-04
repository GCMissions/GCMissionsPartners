package com.hengtiansoft.church.authority.service;

import java.util.List;

import com.hengtiansoft.church.authority.dto.SUserSaveAndUpdateDto;
import com.hengtiansoft.church.authority.dto.SUserSearchDto;
import com.hengtiansoft.church.authority.dto.SUserUpdateDto;
import com.hengtiansoft.church.entity.SRoleInfoEntity;
import com.hengtiansoft.common.dto.ResultDto;

/**
 * 
* Class Name: SUserService
* Description: User business
* @author zhisongliu
*
 */
/**
* Class Name: SUserService
* Description: 
* @author xianghuang
*
*/
public interface SUserService {
    /**
     * 
    * Description: RESEARCH
    *
    * @param dto
     */
    void search(SUserSearchDto dto);

    /**
     * 
    * Description: USER SAVE
    *
    * @param dto
    * @return
     */
    ResultDto<String> save(SUserSaveAndUpdateDto dto);

    /**
     * 
    * Description: THE CURRENT USER DATE UDDATE
    *
    * @param dto
    * @return
     */
    ResultDto<String> selfUpdate(SUserSaveAndUpdateDto dto);

    /**
     * 
    * Description: USER UPDATE
    *
    * @param dto
    * @return
     */
    ResultDto<String> update(SUserUpdateDto dto);

    /**
     * 
    * Description: FIND ALL ROLES
    *
    * @return
     */
    List<SRoleInfoEntity> findRoles();

    /**
     * 
    * Description: FIDN USERS MESSAGE BY ID
    *
    * @param id
    * @return
     */
    SUserSaveAndUpdateDto findById(Long id);
    
    
    /**
    * Description: FIDN USER'S ROLES
    *
    * @param id
    * @return
    */
    List<SRoleInfoEntity> findRolesById(Long id);

    /**
     * 
    * Description: DELETE USER
    *
    * @param ids
    * @return
     */
    ResultDto<String> detele(Long id);

    /**
     * 
    * Description: RESET PASSWORD 
    *
    * @param loginId
    * @return
     */
    ResultDto<String> resetPwd(String loginId);

}
