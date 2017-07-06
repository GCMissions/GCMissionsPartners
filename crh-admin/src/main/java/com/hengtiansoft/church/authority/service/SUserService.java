package com.hengtiansoft.church.authority.service;

import java.util.List;

import com.hengtiansoft.church.authority.dto.SUserSaveAndUpdateDto;
import com.hengtiansoft.church.authority.dto.SUserSearchDto;
import com.hengtiansoft.church.authority.dto.SUserUpdateDto;
import com.hengtiansoft.church.entity.SRoleInfoEntity;
import com.hengtiansoft.common.dto.ResultDto;

/**
* Class Name: SUserService
* Description: 
* @author tao chen
*
*/
public interface SUserService {
    /**
     * 
    * Description: search
    *
    * @param dto
     */
    void search(SUserSearchDto dto);

    /**
     * 
    * Description: save the user
    *
    * @param dto
    * @return
     */
    ResultDto<String> save(SUserSaveAndUpdateDto dto);

    /**
     * 
    * Description: Update personal information (current user)
    *
    * @param dto
    * @return
     */
    ResultDto<String> selfUpdate(SUserSaveAndUpdateDto dto);

    /**
     * 
    * Description: update the user
    *
    * @param dto
    * @return
     */
    ResultDto<String> update(SUserUpdateDto dto);

    /**
     * 
    * Description: Find out all the roles
    *
    * @return
     */
    List<SRoleInfoEntity> findRoles();

    /**
     * 
    * Description: Find out information through the user's ID
    *
    * @param id
    * @return
     */
    SUserSaveAndUpdateDto findById(Long id);
    
    
    /**
    * Description: Queries the user exists role
    *
    * @param id
    * @return
    */
    List<SRoleInfoEntity> findRolesById(Long id);

    /**
     * 
    * Description: delete the user
    *
    * @param ids
    * @return
     */
    ResultDto<String> detele(Long id);

    /**
     * 
    * Description: Reset the password
    *
    * @param loginId
    * @return
     */
    ResultDto<String> resetPwd(String loginId);

}
