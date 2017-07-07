package com.hengtiansoft.church.resource.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.church.common.constants.OprationTypeConstants;
import com.hengtiansoft.church.common.constants.SortConstants;
import com.hengtiansoft.church.common.util.QueryUtil;
import com.hengtiansoft.church.dao.ResourceDao;
import com.hengtiansoft.church.entity.ResourceEntity;
import com.hengtiansoft.church.enums.StatusEnum;
import com.hengtiansoft.church.resource.dto.ResourceListDto;
import com.hengtiansoft.church.resource.dto.ResourceSaveDto;
import com.hengtiansoft.church.resource.dto.ResourceSearchDto;
import com.hengtiansoft.church.resource.service.ResourceAdminService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.authority.domain.UserInfo;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.util.BasicUtil;

@Service
public class ResourceAdminServiceImpl implements ResourceAdminService {

    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public void searchResource(final ResourceSearchDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder conditionSql = new StringBuilder("");
        StringBuilder countSql = new StringBuilder("");
        StringBuilder sql = new StringBuilder(" select id,image,link,title,sort from resource where 1=1 ");
        countSql.append(" select count(1) from ( ").append(sql);
        conditionSql.append(" and del_flag = '1' order by sort ");
        Query query = entityManager.createNativeQuery(sql.append(conditionSql).toString());
        QueryUtil.processParamForQuery(query, param);
        Query countQuery = entityManager.createNativeQuery(countSql.append(conditionSql).append(" ) a").toString());
        QueryUtil.processParamForQuery(countQuery, param);
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        query.setFirstResult(dto.getPageSize() * (dto.getCurrentPage() - 1));
        query.setMaxResults(dto.getPageSize());
        dto.setTotalRecord(totalRecord.longValue());
        dto.setTotalPages(totalRecord.intValue() % dto.getPageSize() == 0 ? totalRecord.intValue() / dto.getPageSize()
                : ((totalRecord.intValue() / dto.getPageSize()) + 1));
        dto.setList(buildResourceList(query.getResultList()));
    }

    private List<ResourceListDto> buildResourceList(List<Object[]> list) {
        List<ResourceListDto> resourceList = new ArrayList<ResourceListDto>();
        for (Object[] obj : list) {
            ResourceListDto dto = new ResourceListDto();
            dto.setId(BasicUtil.objToLong(obj[0]));
            dto.setImage(BasicUtil.objToString(obj[1]));
            dto.setLink(BasicUtil.objToString(obj[2]));
            dto.setTitle(BasicUtil.objToString(obj[3]));
            dto.setIndex(BasicUtil.objToString(obj[4]));
            dto.setTotalRecords(list.size() + "");
            resourceList.add(dto);
        }
        return resourceList;
    }

    @Override
    @Transactional
    public ResultDto<?> deleteResource(Long id) {
        UserInfo userInfo = AuthorityContext.getCurrentUser();
        Long userId = 0L;
        if (userInfo != null) {
            userId = userInfo.getUserId();
        }
        ResourceEntity resource = resourceDao.findOne(id);
        resource.setDelFlag(StatusEnum.DELETE.getCode());
        resource.setModifyId(userId);
        resource.setModifyDate(new Date());
        resourceDao.save(resource);
        resourceDao.updateSort(resource.getSort());
        return ResultDtoFactory.toAck("Delete Success!", null);
    }

    @Override
    public ResourceListDto resourceDetail(Long id) {
        ResourceListDto dto = new ResourceListDto();
        ResourceEntity resource = resourceDao.findOne(id);
        dto.setId(id);
        dto.setImage(resource.getImage());
        dto.setIndex(resource.getSort());
        dto.setLink(resource.getLink());
        dto.setTitle(resource.getTitle());
        return dto;
    }

    @Override
    @Transactional
    public ResultDto<?> adjustSort(Long id, String type) {
        ResourceEntity originResource = resourceDao.findOne(id);
        Integer originSort = Integer.parseInt(originResource.getSort());
        ResourceEntity nextResource = null;
        if (OprationTypeConstants.UP_SORT.equals(type)) {
            // up sort
            nextResource = resourceDao.findBySort(originSort - 1 + "");
            originResource.setSort(originSort - 1 + "");
        } else {
            // down sort
            nextResource = resourceDao.findBySort(originSort + 1 + "");
            originResource.setSort(originSort + 1 + "");
        }
        resourceDao.save(originResource);
        nextResource.setSort(originSort.toString());
        resourceDao.save(nextResource);
        return ResultDtoFactory.toAck("Success!", null);
    }

    @Override
    @Transactional
    public ResultDto<?> saveResource(ResourceSaveDto dto) {
        if (!dto.getLink().contains("http://") && !dto.getLink().contains("https://")) {
            return ResultDtoFactory
                    .toNack("The URL format is incorrect, please check it again.(e.g, http://www.google.com or https://www.google.com)",
                            null);
        }
        if (StringUtils.isNotEmpty(dto.getTitle()) && dto.getTitle().length() > 50) {
            return ResultDtoFactory.toNack("The maximum length of the title is 50 bytes!");
        }
        ResourceEntity resource = null;
        UserInfo userInfo = AuthorityContext.getCurrentUser();
        Long userId = 0L;
        if (userInfo != null) {
            userId = userInfo.getUserId();
        }
        if (OprationTypeConstants.SAVE_OPRATION.equals(dto.getType())) {
            // Save
            resource = new ResourceEntity();
            resource.setCreateDate(new Date());
            resource.setCreateId(userId);
            resource.setDelFlag(StatusEnum.NORMAL.getCode());
            ResourceEntity lastSortResource = resourceDao.findLastSortEntity();
            if (lastSortResource == null) {
                resource.setSort(SortConstants.FIRST_SORT);
            } else {
                resource.setSort(Integer.parseInt(lastSortResource.getSort()) + 1 + "");
            }
        } else {
            // Editor
            resource = resourceDao.findOne(dto.getId());
            resource.setModifyDate(new Date());
            resource.setModifyId(userId);
        }
        resource.setLink(dto.getLink());
        resource.setTitle(dto.getTitle());
        resource.setImage(dto.getImage());
        resourceDao.save(resource);
        return ResultDtoFactory.toAck("Operation is successful!", null);
    }
}
