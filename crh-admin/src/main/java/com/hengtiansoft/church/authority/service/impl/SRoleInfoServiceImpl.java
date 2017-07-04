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

import com.hengtiansoft.church.SystemConst;
import com.hengtiansoft.church.authority.constant.TreeNodeBean;
import com.hengtiansoft.church.authority.dto.SRoleInfoDto;
import com.hengtiansoft.church.authority.dto.SRoleInfoSaveAndUpdateDto;
import com.hengtiansoft.church.authority.dto.SRoleInfoSearchDto;
import com.hengtiansoft.church.authority.service.SRoleInfoService;
import com.hengtiansoft.church.dao.SFunctionInfoDao;
import com.hengtiansoft.church.dao.SRoleFunctionDao;
import com.hengtiansoft.church.dao.SRoleInfoDao;
import com.hengtiansoft.church.entity.SFunctionInfoEntity;
import com.hengtiansoft.church.entity.SRoleFunctionEntity;
import com.hengtiansoft.church.entity.SRoleInfoEntity;
import com.hengtiansoft.church.enums.StatusEnum;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.enumeration.EErrorCode;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.WRWUtil;

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
                // FIDN ALL ROLES which are useful
                predicates.add(cb.equal(root.<String> get("status"), StatusEnum.NORMAL.getCode()));
                //Query the role ID is larger than the role of the platform role ID
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
        // The default root node is 0
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
        if (count > 0) {// Not empty, indicating the role name in the data table
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
        // save role
        sRoleInfoDao.save(entity);

        // Separates values from the left menu bar from the functionIds
        List<Long> listChildrens = new ArrayList<Long>();

        List<SFunctionInfoEntity> parents = sFunctionInfoDao.findByFunctionIdIn(functionIds);
        // get functionId's parent node's id
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
        // Get child nodes
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
                if (count > 0) {// Not empty, indicating the role name in the data table
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

        // Separates values from the left menu bar from the functionIds
        List<Long> listChildrens = new ArrayList<Long>();

        List<SFunctionInfoEntity> parents = sFunctionInfoDao.findByFunctionIdIn(functionIds);
        // get functionId's parent node's id
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
        //get child's node
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
        // can't delete System's role
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
