package com.hengtiansoft.church.authority.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.hengtiansoft.church.authority.constant.TreeNodeBean;
import com.hengtiansoft.church.authority.dto.SRoleInfoDto;
import com.hengtiansoft.church.authority.dto.SRoleInfoSaveAndUpdateDto;
import com.hengtiansoft.church.authority.dto.SRoleInfoSearchDto;
import com.hengtiansoft.church.authority.service.SRoleInfoService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.enumeration.EErrorCode;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.SystemConst;
import com.hengtiansoft.wrw.dao.SFunctionInfoDao;
import com.hengtiansoft.wrw.dao.SRoleFunctionDao;
import com.hengtiansoft.wrw.dao.SRoleInfoDao;
import com.hengtiansoft.wrw.entity.SFunctionInfoEntity;
import com.hengtiansoft.wrw.entity.SRoleFunctionEntity;
import com.hengtiansoft.wrw.entity.SRoleInfoEntity;
import com.hengtiansoft.wrw.enums.StatusEnum;

@Service
public class SRoleInfoServiceImpl implements SRoleInfoService {
    @Autowired
    private SRoleInfoDao     sRoleInfoDao;

    @Autowired
    private SFunctionInfoDao sFunctionInfoDao;

    @Autowired
    private SRoleFunctionDao sRoleFunctionDao;

    @Override
    public void search(final SRoleInfoSearchDto dto) {
        PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.DESC, "createDate"));

        Page<SRoleInfoEntity> page = sRoleInfoDao.findAll(new Specification<SRoleInfoEntity>() {
            @Override
            public Predicate toPredicate(Root<SRoleInfoEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (!WRWUtil.isEmpty(dto.getRole())) {
                    predicates.add(cb.like(root.<String> get("role"), "%" + dto.getRole() + "%"));
                }
                // 查询出状态为启用的角色
                predicates.add(cb.equal(root.<String> get("status"), StatusEnum.NORMAL.getCode()));
                // 查询出角色ID大于平台角色ID的角色
//                predicates.add(cb.ge(root.<Long> get("roleId"), SystemConst.PLATFORM_ROLE_ID));
                Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate);
                return query.getRestriction();
            }
        }, pageable);

        dto.setTotalRecord(page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setList(buildRoleDtos(page.getContent()));
    }

    private List<SRoleInfoDto> buildRoleDtos(List<SRoleInfoEntity> content) {
        List<SRoleInfoDto> list = new ArrayList<SRoleInfoDto>();
        for (SRoleInfoEntity info : content) {
            SRoleInfoDto dto = new SRoleInfoDto();
            dto.setRoleId(info.getRoleId());
            dto.setRole(info.getRole());
            dto.setStatus(StatusEnum.getTextByCode(info.getStatus()));
            dto.setCreateDate(DateTimeUtil.parseDateToString(info.getCreateDate(), DateTimeUtil.SIMPLE_FMT_MINUTE));
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<TreeNodeBean> getFunctionTree(String functionId) {
        // 默认根节点为0
        if (WRWUtil.isEmpty(functionId)) {
            functionId = SystemConst.TREE_ROOT_ID;
        }
        List<SFunctionInfoEntity> functionList = sFunctionInfoDao.findFunctionTreeList("," + functionId + ",");
        List<TreeNodeBean> treeList = new ArrayList<TreeNodeBean>();
        Map<Long, TreeNodeBean> nodeMap = new HashMap<Long, TreeNodeBean>();
        for (int i = functionList.size() - 1; i >= 0; i--) {
            SFunctionInfoEntity functionInfo = functionList.get(i);
            TreeNodeBean nodeBean = new TreeNodeBean();
            nodeBean.setId(functionInfo.getFunctionId());
            nodeBean.setPid(functionInfo.getParentId());
            nodeBean.setNodeName(functionInfo.getFunctionName());
            nodeBean.setLevel(functionInfo.getLevel());
            nodeMap.put(nodeBean.getId(), nodeBean);
            if (nodeMap.containsKey(functionInfo.getParentId())) {
                nodeMap.get(functionInfo.getParentId()).getChildrenList().add(nodeBean);
            } else {
                treeList.add(nodeBean);
            }
        }
        return treeList;
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<String> save(SRoleInfoSaveAndUpdateDto dto) {
        SRoleInfoEntity entity = new SRoleInfoEntity();
        String roleName = dto.getRole();
        if (WRWUtil.isEmpty(roleName)) {
            throw new WRWException(EErrorCode.ROLE_NAME_VALUE_IS_NULL);
        }
        if (roleName.length() > 20) {
            throw new WRWException(EErrorCode.ROLE_NAME_TOO_LONG);
        }
        int count = sRoleInfoDao.findByRoleAndStatus(roleName, StatusEnum.NORMAL.getCode());
        if (count > 0) {// 不为空，说明数据表中有这个角色名
            throw new WRWException(EErrorCode.ENTITY_ROLE_IS_EXIST);
        }
        List<Long> functionIds = dto.getFunctionIds();
        if (functionIds.isEmpty()) {
            throw new WRWException(EErrorCode.ROLE_FUNCTION_IS_NULL);
        }
        entity.setRole(roleName);
        if (!WRWUtil.isEmpty(dto.getDescription())) {
            if (dto.getDescription().length() > 20) {
                throw new WRWException(EErrorCode.ROLE_DESC_TOO_LONG);
            }
            entity.setDescription(dto.getDescription());
        }

        entity.setCreateDate(new Date());
        entity.setStatus(StatusEnum.NORMAL.getCode());
        // 保存角色
        sRoleInfoDao.save(entity);

        // 从functionIds中分离出不是左侧菜单栏的值
        List<Long> listChildrens = new ArrayList<Long>();

        List<SFunctionInfoEntity> parents = sFunctionInfoDao.findByFunctionIdIn(functionIds);
        // 获取functionIds中父节点的Id
        List<SRoleFunctionEntity> resultList = new ArrayList<SRoleFunctionEntity>();
        for (SFunctionInfoEntity info : parents) {
            if (info.getParentId() == SystemConst.ROLE_FUNCTION_PARENT_ID) {
                SRoleFunctionEntity roleFunction = new SRoleFunctionEntity();
                roleFunction.setFunctionId(info.getFunctionId());
                roleFunction.setRoleId(entity.getRoleId());
                resultList.add(roleFunction);
            } else {
                listChildrens.add(info.getFunctionId());
            }
        }
        if (resultList != null && resultList.size() > 0) {
            sRoleFunctionDao.save(resultList);
        }
        List<SRoleFunctionEntity> resultChildrens = new ArrayList<SRoleFunctionEntity>();
        // 获取子节点
        for (Long functionId : listChildrens) {
            SRoleFunctionEntity SRoleFunctionEntity = new SRoleFunctionEntity();
            SRoleFunctionEntity.setFunctionId(functionId);
            SRoleFunctionEntity.setRoleId(entity.getRoleId());
            resultChildrens.add(SRoleFunctionEntity);
        }

        if (resultChildrens != null || resultChildrens.size() > 0) {
            sRoleFunctionDao.save(resultChildrens);
        }

        return ResultDtoFactory.toAck(EErrorCode.SUCCESS);
    }

    @Override
    public SRoleInfoSaveAndUpdateDto findById(Long id) {
        SRoleInfoEntity info = sRoleInfoDao.findOne(id);
        List<SRoleFunctionEntity> list = sRoleFunctionDao.findAll(id);
        List<Long> functionIds = new ArrayList<Long>();
        for (SRoleFunctionEntity pfrf : list) {
            functionIds.add(pfrf.getFunctionId());
        }
        SRoleInfoSaveAndUpdateDto dto = new SRoleInfoSaveAndUpdateDto();
        dto.setFunctionIds(functionIds);
        dto.setRoleId(info.getRoleId());
        dto.setDescription(info.getDescription());
        dto.setRole(info.getRole());
        return dto;
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<String> update(SRoleInfoSaveAndUpdateDto dto) {
        SRoleInfoEntity info = sRoleInfoDao.findOne(dto.getRoleId());
        String roleName = dto.getRole();
        if (!WRWUtil.isEmpty(roleName)) {
            if (!roleName.equals(info.getRole())) {
                int count = sRoleInfoDao.findByRoleAndStatus(roleName, StatusEnum.NORMAL.getCode());
                if (count > 0) {// 不为空，说明数据表中有这个角色名
                    throw new WRWException(EErrorCode.ENTITY_ROLE_IS_EXIST);
                }
            }
            info.setRole(roleName);
        }
        info.setDescription(dto.getDescription());

        List<Long> functionIds = dto.getFunctionIds();
        if (functionIds.isEmpty()) {
            throw new WRWException(EErrorCode.ROLE_FUNCTION_IS_NULL);
        }
        sRoleInfoDao.save(info);

        sRoleFunctionDao.deleteByRoleId(dto.getRoleId());

        // 从functionIds中分离出不是左侧菜单栏的值
        List<Long> listChildrens = new ArrayList<Long>();

        List<SFunctionInfoEntity> parents = sFunctionInfoDao.findByFunctionIdIn(functionIds);
        // 获取functionIds中父节点的Id
        List<SRoleFunctionEntity> resultList = new ArrayList<SRoleFunctionEntity>();
        for (SFunctionInfoEntity functionInfo : parents) {
            if (functionInfo.getParentId() == SystemConst.ROLE_FUNCTION_PARENT_ID) {
                SRoleFunctionEntity SRoleFunctionEntity = new SRoleFunctionEntity();
                SRoleFunctionEntity.setFunctionId(functionInfo.getFunctionId());
                SRoleFunctionEntity.setRoleId(info.getRoleId());
                resultList.add(SRoleFunctionEntity);
            } else {
                listChildrens.add(functionInfo.getFunctionId());
            }
        }
        if (resultList != null && resultList.size() > 0) {
            sRoleFunctionDao.save(resultList);
        }

        List<SRoleFunctionEntity> resultChildrens = new ArrayList<SRoleFunctionEntity>();
        // 获取子节点
        for (Long functionId : listChildrens) {

            SRoleFunctionEntity SRoleFunctionEntity = new SRoleFunctionEntity();
            SRoleFunctionEntity.setFunctionId(functionId);
            SRoleFunctionEntity.setRoleId(info.getRoleId());
            resultChildrens.add(SRoleFunctionEntity);
        }
        sRoleFunctionDao.save(resultChildrens);
        return ResultDtoFactory.toAck(EErrorCode.SUCCESS);
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<String> delete(Long id) {
        // 系统角色无法删除
        if (SystemConst.PLATFORM_ROLE_ID.equals(id)) {
            throw new WRWException(EErrorCode.ROLE_SYSTEM_NOT_DELETE);
        }
        int countNum = sRoleInfoDao.findCountByRoleId(id);
        if (countNum > 0) {
            throw new WRWException(EErrorCode.ROLE_DELETE_IS_USING);
        }

        SRoleInfoEntity info = sRoleInfoDao.findOne(id);
        info.setStatus(StatusEnum.DELETE.getCode());
        sRoleInfoDao.save(info);
        return ResultDtoFactory.toAck(EErrorCode.SUCCESS);
    }

}
