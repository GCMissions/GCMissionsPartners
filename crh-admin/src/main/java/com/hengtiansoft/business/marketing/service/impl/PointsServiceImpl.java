package com.hengtiansoft.business.marketing.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.marketing.dto.PointsDto;
import com.hengtiansoft.business.marketing.service.PointsService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.enumeration.EErrorCode;
import com.hengtiansoft.wrw.dao.SBasicParaDao;
import com.hengtiansoft.wrw.entity.SBasicParaEntity;
import com.hengtiansoft.wrw.enums.BasicTypeEnum;

/**
 * Class Name: PointsServiceImpl Description: 积分配置管理
 * 
 * @author yigesong
 */
@Service
public class PointsServiceImpl implements PointsService {

    @Autowired
    private SBasicParaDao sBasicParaDao;

    @Override
    public PointsDto getPointConfig() {
        PointsDto dto = new PointsDto();
        List<SBasicParaEntity> registerEntity = sBasicParaDao.findByTypeId(BasicTypeEnum.POINT_REGISTER.getKey());
        if (registerEntity.size() > 0) {
            dto.setRegisterPoints(registerEntity.get(0).getParaValue1());
        }
        List<SBasicParaEntity> buyEntities = sBasicParaDao.findByTypeId(BasicTypeEnum.POINT_BUY.getKey());
        if (buyEntities.size() > 0) {
            SBasicParaEntity buyEntity = buyEntities.get(0);
            dto.setBuyAmount(buyEntity.getParaValue1());
            dto.setBuyPoints(buyEntity.getParaValue2());
        }
        return dto;
    }

    @Override
    @Transactional
    public ResultDto<String> savePointsConfig(PointsDto pointsDto) {
        List<SBasicParaEntity> registerEntities = sBasicParaDao.findByTypeId(BasicTypeEnum.POINT_REGISTER.getKey());
        if (registerEntities.size() > 0) {
            SBasicParaEntity registerEntity = registerEntities.get(0);
            registerEntity.setParaValue1(pointsDto.getRegisterPoints());
            sBasicParaDao.save(registerEntity);
        } else {
            SBasicParaEntity registerEntity = new SBasicParaEntity();
            registerEntity.setTypeId(BasicTypeEnum.POINT_REGISTER.getKey());
            registerEntity.setParaName(BasicTypeEnum.POINT_REGISTER.getValue());
            registerEntity.setParaValue1(pointsDto.getRegisterPoints());
            sBasicParaDao.save(registerEntity);
        }
        List<SBasicParaEntity> buyEntities = sBasicParaDao.findByTypeId(BasicTypeEnum.POINT_BUY.getKey());
        if (buyEntities.size() > 0) {
            SBasicParaEntity buyEntity = buyEntities.get(0);
            buyEntity.setParaValue1(pointsDto.getBuyAmount());
            buyEntity.setParaValue2(pointsDto.getBuyPoints());
            sBasicParaDao.save(buyEntity);
        } else {
            SBasicParaEntity buyEntity = new SBasicParaEntity();
            buyEntity.setParaName(BasicTypeEnum.POINT_BUY.getValue());
            buyEntity.setTypeId(BasicTypeEnum.POINT_BUY.getKey());
            buyEntity.setParaValue1(pointsDto.getBuyAmount());
            buyEntity.setParaValue2(pointsDto.getBuyPoints());
            sBasicParaDao.save(buyEntity);
        }
        return ResultDtoFactory.toAck(EErrorCode.SUCCESS);
    }
}
