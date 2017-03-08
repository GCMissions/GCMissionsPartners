package com.hengtiansoft.church.authority.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.church.SystemConst;
import com.hengtiansoft.church.authority.dto.SUserDto;
import com.hengtiansoft.church.authority.dto.SUserSaveAndUpdateDto;
import com.hengtiansoft.church.authority.dto.SUserSearchDto;
import com.hengtiansoft.church.authority.dto.SUserUpdateDto;
import com.hengtiansoft.church.authority.service.SUserService;
import com.hengtiansoft.church.common.util.QueryUtil;
import com.hengtiansoft.church.dao.SRoleInfoDao;
import com.hengtiansoft.church.dao.SUserDao;
import com.hengtiansoft.church.dao.SUserRoleDao;
import com.hengtiansoft.church.entity.SRoleInfoEntity;
import com.hengtiansoft.church.entity.SUserEntity;
import com.hengtiansoft.church.entity.SUserRoleEntity;
import com.hengtiansoft.church.enums.StatusEnum;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.enumeration.EErrorCode;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.EncryptUtil;
import com.hengtiansoft.common.util.WRWUtil;

/**
 * Class Name: SUserServiceImpl Description: 账户管理业务实现类
 * 
 * @author zhisongliu
 */
@Service
public class SUserServiceImpl implements SUserService {

    @Autowired
    private SUserDao sUserDao;

    @Autowired
    private SRoleInfoDao sRoleInfoDao;

    @Autowired
    private SUserRoleDao sUserRoleDao;

    @Autowired
    private EntityManager entityManager;

    @Override
    public void search(final SUserSearchDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder sbSql = new StringBuilder();
        StringBuilder countSql = new StringBuilder();
        StringBuilder paramSql = new StringBuilder();
        sbSql.append("select s.* from user s left join user_role r on r.USER_ID=s.ID where 1=1 and s.ORG_ID ="+SystemConst.PLATFORM_USER_ORG_ID + " and  s.STATUS != "+StatusEnum.REMOVED.getCode());
        countSql.append("select count(1) from (select s.* from user s left join user_role r on r.USER_ID=s.ID where 1=1 and s.ORG_ID ="+SystemConst.PLATFORM_USER_ORG_ID + " and s.STATUS != "+StatusEnum.REMOVED.getCode());
        if (!WRWUtil.isEmpty(dto.getUserName())) {
            paramSql.append(" and s.USER_NAME like :userName");
            param.put("userName", "%" + dto.getUserName() + "%");
        }
        // 转化成小写比较
        if (!WRWUtil.isEmpty(dto.getLoginId())) {
            paramSql.append(" and s.LOGIN_ID like :loginId");
            param.put("loginId", "%" + dto.getLoginId() + "%");
        }
        if (!WRWUtil.isEmpty(dto.getPhone())) {
            paramSql.append(" and s.PHONE like :phone");
            param.put("phone", "%" + dto.getPhone() + "%");
        }
        if (!WRWUtil.isEmpty(dto.getEmail())) {
            paramSql.append(" and s.EMAIL like :email");
            param.put("email", "%" + dto.getEmail() + "%");
        }
        if (dto.getRoleId() != null) {
            paramSql.append(" and r.ROLE_ID  = :roleId");
            param.put("roleId", dto.getRoleId());
        }
        sbSql.append(paramSql.toString()).append(" group by s.ID order by s.CREATE_DATE desc");
        countSql.append(paramSql.toString() + " group by s.ID ) p" );

        Query query = entityManager.createNativeQuery(sbSql.toString(), SUserEntity.class);
        QueryUtil.processParamForQuery(query, param);
        Query countQuery = entityManager.createNativeQuery(countSql.toString());
        QueryUtil.processParamForQuery(countQuery, param);

        query.setFirstResult(dto.getPageSize() * (dto.getCurrentPage() - 1));
        query.setMaxResults(dto.getPageSize());
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        QueryUtil.buildPagingDto(dto, totalRecord.longValue(), buildUserDtos(query.getResultList()));
    }

    private List<SUserDto> buildUserDtos(List<SUserEntity> content) {
        List<SUserDto> list = new ArrayList<SUserDto>();
        for (SUserEntity entity : content) {
            SUserDto dto = ConverterService.convert(entity, new SUserDto());
            dto.setId(entity.getUserId());
            dto.setCreateTime(DateTimeUtil.parseDateToString(entity.getCreateDate(), DateTimeUtil.SIMPLE_FMT_MINUTE));
            dto.setStatus(StatusEnum.getTextByCode(entity.getStatus()));
            list.add(dto);
        }
        return list;
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<String> save(SUserSaveAndUpdateDto dto) {
        SUserEntity entity = new SUserEntity();
        // 验证
        if (WRWUtil.isEmpty(dto.getUserName())) {
            throw new WRWException(EErrorCode.USER_NAME_VALUE_IS_NULL);
        } else {
            entity.setUserName(dto.getUserName());
        }

        if (WRWUtil.isEmpty(dto.getLoginId())) {
            throw new WRWException(EErrorCode.LOGIN_ID_VALUE_IS_NULL);
        } else {
            // 验证该登录名是否存在
            int countLoginId = sUserDao.findbyLoginIdAndOrgId(dto.getLoginId());
            if (countLoginId > 0) {
                throw new WRWException(EErrorCode.ENTITY_USER_IS_EXIST);
            }
            entity.setLoginId(dto.getLoginId());
        }
        if (WRWUtil.isEmpty(dto.getPhone())) {
            throw new WRWException(EErrorCode.PHONE_VALUE_IS_NULL);
        } else {
            entity.setPhone(dto.getPhone());
        }
        if (WRWUtil.isEmpty(dto.getEmail())) {
            throw new WRWException(EErrorCode.EMAIL_VALUE_IS_NULL);
        } else {
            entity.setEmail(dto.getEmail());
        }
        if (!WRWUtil.isEmpty(dto.getRemark())) {
            entity.setRemark(dto.getRemark());
        }
        if (!WRWUtil.isEmpty(dto.getStatus())) {
            entity.setStatus(StatusEnum.getCodeByText(dto.getStatus()));
        }
        // 以手机号后六位作为默认密码
        String myPwd = "";
        if (!WRWUtil.isEmpty(dto.getPassword())) {
            myPwd = dto.getPassword();
        } else {
            String phone = entity.getPhone();
            myPwd = phone.substring(phone.length() - 6, phone.length());
        }
        String onePassword = EncryptUtil.encryptMd5(myPwd);
        String password2 = EncryptUtil.encryptMd5(onePassword, dto.getLoginId());
        entity.setPassword(password2);

        if (dto.getRoleIds() == null || dto.getRoleIds().size() == 0) {
            throw new WRWException(EErrorCode.USER_ROLE_VALUE_IS_NULL);
        }
        entity.setCreateDate(new Date());
        entity.setCreateId(AuthorityContext.getCurrentUser().getUserId());
        entity.setOrgId(SystemConst.PLATFORM_USER_ORG_ID);
        sUserDao.save(entity);

        List<SUserRoleEntity> list = new ArrayList<SUserRoleEntity>(dto.getRoleIds().size());
        for (Long roleId : dto.getRoleIds()) {
            SUserRoleEntity rEntity = new SUserRoleEntity();
            rEntity.setRoleId(roleId);
            rEntity.setUserId(entity.getUserId());
            list.add(rEntity);
        }
        sUserRoleDao.save(list);

        return ResultDtoFactory.toAck(EErrorCode.SUCCESS);
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<String> update(SUserUpdateDto dto) {
        // 判断是否是当前用户,
        if (dto.getId().equals(AuthorityContext.getCurrentUser().getUserId())) {
            throw new WRWException(EErrorCode.USER_UPDATE_IS_USE);
        }
        SUserEntity entity = sUserDao.findOne(dto.getId());
        // 验证
        if (WRWUtil.isEmpty(dto.getUserName())) {
            throw new WRWException(EErrorCode.USER_NAME_VALUE_IS_NULL);
        } else {
            entity.setUserName(dto.getUserName());
        }
        if (WRWUtil.isEmpty(dto.getPhone())) {
            throw new WRWException(EErrorCode.LOGIN_ID_VALUE_IS_NULL);
        } else {
            entity.setPhone(dto.getPhone());
        }
        if (!"on".equals(dto.getLockUser())) {
            entity.setStatus(StatusEnum.NORMAL.getCode());
        }
        // 判断传过来的密码和数据库中的是否相等,如果相等，则没有更新密码，如果不相等，则更改了密码
        if (!WRWUtil.isEmpty(dto.getPassword())) {
            String updatePwd = EncryptUtil.encryptMd5(dto.getPassword());
            if (!entity.getPassword().equals(updatePwd)) {
                String password2 = EncryptUtil.encryptMd5(updatePwd, entity.getLoginId());
                entity.setPassword(password2);
            }
        }

        if (dto.getRoleIds() == null || dto.getRoleIds().size() == 0) {
            throw new WRWException(EErrorCode.USER_ROLE_VALUE_IS_NULL);
        }
        sUserDao.save(entity);

        List<SUserRoleEntity> list = new ArrayList<SUserRoleEntity>(dto.getRoleIds().size());
        for (Long roleId : dto.getRoleIds()) {
            SUserRoleEntity rEntity = new SUserRoleEntity();
            rEntity.setRoleId(roleId);
            rEntity.setUserId(entity.getUserId());
            list.add(rEntity);
        }
        sUserRoleDao.deleteByUserId(entity.getUserId());
        sUserRoleDao.save(list);

        return ResultDtoFactory.toAck(EErrorCode.SUCCESS);
    }

    @Override
    public List<SRoleInfoEntity> findRoles() {
        // 去掉区域平台商和终端配送商的ID
        return sRoleInfoDao.findAllByStatus();
    }

    @Override
    public List<SRoleInfoEntity> findRolesById(Long id) {
        List<Long> roleIds = new ArrayList<Long>();
        List<SUserRoleEntity> list = sUserRoleDao.findByUserId(id);
        for (SUserRoleEntity sUserRoleEntity : list) {
            roleIds.add(sUserRoleEntity.getRoleId());
        }

        return sRoleInfoDao.findAll(roleIds);
    }

    @Override
    public SUserSaveAndUpdateDto findById(Long id) {
        SUserEntity entity = sUserDao.findOne(id);
        SUserSaveAndUpdateDto dto = ConverterService.convert(entity, new SUserSaveAndUpdateDto());
        dto.setId(entity.getUserId());
        dto.setStatus(StatusEnum.getTextByCode(entity.getStatus()));

        List<SUserRoleEntity> list = sUserRoleDao.findByUserId(id);
        List<Long> roleIds = new ArrayList<>(list.size());
        for (SUserRoleEntity sUserRoleEntity : list) {
            roleIds.add(sUserRoleEntity.getRoleId());
        }
        dto.setRoleIds(roleIds);
        return dto;
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<String> detele(Long id) {
        // 判断该ID是否为当前登录ID,
        if (id.equals(AuthorityContext.getCurrentUser().getUserId())) {
            throw new WRWException(EErrorCode.USER_DELETE_IS_USING);
        }
        // 判断该ID是否为系统adminID,
        if (id.equals(SystemConst.SYSTEM_USER_ID)) {
            throw new WRWException(EErrorCode.SYSTEM_USER_NOT_DELETE);
        }
        SUserEntity entity = sUserDao.findOne(id);
        // 将状态置为3，则就是逻辑删除
        entity.setStatus(StatusEnum.REMOVED.getCode());
        sUserDao.save(entity);
        return ResultDtoFactory.toAck(EErrorCode.SUCCESS);
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<String> resetPwd(String loginId) {
        SUserEntity entity = sUserDao.findByLoginId(loginId);
        if (null == entity) {
            throw new WRWException(EErrorCode.ENTITY_USER_NOT_EXIST);
        } else {
            entity.setPassword(EncryptUtil.encryptMd5("e10adc3949ba59abbe56e057f20f883e", String.valueOf(loginId)));
            sUserDao.save(entity);
            return ResultDtoFactory.toAck(EErrorCode.SUCCESS);
        }
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<String> selfUpdate(SUserSaveAndUpdateDto dto) {
        // 判断是否是当前用户
        if (!dto.getId().equals(AuthorityContext.getCurrentUser().getUserId())) {
            throw new WRWException(EErrorCode.USER_UPDATE_NOT_USING);
        }
        SUserEntity entity = sUserDao.findOne(dto.getId());
        // 验证 ,只有平台（orgId=0）才做手机号姓名验证
        if (entity.getOrgId().equals(0L)) {
            if (WRWUtil.isEmpty(dto.getUserName())) {
                throw new WRWException(EErrorCode.USER_NAME_VALUE_IS_NULL);
            } else {
                entity.setUserName(dto.getUserName());
            }
            if (WRWUtil.isEmpty(dto.getPhone())) {
                throw new WRWException(EErrorCode.PHONE_VALUE_IS_NULL);
            } else {
                entity.setPhone(dto.getPhone());
            }
        }

        if (!WRWUtil.isEmpty(dto.getRemark())) {
            entity.setRemark(dto.getRemark());
        }
        if (!WRWUtil.isEmpty(dto.getStatus())) {
            entity.setStatus(StatusEnum.getCodeByText(dto.getStatus()));
        }
        // 判断传过来的密码和数据库中的是否相等,如果相等，则没有更新密码，如果不相等，则更改了密码
        // 先判断传进来的密码是否为空，若为空则表示不更改密码
        if (!WRWUtil.isEmpty(dto.getPassword()) && !entity.getPassword().equals(dto.getPassword())) {
            String password2 = EncryptUtil.encryptMd5(dto.getPassword(), entity.getLoginId());
            entity.setPassword(password2);
        }
        sUserDao.save(entity);

        return ResultDtoFactory.toAck(EErrorCode.SUCCESS);
    }
}
