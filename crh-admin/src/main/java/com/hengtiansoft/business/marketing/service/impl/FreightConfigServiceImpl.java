/*
 * Project Name: wrw-admin
 * File Name: FreightConfigServiceImpl.java
 * Class Name: FreightConfigServiceImpl
 * Copyright 2014 Hengtian Software Inc
 * Licensed under the Hengtiansoft
 * http://www.hengtiansoft.com
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.business.marketing.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.marketing.dto.BrandDto;
import com.hengtiansoft.business.marketing.dto.CateDto;
import com.hengtiansoft.business.marketing.dto.FreePostageDto;
import com.hengtiansoft.business.marketing.dto.FreePostageListDto;
import com.hengtiansoft.business.marketing.dto.FreightConfigDto;
import com.hengtiansoft.business.marketing.dto.FreightConfigListDto;
import com.hengtiansoft.business.marketing.service.FreightConfigService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.enumeration.EErrorCode;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.SystemConst;
import com.hengtiansoft.wrw.dao.PBrandDao;
import com.hengtiansoft.wrw.dao.PCategoryDao;
import com.hengtiansoft.wrw.dao.PProductDao;
import com.hengtiansoft.wrw.dao.SBasicParaDao;
import com.hengtiansoft.wrw.dao.SFreightConfigDao;
import com.hengtiansoft.wrw.dao.SFreightRegionDao;
import com.hengtiansoft.wrw.entity.PBrandEntity;
import com.hengtiansoft.wrw.entity.PCategoryEntity;
import com.hengtiansoft.wrw.entity.PProductEntity;
import com.hengtiansoft.wrw.entity.SBasicParaEntity;
import com.hengtiansoft.wrw.entity.SFreightConfigEntity;
import com.hengtiansoft.wrw.entity.SFreightRegionEntity;
import com.hengtiansoft.wrw.enums.BasicTypeEnum;
import com.hengtiansoft.wrw.enums.SFreightConfigType;
import com.hengtiansoft.wrw.enums.StatusEnum;

/**
 * Class Name: FreightConfigServiceImpl
 * Description: TODO
 * 
 * @author chengminmiao
 */
@Service
public class FreightConfigServiceImpl implements FreightConfigService {
    @Autowired
    private SFreightConfigDao sFreightConfigDao;

    @Autowired
    private SFreightRegionDao sFreightRegionDao;

    @Autowired
    private SBasicParaDao     sBasicParaDao;

    @Autowired
    private PProductDao       pProductDao;

    @Autowired
    private PCategoryDao      pCategoryDao;

    @Autowired
    private PBrandDao         pBrandDao;

    /**
     * 查询
     */
    @Override
    public FreightConfigListDto configList(String type) {
        FreightConfigListDto freightConfigListDto = new FreightConfigListDto();

        Integer basicType = null;
        Long defConfigId = null;

        // 处理免费邮费金额
        if (type.equalsIgnoreCase(SFreightConfigType.FREIGHT.getKey())) {
            basicType = BasicTypeEnum.FREE_FRIEIGHT.getKey();
            defConfigId = SystemConst.DEF_FREIGHT;
        } else if (type.equalsIgnoreCase(SFreightConfigType.POSTAGE.getKey())) {
            basicType = BasicTypeEnum.FREE_POSTAGE.getKey();
            defConfigId = SystemConst.DEF_POSTAGE;
        }
        SBasicParaEntity paraEntity = sBasicParaDao.findByTypeId(basicType).get(0);
        if (null != paraEntity) {
            freightConfigListDto.setFreeFreight(new Long(paraEntity.getParaValue1()));
        }

        // 保存默认的邮费信息
        SFreightConfigEntity defEntity = sFreightConfigDao.findOne(defConfigId);
        if (null != defEntity) {
            freightConfigListDto.setStartCost(defEntity.getStartCost());
            freightConfigListDto.setStartNum(defEntity.getStartNum());
            freightConfigListDto.setPlusCost(defEntity.getPlusCost());
            freightConfigListDto.setPlusNum(defEntity.getPlusNum());
        }

        List<SFreightConfigEntity> configEntities = sFreightConfigDao.findByFreightTypeNotDef(type);
        if (configEntities.size() != 0) {
            List<FreightConfigDto> dtos = new ArrayList<FreightConfigDto>();
            for (SFreightConfigEntity configEntity : configEntities) {
                FreightConfigDto configDto = ConverterService.convert(configEntity, FreightConfigDto.class);
                configDto.setRegionId(sFreightRegionDao.findRegionIdsByConfigId(configEntity.getConfigId()));
                configDto.setRegionIdString(WRWUtil.intListToString(configDto.getRegionId()));
                configDto.setRegionNames(WRWUtil.listToString(sFreightRegionDao.findAllRegionNames(configEntity.getConfigId())));
                dtos.add(configDto);
            }
            freightConfigListDto.setList(dtos);
        }

        return freightConfigListDto;
    }

    /**
     * 保存
     */
    @Override
    @Transactional
    public ResultDto<String> configSave(FreightConfigListDto dto, String type) {
        Integer basicType = null;
        Long defConfigId = null;

        // 处理免费邮费金额
        if (type.equalsIgnoreCase(SFreightConfigType.FREIGHT.getKey())) {
            basicType = BasicTypeEnum.FREE_FRIEIGHT.getKey();
            defConfigId = SystemConst.DEF_FREIGHT;
        } else if (type.equalsIgnoreCase(SFreightConfigType.POSTAGE.getKey())) {
            basicType = BasicTypeEnum.FREE_POSTAGE.getKey();
            defConfigId = SystemConst.DEF_POSTAGE;
        }
        if (null != dto.getFreeFreight()) {
            SBasicParaEntity paraEntity = sBasicParaDao.findByTypeId(basicType).get(0);
            paraEntity.setParaValue1(dto.getFreeFreight().toString());
            sBasicParaDao.save(paraEntity);
        }
        // 保存默认的邮费信息
        SFreightConfigEntity defEntity = sFreightConfigDao.findOne(defConfigId);
        if (null == defEntity) {
            defEntity = new SFreightConfigEntity();
            defEntity.setConfigId(defConfigId);
            defEntity.setFreightType(type);
            defEntity.setCreateId(AuthorityContext.getCurrentUser().getUserId());
        }
        defEntity.setStartNum(dto.getStartNum());
        defEntity.setStartCost(dto.getStartCost());
        defEntity.setPlusNum(dto.getPlusNum());
        defEntity.setPlusCost(dto.getPlusCost());
        defEntity.setModifyDate(new Date());
        defEntity.setModifyId(AuthorityContext.getCurrentUser().getUserId());
        sFreightConfigDao.save(defEntity);

        // 保存配置信息
        List<Long> delList = sFreightConfigDao.findConfigIdByType(type);
        delList.remove(SystemConst.DEF_FREIGHT);
        delList.remove(SystemConst.DEF_POSTAGE);

        List<SFreightRegionEntity> frEntities = new ArrayList<SFreightRegionEntity>();
        for (FreightConfigDto configDto : dto.getList()) {
            SFreightConfigEntity configEntity = ConverterService.convert(configDto, SFreightConfigEntity.class);
            if (configEntity.getConfigId() != null) {
                configEntity.setModifyId(AuthorityContext.getCurrentUser().getUserId());
                configEntity.setModifyDate(new Date());
                delList.remove(configEntity.getConfigId());
            } else {
                configEntity.setFreightType(type);
                configEntity.setCreateId(AuthorityContext.getCurrentUser().getUserId());
            }
            sFreightConfigDao.save(configEntity);
            String[] regionIdStrings = configDto.getRegionIdString().split(",");
            for (String regionId : regionIdStrings) {
                frEntities.add(new SFreightRegionEntity(configEntity.getConfigId(), Integer.valueOf(regionId)));
            }

        }
        sFreightRegionDao.save(frEntities);

        // 删除不存在的数据
        if (delList.size() > 0) {
            sFreightRegionDao.delByConfigIds(delList);
            sFreightConfigDao.delByConfigIds(delList);
        }

        return ResultDtoFactory.toAck(EErrorCode.SUCCESS);
    }

    @Override
    public List<CateDto> findCate() {
        List<CateDto> dto = new ArrayList<CateDto>();
        for (PCategoryEntity entity : pCategoryDao.findAllByStatus(StatusEnum.NORMAL.getCode())) {
            dto.add(ConverterService.convert(entity, CateDto.class));
        }
        return dto;
    }

    @Override
    public List<BrandDto> findBrand() {
        List<BrandDto> dto = new ArrayList<BrandDto>();
        for (PBrandEntity entity : pBrandDao.findAllByStatus(StatusEnum.NORMAL.getCode())) {
            dto.add(ConverterService.convert(entity, BrandDto.class));
        }
        return dto;
    }

    @Override
    public void freeList(final FreePostageListDto dto) {
        PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.DESC, "productId"));

        Page<PProductEntity> page = pProductDao.findAll(new Specification<PProductEntity>() {
            @Override
            public Predicate toPredicate(Root<PProductEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.<String> get("status"), StatusEnum.NORMAL.getCode()));
                if (!WRWUtil.isEmpty(dto.getProductName())) {
                    predicates.add(cb.like(root.<String> get("productName"), "%" + dto.getProductName() + "%"));
                }
                if (null != dto.getCateId()) {
                    predicates.add(cb.equal(root.<Long> get("cateId"), dto.getCateId()));
                }
                if (!WRWUtil.isEmpty(dto.getProductCode())) {
                    predicates.add(cb.equal(root.<String> get("productCode"), dto.getProductCode()));
                }
                if (null != dto.getBrandId()) {
                    predicates.add(cb.equal(root.<Long> get("brandId"), dto.getBrandId()));
                }

                Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate);
                return query.getRestriction();
            }
        }, pageable);

        dto.setTotalRecord(page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setList(buildFreePostageDto(page.getContent()));
    }

    private List<FreePostageDto> buildFreePostageDto(List<PProductEntity> content) {
        List<FreePostageDto> dtos = new ArrayList<FreePostageDto>();

        if (content.size() > 0) {
            for (PProductEntity entity : content) {
                dtos.add(ConverterService.convert(entity, FreePostageDto.class));
            }
        }
        return dtos;
    }

    @Override
    @Transactional
    public ResultDto<String> saveFree(FreePostageDto dto) {
        PProductEntity entity = pProductDao.findOne(dto.getProductId());
        if (null == entity) {
            throw new WRWException(EErrorCode.ENTITY_PRODUCT_NOT_EXIST);
        }
        entity.setShipfeeConfig(dto.getShipfeeConfig());
        pProductDao.save(entity);
        return ResultDtoFactory.toAck(EErrorCode.SUCCESS);
    }
}
