package com.hengtiansoft.church.authority.service;

import java.util.List;

import com.hengtiansoft.church.authority.constant.TreeNodeBean;
import com.hengtiansoft.church.authority.dto.SRoleInfoSaveAndUpdateDto;
import com.hengtiansoft.church.authority.dto.SRoleInfoSearchDto;
import com.hengtiansoft.common.dto.ResultDto;

/**
 * 
* Class Name: SRoleInfoService
* Description: 角色Service
* @author zhisongliu
*
 */
public interface SRoleInfoService {
    /**
     * 
    * Description: 搜索
    *
    * @param dto
     */
    void search(SRoleInfoSearchDto dto);

    /**
     * 
    * Description: 获取权限节点树
    *
    * @param functionId
    * @return
     */
    List<TreeNodeBean> getFunctionTree(String functionId);

    /**
     * 
    * Description: 角色保存
    *
    * @param dto
    * @return
     */
    ResultDto<String> save(SRoleInfoSaveAndUpdateDto dto);

    /**
     * 
    * Description: 通过Id来查找角色信息
    *
    * @param id
    * @return
     */
    SRoleInfoSaveAndUpdateDto findById(Long id);

    /**
     * 
    * Description: 角色更新
    *
    * @param dto
    * @return
     */
    ResultDto<String> update(SRoleInfoSaveAndUpdateDto dto);

    /**
     * 
    * Description: 角色删除
    *
    * @param id
    * @return
     */
    ResultDto<String> delete(Long id);

}
