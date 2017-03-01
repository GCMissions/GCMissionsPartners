package com.hengtiansoft.business.activity.service;

import java.util.List;

import com.hengtiansoft.business.activity.dto.ActivityCopyDto;
import com.hengtiansoft.business.activity.dto.ActivityCopyPageDto;
import com.hengtiansoft.business.activity.dto.ActivityDetailSaveDto;
import com.hengtiansoft.business.activity.dto.ActivityEditDto;
import com.hengtiansoft.business.activity.dto.ActivitySaveDto;
import com.hengtiansoft.business.activity.dto.ActivitySearchDto;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.wrw.entity.PCategoryEntity;
import com.hengtiansoft.wrw.entity.PProductEntity;
import com.hengtiansoft.wrw.entity.SOrgEntity;
/**
 * 
* Class Name: ActivityService
* Description: TODO
* @author chenghongtu
*
 */
public interface ActivityService {
    
    void search(ActivitySearchDto dto);
    
//    List<SOrgEntity> findAllOrgs();
    
    List<PCategoryEntity> findAllFirstCates();

    List<PCategoryEntity> findAllSecondCateByFirstCateId(Long firstCateId);

    List<SOrgEntity> findOrgs();

    PProductEntity save(ActivitySaveDto dto);

    void search(ActivityCopyPageDto activityCopyPageDto);

    PCategoryEntity getParentCateBySon(Long id);
    
    ResultDto<String> delete(String ids);

    ActivityCopyDto findById(Long id);

    PProductEntity update(ActivityEditDto dto);

    /**
     * 
    * Description: 获得商品的一级品类、二级品类名称
    *
    * @param id
    * @return
     */
    String findCateByProductId(Long id);

    /**
     * 
    * Description: 保存商品详情
    *
    * @param dto
    * @return
     */
    PProductEntity saveDetail(ActivityDetailSaveDto dto);

    void changeCartByModifyProduct(Long pid);

}
