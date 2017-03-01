package com.hengtiansoft.business.provider.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.common.service.SMSService;
import com.hengtiansoft.business.provider.dto.OrgDto;
import com.hengtiansoft.business.provider.dto.OrgSaveDto;
import com.hengtiansoft.business.provider.dto.OrgSimpleDto;
import com.hengtiansoft.business.provider.dto.SalerDto;
import com.hengtiansoft.business.provider.dto.SearchOrgDto;
import com.hengtiansoft.business.provider.dto.SearchSalerDto;
import com.hengtiansoft.business.provider.service.OrgService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.enumeration.EErrorCode;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.sequence.SequenceGenerator;
import com.hengtiansoft.common.sequence.SequenceType;
import com.hengtiansoft.common.util.AppConfigUtil;
import com.hengtiansoft.common.util.BeanUtil;
import com.hengtiansoft.common.util.EncryptUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.SystemConst;
import com.hengtiansoft.wrw.dao.MOrderMainDao;
import com.hengtiansoft.wrw.dao.PProductDao;
import com.hengtiansoft.wrw.dao.SOrgDao;
import com.hengtiansoft.wrw.dao.SRegionDao;
import com.hengtiansoft.wrw.dao.SRoleInfoDao;
import com.hengtiansoft.wrw.dao.SShipmentOrderDao;
import com.hengtiansoft.wrw.dao.SUserDao;
import com.hengtiansoft.wrw.dao.SUserRoleDao;
import com.hengtiansoft.wrw.entity.PProductEntity;
import com.hengtiansoft.wrw.entity.SOrgEntity;
import com.hengtiansoft.wrw.entity.SRegionEntity;
import com.hengtiansoft.wrw.entity.SUserEntity;
import com.hengtiansoft.wrw.entity.SUserRoleEntity;
import com.hengtiansoft.wrw.enums.OrgTypeEnum;
import com.hengtiansoft.wrw.enums.SOrgStatusEnum;
import com.hengtiansoft.wrw.enums.StatusEnum;
import com.hengtiansoft.wrw.enums.UserStateEnum;

@Service
public class OrgServiceImpl implements OrgService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrgServiceImpl.class);

    @Autowired
    private SOrgDao sOrgDao;

    @Autowired
    private SUserDao sUserDao;

    @Autowired
    private SUserRoleDao sUserRoleDao;

    @Autowired
    private SRegionDao sRegionDao;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private SShipmentOrderDao sShipmentOrderDao;

    @Autowired
    private MOrderMainDao mOrderMainDao;

    @Autowired
    private SMSService sMSService;

    @Autowired
    private SRoleInfoDao sRoleInfoDao;

    @Autowired
    private PProductDao pProductDao;

    /**
     * 查找Org
     */
    @Override
    public void findOrg(final SearchOrgDto dto, final String orgType) {
        final List<Integer> regionIds = new ArrayList<Integer>();
        boolean regionFlag = false;
        if (dto.getCityId() != null) {
            regionIds.addAll(sRegionDao.findAllByTreePath("%" + dto.getCityId() + "%"));
            regionFlag = true;
        } else if (dto.getProvinceId() != null) {
            regionIds.addAll(sRegionDao.findAllByTreePath("%" + dto.getProvinceId() + "%"));
            regionFlag = true;
        }

        if (!(regionFlag && regionIds.size() == 0)) {

            PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(
                    Direction.DESC, "createDate"));
            Page<SOrgEntity> page = sOrgDao.findAll(new Specification<SOrgEntity>() {
                @Override
                public Predicate toPredicate(Root<SOrgEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> predicates = new ArrayList<Predicate>();
                    if (!WRWUtil.isEmpty(dto.getStatus())) {
                        predicates.add(cb.equal(root.<String> get("status"), dto.getStatus()));
                    }
                    if (!WRWUtil.isEmpty(orgType)) {
                        predicates.add(cb.equal(root.<String> get("orgType"), orgType));
                    }
                    if (!WRWUtil.isEmpty(dto.getOrgCode())) {
                        predicates.add(cb.equal(root.<String> get("orgCode"), dto.getOrgCode()));
                    }
                    if (!WRWUtil.isEmpty(dto.getOrgName())) {
                        predicates.add(cb.like(root.<String> get("orgName"), "%" + dto.getOrgName() + "%"));
                    }
                    if (null != dto.getParentId()) {
                        predicates.add(cb.equal(root.<Long> get("parentId"), dto.getParentId()));
                    }
                    if (!WRWUtil.isEmpty(dto.getContact())) {
                        predicates.add(cb.like(root.<String> get("contact"), "%" + dto.getContact() + "%"));
                    }
                    if (!WRWUtil.isEmpty(dto.getPhone())) {
                        predicates.add(cb.like(root.<String> get("phone"), dto.getPhone() + "%"));
                    }
                    if (regionIds.size() > 0) {
                        predicates.add(root.<Integer> get("region").in(regionIds));
                    }
                    if (null != dto.getBeginDate()) {
                        predicates.add(cb.greaterThanOrEqualTo(root.<Date> get("createDate"), dto.getBeginDate()));
                    }
                    if (null != dto.getEndDate()) {
                        predicates.add(cb.lessThanOrEqualTo(root.<Date> get("createDate"), dto.getEndDate()));
                    }

                    Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    query.where(predicate);
                    return query.getRestriction();
                }
            }, pageable);
            dto.setTotalRecord(page.getTotalElements());
            dto.setTotalPages(page.getTotalPages());
            dto.setList(buildOrgDtos(page.getContent()));
        }
    }

    /**
     * Description: 数据转换
     *
     * @param content
     * @return
     */
    private List<OrgSimpleDto> buildOrgDtos(List<SOrgEntity> content) {
        List<OrgSimpleDto> dtos = new ArrayList<OrgSimpleDto>();

        if (content.size() > 0) {
            List<Integer> regionIds = new ArrayList<Integer>();
            HashMap<Integer, SRegionEntity> regionMap = new HashMap<Integer, SRegionEntity>();
            for (SOrgEntity entity : content) {
                regionIds.add(entity.getRegion());
            }
            if (regionIds.size() > 0) {
                List<SRegionEntity> regionEntities = sRegionDao.findAll(regionIds);
                for (SRegionEntity regionEntity : regionEntities) {
                    regionMap.put(regionEntity.getId(), regionEntity);
                }
            }

            for (SOrgEntity entity : content) {
                OrgSimpleDto dto = ConverterService.convert(entity, new OrgSimpleDto());
                dto.setAddress(regionMap.get(entity.getRegion()).getMergerName().replace(",", "") + dto.getAddress());
                dto.setProvinceName(regionMap.get(entity.getRegion()).getMergerName());
                dtos.add(dto);
            }
        }
        return dtos;
    }

    private List<SalerDto> buildSalerDtos(List<SOrgEntity> content) {
        List<SalerDto> dtos = new ArrayList<SalerDto>();
        for (SOrgEntity entity : content) {
            SalerDto dto = ConverterService.convert(entity, new SalerDto());
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public OrgDto findById(Long orgId) {
        SOrgEntity orgEntity = sOrgDao.findOne(orgId);
        if (orgEntity != null) {
            OrgDto dto = ConverterService.convert(orgEntity, new OrgDto());
            SUserEntity userEntity = sUserDao.findByOrgId(orgEntity.getOrgId());
            SRegionEntity regionEntity = sRegionDao.findOne(orgEntity.getRegion());
            if (null != orgEntity.getParentId()) {
                SOrgEntity parentEntity = sOrgDao.findOne(orgEntity.getParentId());
                dto.setParentName(parentEntity.getOrgName());
            }
            dto.setPhone(userEntity.getPhone());
            dto.setLoginId(userEntity.getLoginId());
            dto.setRegionName(regionEntity.getMergerName().replace(",", ""));
            String[] regionIds = regionEntity.getTreePath().split(",");
            dto.setProvinceId(Integer.valueOf(regionIds[1]));
            dto.setCityId(Integer.valueOf(regionIds[2]));
            if (regionIds.length > 3) {
                dto.setAreaId(Integer.valueOf(regionIds[3]));
            }
            dto.setQrCodeUrl(AppConfigUtil.getConfig("common.qr.server") + AppConfigUtil.getConfig("common.qr.url")
                    + "/?" + dto.getOrgId());
            return dto;
        }
        return null;
    }

    @Override
    @Transactional
    public ResultDto<String> update(OrgDto dto) {
        SOrgEntity oldOrgEntity = sOrgDao.findOne(dto.getOrgId());
        if (oldOrgEntity == null) {
            throw new WRWException(EErrorCode.ENTITY_ORG_NOT_EXIST);
        }
        // 处理禁用逻辑
        if (StatusEnum.DELETE.getCode().equalsIgnoreCase(dto.getStatus())) {
            // 判断所属在用的终端配送商
            if (OrgTypeEnum.P.getKey().equalsIgnoreCase(oldOrgEntity.getOrgType())) {
                if (sOrgDao.countAllUsedOrg(StatusEnum.NORMAL.getCode(), oldOrgEntity.getOrgId()) > 0) {
                    throw new WRWException(EErrorCode.BIZ_ORGSON_IS_USING);
                }
            }
            // 判断未完成订单
            if (sShipmentOrderDao.countOrderByStatusAndShipmentId(SystemConst.SHIPMENT_UNCOMPLET_STATUS,
                    oldOrgEntity.getOrgId()) > 0) {
                throw new WRWException(EErrorCode.BIZ_HAS_ORDER);
            }
            if (sShipmentOrderDao.countOrderByStatusAndReceivingId(SystemConst.SHIPMENT_UNCOMPLET_STATUS,
                    oldOrgEntity.getOrgId()) > 0) {
                throw new WRWException(EErrorCode.BIZ_HAS_ORDER);
            }
            if (mOrderMainDao.countOrderByStatusAndOrgId(SystemConst.ORDER_UNCOMPLET_STATUS, oldOrgEntity.getOrgId()) > 0) {
                throw new WRWException(EErrorCode.BIZ_HAS_ORDER);
            }
        }

        BeanUtil.copyProperties(oldOrgEntity, dto);

        oldOrgEntity.setModifyDate(new Date());
        oldOrgEntity.setModifyId(AuthorityContext.getCurrentUser().getUserId());
        sOrgDao.save(oldOrgEntity);

        SUserEntity userEntity = sUserDao.findByOrgId(oldOrgEntity.getOrgId());
        userEntity.setPhone(dto.getPhone());
        userEntity.setStatus(oldOrgEntity.getStatus());
        sUserDao.save(userEntity);

        return ResultDtoFactory.toAck(EErrorCode.SUCCESS);
    }

    @Override
    @Transactional
    public ResultDto<String> save(OrgDto dto) {
        // 查找用户，如果已经存在，不允许新建
        SUserEntity oldUserEntity = sUserDao.findByLoginId(dto.getLoginId());
        if (null != oldUserEntity) {
            throw new WRWException(EErrorCode.ENTITY_USER_IS_EXIST);
        }
        SOrgEntity oldSOrgEntity = sOrgDao.findByOrgName(dto.getOrgName());
        if (null != oldSOrgEntity) {
            throw new WRWException("名称已存在，请重新输入", EErrorCode.ENTITY_ORG_IS_EXIST);
        }
        // ORG数据处理
        SOrgEntity orgEntity = ConverterService.convert(dto, new SOrgEntity());
        orgEntity.setOrgCode(dto.getOrgType() + sequenceGenerator.take(SequenceType.ORG));
        orgEntity.setRegion(dto.getRegion());
        orgEntity.setStatus(SOrgStatusEnum.NORMAL.getCode());
        if (null == orgEntity.getBarcodeFlag()) {
            orgEntity.setBarcodeFlag("0");
        }
        sOrgDao.save(orgEntity);

        // 用户数据处理
        SUserEntity userEntity = new SUserEntity();
        userEntity.setOrgId(orgEntity.getOrgId());
        // 默认密码取6位随机数
        String randomPw = WRWUtil.getRandomNumberString(6);

        userEntity.setPassword(EncryptUtil.encryptMd5(EncryptUtil.encryptMd5(randomPw),
                String.valueOf(dto.getLoginId())));
        userEntity.setUserName(orgEntity.getOrgName());
        userEntity.setLoginId(dto.getLoginId());
        userEntity.setPhone(orgEntity.getPhone());
        userEntity.setStatus(UserStateEnum.NORMAL.getCode());
        userEntity.setCreateDate(new Date());
        userEntity.setCreateId(AuthorityContext.getCurrentUser().getUserId());

        sUserDao.save(userEntity);

        HashMap<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("userName", dto.getLoginId());
        dataMap.put("password", randomPw);

        // 发送短信
        /*
         * if (dto.getOrgType().equals(OrgTypeEnum.P.getKey())) { sMSService.sendSMS(orgEntity.getPhone(),
         * MessageModelEnum.sms_P_registerSuccess, dataMap); } else if (dto.getOrgType().equals(OrgTypeEnum.Z.getKey()))
         * { sMSService.sendSMS(orgEntity.getPhone(), MessageModelEnum.sms_Z_registerSuccess, dataMap); }
         */

        // 存储用户角色
        SUserRoleEntity roleEntity = new SUserRoleEntity();
        roleEntity.setUserId(userEntity.getUserId());
        if (dto.getOrgType().equals(OrgTypeEnum.P.getKey())) {
            roleEntity.setRoleId(SystemConst.P_ROLE_ID);
        } else if (dto.getOrgType().equals(OrgTypeEnum.Z.getKey())) {
            if ("1".equalsIgnoreCase(dto.getBarcodeFlag())) {
                roleEntity.setRoleId(SystemConst.SALE_ROLE_ID);

            } else {
                roleEntity.setRoleId(SystemConst.Z_ROLE_ID);
            }
        }
        sUserRoleDao.save(roleEntity);
        return ResultDtoFactory.toAck(EErrorCode.SUCCESS);
    }

    @Override
    @Transactional
    public ResultDto<String> saveSOrg(OrgSaveDto dto) {
        SUserEntity currUser = (SUserEntity) AuthorityContext.getCurrentUser();
        // 查找是否已经有该编号商家
        Long orgId = sOrgDao.findOrgIdByOrgCode(dto.getOrgCode());
        if (orgId != null) {
            return ResultDtoFactory.toNack("商家编号已被占用");
        }
        if (!sOrgDao.getByOrgName(dto.getOrgName()).isEmpty()) {
            return ResultDtoFactory.toNack("商家已存在");
        }
        SOrgEntity sOrg = new SOrgEntity();
        sOrg.setOrgCode(dto.getOrgCode());
        sOrg.setOrgName(dto.getOrgName());
        sOrg.setOrgType(dto.getOrgType());
        sOrg.setStatus(dto.getStatus());
        sOrg.setPhone(dto.getPhone());
        sOrg.setContact(dto.getContact());
        sOrg.setBusinesser(dto.getBusinesser());
        sOrg.setRegistrationLicense(dto.getRegistrationLicense());
        sOrg.setServicePhone(dto.getServicePhone());
        sOrg.setCreateDate(new Date());
        sOrg.setCreateId(currUser.getUserId());
        if (dto.getIntroduce() != null && !"".equals(dto.getIntroduce())) {
            sOrg.setIntroduce(dto.getIntroduce());
        }
        SOrgEntity newOrg = sOrgDao.save(sOrg);
        SUserEntity user = new SUserEntity();
        user.setOrgId(newOrg.getOrgId());
        user.setLoginId(dto.getOrgCode());
        user.setPassword(EncryptUtil.encryptMd5(EncryptUtil.encryptMd5("123456"), String.valueOf(dto.getOrgCode())));
        user.setUserName(dto.getOrgName());
        user.setPhone(dto.getPhone());
        user.setStatus("1");
        user.setCreateDate(new Date());
        user.setCreateId(0L);
        SUserEntity newUser = sUserDao.save(user);
        SUserRoleEntity userRole = new SUserRoleEntity();
        userRole.setUserId(newUser.getUserId());
        userRole.setRoleId(sRoleInfoDao.findByRole("服务商角色").getRoleId());
        sUserRoleDao.save(userRole);
        return ResultDtoFactory.toAck(EErrorCode.SUCCESS);
    }

    @Override
    @Transactional
    public ResultDto<String> resetPwd(Long orgId) {
        SUserEntity userEntity = sUserDao.findOneByOrgId(orgId);
        if (userEntity != null) {
            userEntity.setPassword(EncryptUtil.encryptMd5(EncryptUtil.encryptMd5("123456"),
                    String.valueOf(userEntity.getLoginId())));
            sUserDao.save(userEntity);
            return ResultDtoFactory.toAck("密码重置成功");
        } else {
            throw new WRWException(EErrorCode.ENTITY_MEMBER_NOT_EXIST);
        }
    }

    @Override
    public void findSaler(final SearchSalerDto dto, final String orgType) {
        PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.ASC,
                "createDate"));
        Page<SOrgEntity> page = sOrgDao.findAll(new Specification<SOrgEntity>() {
            @Override
            public Predicate toPredicate(Root<SOrgEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (!WRWUtil.isEmpty(dto.getStatus())) {
                    predicates.add(cb.equal(root.<String> get("status"), dto.getStatus()));
                }
                if (!WRWUtil.isEmpty(orgType)) {
                    predicates.add(cb.equal(root.<String> get("orgType"), orgType));
                }
                if (!WRWUtil.isEmpty(dto.getOrgCode())) {
                    predicates.add(cb.like(root.<String> get("orgCode"), "%" + dto.getOrgCode() + "%"));
                }
                if (!WRWUtil.isEmpty(dto.getOrgName())) {
                    predicates.add(cb.like(root.<String> get("orgName"), "%" + dto.getOrgName() + "%"));
                }
                if (!WRWUtil.isEmpty(dto.getContact())) {
                    predicates.add(cb.like(root.<String> get("contact"), "%" + dto.getContact() + "%"));
                }
                if (!WRWUtil.isEmpty(dto.getPhone())) {
                    predicates.add(cb.like(root.<String> get("phone"), "%" + dto.getPhone() + "%"));
                }
                if (!WRWUtil.isEmpty(dto.getBusinesser())) {
                    predicates.add(cb.like(root.<String> get("businesser"), "%" + dto.getBusinesser() + "%"));
                }
                if (!WRWUtil.isEmpty(dto.getRegistrationLicense())) {
                    predicates.add(cb.like(root.<String> get("registrationLicense"), "%" + dto.getRegistrationLicense()
                            + "%"));
                }

                Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate);
                return query.getRestriction();
            }
        }, pageable);
        dto.setTotalRecord(page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setList(buildSalerDtos(page.getContent()));
    }

    @Override
    public ResultDto<String> getOrgCode() {
        List<SOrgEntity> sOrgs = sOrgDao.findOrgNew(OrgTypeEnum.S.getKey());
        StringBuffer orgCode = new StringBuffer("S");
        if (sOrgs.isEmpty()) {
            orgCode.append("1");
        } else {
            String oldCode = sOrgs.get(0).getOrgCode();
            orgCode.append(Integer.parseInt(oldCode.substring(oldCode.indexOf(OrgTypeEnum.S.getKey()) + 1,
                    oldCode.length())) + 1);
        }
        return ResultDtoFactory.toAck("get success!", orgCode.toString());
    }

    @Override
    public List<SOrgEntity> findByType(String orgType) {
        return sOrgDao.findByOrgType(orgType);
    }

    @Override
    @Transactional
    public ResultDto<String> editorOrg(OrgSaveDto dto) {
        try {
			if (!sOrgDao.getByOrgNameExceptSelf(dto.getOrgName(), dto.getOrgCode()).isEmpty()) {
                return ResultDtoFactory.toNack("商家已存在");
            } else {
				String introduce = null;
            	if (dto.getIntroduce() != null && !"".equals(dto.getIntroduce())) {
                	introduce = dto.getIntroduce();
            	}
                sOrgDao.updateOrg(dto.getOrgName(), dto.getPhone(), dto.getContact(), dto.getBusinesser(),
                        dto.getRegistrationLicense(), dto.getServicePhone(), introduce, dto.getOrgCode());
                return ResultDtoFactory.toAck("修改成功！");
            }

        } catch (Exception e) {
            return ResultDtoFactory.toNack("修改失败！");
        }
    }

    @Override
    @Transactional
    public ResultDto<String> deleteOrg(String orgCode) {
        SOrgEntity org = sOrgDao.findByOrgCode(orgCode);
        if (org != null) {
            List<PProductEntity> productList = pProductDao.findByOrgIdAndStatus(org.getOrgId());
            if (productList.isEmpty()) {
                try {
                    sOrgDao.updateStatus(orgCode);
                    sUserDao.deleteUser(org.getOrgId());
                    return ResultDtoFactory.toAck("删除成功！");
                } catch (Exception e) {
                    LOGGER.error(e.toString());
                    return ResultDtoFactory.toNack("删除失败！");
                }
            } else {
                return ResultDtoFactory.toNack("该商家还有关联商品，无法删除！");
            }
        } else {
            return ResultDtoFactory.toNack("服务商不存在！");
        }
    }
}
