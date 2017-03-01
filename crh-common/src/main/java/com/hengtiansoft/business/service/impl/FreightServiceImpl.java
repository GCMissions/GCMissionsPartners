package com.hengtiansoft.business.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.dto.ShipmentFeeDto;
import com.hengtiansoft.business.service.FreightService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.BizUtil;
import com.hengtiansoft.wrw.SystemConst;
import com.hengtiansoft.wrw.dao.MAddressDao;
import com.hengtiansoft.wrw.dao.PProductDao;
import com.hengtiansoft.wrw.dao.SFreightConfigDao;
import com.hengtiansoft.wrw.dao.SFreightRegionDao;
import com.hengtiansoft.wrw.dao.SRegionDao;
import com.hengtiansoft.wrw.entity.MAddressEntity;
import com.hengtiansoft.wrw.entity.PProductEntity;
import com.hengtiansoft.wrw.entity.SFreightConfigEntity;
import com.hengtiansoft.wrw.enums.AddressType;
import com.hengtiansoft.wrw.enums.ShipmentType;
import com.hengtiansoft.wrw.enums.StatusEnum;

/**
 * @author huizhuang
 */
@Service
public class FreightServiceImpl implements FreightService {

    @Autowired
    private MAddressDao       mAddressDao;

    @Autowired
    private PProductDao       pProductDao;

    @Autowired
    private SRegionDao        sRegionDao;

    @Autowired
    private SFreightRegionDao sFreightRegionDao;

    @Autowired
    private SFreightConfigDao sFreightConfigDao;

    /**
     * Description: 获取配送费
     *
     * @param productNumMap
     * @return
     */
    @Deprecated
    public ShipmentFeeDto getActualShipAmount(Map<Long, Integer> productNumMap) {
        // 获取配送地区ID
        MAddressEntity address = mAddressDao.findByMemberIdAndDefFlagAndStatus(getMemberId(), AddressType.DEFAULT.getCode(), StatusEnum.NORMAL.getCode());
        if (null == address) {
            throw new WRWException("没有有效的默认地址");
        }
        
        return getActualShipAmount(address.getId(), productNumMap);
    }
    
    /**
     * 获取配送费
     */
    @Override
    public ShipmentFeeDto getActualShipAmount(Long addressId, Map<Long, Integer> productNumMap) {
        ShipmentFeeDto shipmentFeeDto = new ShipmentFeeDto();
       
        SFreightConfigEntity freightConfig = getFreightConfig(addressId);
        Long startCost = freightConfig.getStartCost();
        Long plusCost = freightConfig.getPlusCost();
        
        Long totalShipAmount = 0L;
        Double totalDiscountShipAmount = 0D;
        
        for (PProductEntity product : pProductDao.findAll(productNumMap.keySet())) {
            Integer specProductNum = product.getSpecNum() * productNumMap.get(product.getProductId());
            
            Long shipAmount = calculateDeliveryFee(specProductNum, startCost, plusCost);
            Double discountShipAmount = shipAmount.doubleValue() * getShipmentFeeDiscount(product.getShipfeeConfig(), specProductNum);
            
            totalShipAmount += shipAmount;
            totalDiscountShipAmount += discountShipAmount;
        }
        
        shipmentFeeDto.setTotalAmount(totalShipAmount);
        shipmentFeeDto.setActualAmount(totalDiscountShipAmount.longValue());
        shipmentFeeDto.setDiscountAmount(shipmentFeeDto.getTotalAmount() - shipmentFeeDto.getActualAmount());
        return shipmentFeeDto;
    }
    
    /**
     * Description: 获取产品配送费折扣
     *
     * @param config 折扣配置
     * @param productNum 产品数量
     * @return
     */
    private Double getShipmentFeeDiscount(String config, Integer productNum) {
        if (BizUtil.isEmpty(config)) {
            return 1D;
        }
        
        // 梯度折扣配置划分
        String[] discountConfigDivide = config.split(",|;");
        for (int i = 0, size = discountConfigDivide.length; i < size; i+=3) {
            if (productNum >= Integer.valueOf(discountConfigDivide[i]) && productNum <= Integer.valueOf(discountConfigDivide[i + 1])) {
                return Double.valueOf(discountConfigDivide[i + 2]);
            }
        }
        
        return 1D;    
    }

    /**
     * Description: 计算配送费
     *
     * @param num
     *            商品数量
     * @param config
     *            配送费配置
     * @return
     */
    private Long calculateDeliveryFee(Integer num, Long startCost, Long plusCost) {
        if (num <= 0L) {
            return 0L;
        }
        return num <= 1 ? startCost : num * plusCost;
    }
    
    /**
     * Description: 获取运费配置
     *
     * @param freightType
     *            配送类型
     * @return
     */
    private SFreightConfigEntity getFreightConfig(Long addressId) {
        if (null == addressId) {
            return sFreightConfigDao.findOne(SystemConst.DEF_FREIGHT);
        }
        
        Integer regionId = sRegionDao.findOne(mAddressDao.findOne(addressId).getRegionId()).getParentId();

        // 获取配送费配置
        List<Long> freightConfigIds = sFreightRegionDao.findConfigIdByRegionId(regionId);
        if (!freightConfigIds.isEmpty()) {
            List<SFreightConfigEntity> freightConfigs = sFreightConfigDao.findByFreightTypeAndConfigIdIn(ShipmentType.delivery.getKey(), freightConfigIds);
            if (!freightConfigs.isEmpty()) {
                return freightConfigs.get(0);
            }
        }
        
        // 获取【默认】配送费配置
        return sFreightConfigDao.findOne(SystemConst.DEF_FREIGHT);
    }
    
    /**
     * Description: 获取当前用户ID
     *
     * @return
     */
    private Long getMemberId() {
        return AuthorityContext.getCurrentUser().getUserId();
    }
}
