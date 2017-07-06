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
import com.hengtiansoft.common.util.BasicUtil;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.EncryptUtil;
import com.hengtiansoft.common.util.WRWUtil;

/**
 * Class Name: SUserServiceImpl Description: User management implementation class
 * 
 * @author tao chen
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

    @SuppressWarnings("unchecked")
    @Override
    public void search(final SUserSearchDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder sbSql = new StringBuilder();
        StringBuilder countSql = new StringBuilder();
        StringBuilder paramSql = new StringBuilder();
        sbSql.append("select s.ID,s.login_id,s.email,s.status,s.create_date,group_concat(ro.ROLE separator '#&#') from user s left join user_role r on r.USER_ID=s.ID left join role_info ro on r.ROLE_ID=ro.ROLE_ID where 1=1 and s.ORG_ID ="+SystemConst.PLATFORM_USER_ORG_ID + " and  s.STATUS != "+StatusEnum.REMOVED.getCode());
        countSql.append("select count(1) from (select s.ID,s.login_id,s.email,s.status,s.create_date,ro.ROLE from user s left join user_role r on r.USER_ID=s.ID left join role_info ro on r.ROLE_ID=ro.ROLE_ID where 1=1 and s.ORG_ID ="+SystemConst.PLATFORM_USER_ORG_ID + " and  s.STATUS != "+StatusEnum.REMOVED.getCode());
        if (!WRWUtil.isEmpty(dto.getUserName())) {
            paramSql.append(" and s.USER_NAME like :userName");
            param.put("userName", "%" + dto.getUserName() + "%");
        }
        // Convert lowercase for comparison
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

        Query query = entityManager.createNativeQuery(sbSql.toString());
        QueryUtil.processParamForQuery(query, param);
        Query countQuery = entityManager.createNativeQuery(countSql.toString());
        QueryUtil.processParamForQuery(countQuery, param);

        query.setFirstResult(dto.getPageSize() * (dto.getCurrentPage() - 1));
        query.setMaxResults(dto.getPageSize());
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        QueryUtil.buildPagingDto(dto, totalRecord.longValue(), buildUserDtos(query.getResultList()));
    }

    private List<SUserDto> buildUserDtos(List<Object[]> content) {
        List<SUserDto> list = new ArrayList<SUserDto>();
        for (Object[] obj : content) {
            SUserDto dto = new SUserDto();
            dto.setLoginId(BasicUtil.objToString(obj[1]));
            dto.setEmail(BasicUtil.objToString(obj[2]));
            String roles = BasicUtil.objToString(obj[5]);
            String[] roleList = roles.split("#&#");
            String role = "";
            for (int i = 0; i < roleList.length; i++) {
                if (i == roleList.length - 1) {
                    role += roleList[i];
                } else {
                    role += roleList[i] + ",";
                }
            }
            dto.setRole(role);
            dto.setId(BasicUtil.objToLong(obj[0]));
            dto.setCreateTime(DateTimeUtil.parseDateToString((Date) obj[4], DateTimeUtil.SIMPLE_FMT_MINUTE));
            dto.setStatus(StatusEnum.getTextByCode(BasicUtil.objToString(obj[3])));
            list.add(dto);
        }
        return list;
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<String> save(SUserSaveAndUpdateDto dto) {
        SUserEntity entity = new SUserEntity();
        if (WRWUtil.isEmpty(dto.getLoginId())) {
            throw new WRWException(EErrorCode.LOGIN_ID_VALUE_IS_NULL);
        } else {
            // Verify that the user name exists
            int countLoginId = sUserDao.findbyLoginIdAndOrgId(dto.getLoginId());
            if (countLoginId > 0) {
                throw new WRWException(EErrorCode.ENTITY_USER_IS_EXIST);
            }
            entity.setLoginId(dto.getLoginId());
        }
        if (WRWUtil.isEmpty(dto.getEmail())) {
            throw new WRWException(EErrorCode.EMAIL_VALUE_IS_NULL);
        } else {
            List<SUserEntity> userList = sUserDao.findByEmailAndNotEqStatus(dto.getEmail(), StatusEnum.REMOVED.getCode());
            if (!userList.isEmpty()) {
                throw new WRWException(EErrorCode.EMAIL_VALUE_IS_EXIST);
            } else {
                entity.setEmail(dto.getEmail());
            }
        }
        if (!WRWUtil.isEmpty(dto.getRemark())) {
            entity.setRemark(dto.getRemark());
        }
        if (!WRWUtil.isEmpty(dto.getStatus())) {
            entity.setStatus(StatusEnum.getCodeByText(dto.getStatus()));
        }
        // The last six digits of the phone number as the default password
        String myPwd = "";
        if (!WRWUtil.isEmpty(dto.getPassword())) {
            myPwd = dto.getPassword();
        } else {
            myPwd = "123456";
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
        SUserEntity entity = sUserDao.findOne(dto.getId());
        if (!"on".equals(dto.getLockUser())) {
            entity.setStatus(StatusEnum.NORMAL.getCode());
        }
        // To determine whether the password entered and the password in the database are equal. 
       // If they are equal, the password is not updated and the password is changed if they are not equal.
        if (!WRWUtil.isEmpty(dto.getPassword())) {
            String updatePwd = EncryptUtil.encryptMd5(dto.getPassword());
            if (!entity.getPassword().equals(updatePwd)) {
                String password2 = EncryptUtil.encryptMd5(updatePwd, entity.getLoginId());
                entity.setPassword(password2);
            }
        }

        if (!WRWUtil.isEmpty(dto.getEmail())) {
            entity.setEmail(dto.getEmail());
        } else {
            throw new WRWException(EErrorCode.EMAIL_VALUE_IS_NULL);
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
        // Remove the ID of the regional platform vendor 
        //and the ID of the terminal distributor
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
        // Determines whether or not the ID is the current login ID,
        if (id.equals(AuthorityContext.getCurrentUser().getUserId())) {
            throw new WRWException(EErrorCode.USER_DELETE_IS_USING);
        }
        // Determines whether the ID is the ID of the system admin,
        if (id.equals(SystemConst.SYSTEM_USER_ID)) {
            throw new WRWException(EErrorCode.SYSTEM_USER_NOT_DELETE);
        }
        SUserEntity entity = sUserDao.findOne(id);
        // Setting the status to 3 is indicated as deletion
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
        if (!dto.getId().equals(AuthorityContext.getCurrentUser().getUserId())) {
            throw new WRWException(EErrorCode.USER_UPDATE_NOT_USING);
        }
        SUserEntity entity = sUserDao.findOne(dto.getId());
        // Only the platform (orgId = 0) to do phone number name verification
        if (entity.getOrgId().equals(0L)) {
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
        // To determine whether the password entered and the password in the database are equal. 
        // If they are equal, the password is not updated and the password is changed if they are not equal.
        // First determine whether the incoming password is empty, if it is empty that does not change the password
        if (!WRWUtil.isEmpty(dto.getPassword()) && !entity.getPassword().equals(dto.getPassword())) {
            String password2 = EncryptUtil.encryptMd5(dto.getPassword(), entity.getLoginId());
            entity.setPassword(password2);
        }
        sUserDao.save(entity);

        return ResultDtoFactory.toAck(EErrorCode.SUCCESS);
    }
}
