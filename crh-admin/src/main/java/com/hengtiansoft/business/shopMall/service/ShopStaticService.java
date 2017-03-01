package com.hengtiansoft.business.shopMall.service;

import java.util.List;

import com.hengtiansoft.wrw.entity.PProductEntity;
import com.hengtiansoft.wrw.entity.SRegionEntity;

public interface ShopStaticService {

    List<SRegionEntity> findByRegions(List<Integer> list);
    
    List<PProductEntity> findAllShief();

}
