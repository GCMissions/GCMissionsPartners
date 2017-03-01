package com.hengtiansoft.business.wrkd.activity.service;

import java.util.List;

import com.hengtiansoft.business.wrkd.activity.dto.KdGroupBuySaveDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdGroupBuySearchDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdStartDateAndEndDateDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdTeamBuyProductDetailInfoDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdTeamBuySpecListDto;
import com.hengtiansoft.business.wrkd.activity.dto.SpecPriceDto;
import com.hengtiansoft.business.wrkd.product.dto.KdProductSearchDto;
import com.hengtiansoft.common.dto.ResultDto;

public interface KdGroupBuyService {

    void searchGroupBuyList(KdGroupBuySearchDto dto);
    
    ResultDto<?> saveAndUpdate(KdGroupBuySaveDto dto);
    
    ResultDto<?> saveGroupBuyProduct(KdGroupBuySaveDto dto, Long userId);
    
    ResultDto<?> updateGroupBuyProduct(KdGroupBuySaveDto dto, Long userId);
    
    KdGroupBuySaveDto getInfo(Long teamBuyId);
    
    ResultDto<List<SpecPriceDto>> getSpecPrice(Long teamBuyId);
    
    /**
     * 
    * Description: 判断商品是否绑定团购活动
    *
    * @param productId
    * @return
     */
    Boolean isGroupBuyProduct(Long productId);
    
    void saleOffUpdateStatus(Long productId);
    
    KdStartDateAndEndDateDto getEarlyStartDateAndLateEndDate(Long productId);
    
    void search(KdProductSearchDto searchDto);
    
    ResultDto<?> deleteGroupBuy(List<Long> teamBuyIds);

    ResultDto<?> getActDetailInfo(Long id);

    KdTeamBuyProductDetailInfoDto getTeamActDetailInfo(Long actId);

    List<KdTeamBuySpecListDto> getSpecforTeam(Long actId);
    
    
}
