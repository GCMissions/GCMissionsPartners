package com.hengtiansoft.church.authority.service;

import java.util.List;

import com.hengtiansoft.church.authority.dto.SUserSaveAndUpdateDto;
import com.hengtiansoft.church.authority.dto.SUserSearchDto;
import com.hengtiansoft.church.authority.dto.SUserUpdateDto;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.wrw.entity.SRoleInfoEntity;

/**
 * 
* Class Name: SUserService
* Description: 用户业务
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
    * Description: 搜索
    *
    * @param dto
     */
    void search(SUserSearchDto dto);

    /**
     * 
    * Description: 用户保存
    *
    * @param dto
    * @return
     */
    ResultDto<String> save(SUserSaveAndUpdateDto dto);

    /**
     * 
    * Description: 个人资料更新（当前用户）
    *
    * @param dto
    * @return
     */
    ResultDto<String> selfUpdate(SUserSaveAndUpdateDto dto);

    /**
     * 
    * Description: 用户更新
    *
    * @param dto
    * @return
     */
    ResultDto<String> update(SUserUpdateDto dto);

    /**
     * 
    * Description: 查询出所有的角色
    *
    * @return
     */
    List<SRoleInfoEntity> findRoles();

    /**
     * 
    * Description: 通过ID来查找出用户信息
    *
    * @param id
    * @return
     */
    SUserSaveAndUpdateDto findById(Long id);
    
    
    /**
    * Description: 查询用户拥有的角色
    *
    * @param id
    * @return
    */
    List<SRoleInfoEntity> findRolesById(Long id);

    /**
     * 
    * Description: 删除账户
    *
    * @param ids
    * @return
     */
    ResultDto<String> detele(Long id);

    /**
     * 
    * Description: 重置密码
    *
    * @param loginId
    * @return
     */
    ResultDto<String> resetPwd(String loginId);

}
