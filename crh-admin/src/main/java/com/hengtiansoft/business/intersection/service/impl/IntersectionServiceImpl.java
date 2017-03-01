package com.hengtiansoft.business.intersection.service.impl;

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

import com.hengtiansoft.business.common.util.QueryUtil;
import com.hengtiansoft.business.intersection.dto.IntersectionListDto;
import com.hengtiansoft.business.intersection.dto.IntersectionSaveDto;
import com.hengtiansoft.business.intersection.dto.IntersectionSearchDto;
import com.hengtiansoft.business.intersection.service.IntersectionService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.SIntersectionDao;
import com.hengtiansoft.wrw.entity.SIntersectionEntity;
import com.hengtiansoft.wrw.entity.SUserEntity;
import com.hengtiansoft.wrw.enums.StatusEnum;

@Service
public class IntersectionServiceImpl implements IntersectionService {
    
    private static final String EDITOR = "2";
    
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private SIntersectionDao sIntersectionDao;

    @SuppressWarnings("unchecked")
    @Override
    public void searchIntersection(final IntersectionSearchDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder conditionSql = new StringBuilder("");
        StringBuilder countSql = new StringBuilder("");
        StringBuilder sql = new StringBuilder(
                " SELECT ID,NAME,CREATE_DATE FROM S_INTERSECTION WHERE STATUS = '1' ");
        countSql.append(" SELECT COUNT(1) FROM ( ").append(sql);
        
        // 合集名称
        if (!WRWUtil.isEmpty(dto.getName())) {
            String msg = dto.getName().trim();
            char escape = '\\';
            msg =msg.replace("\\", escape+"\\");
            msg =msg.replace("%", escape+"%");
            msg =msg.replace("_", escape+"_");
            conditionSql.append(" AND REPLACE(NAME,' ','') LIKE REPLACE(:NAME,' ','') ");
            param.put("NAME", "%"+msg+"%");   
        }
        // 创建时间
        if (!WRWUtil.isEmpty(dto.getStartDate())) {
            conditionSql.append(" AND CREATE_DATE >= :START ");
            param.put("START", DateTimeUtil.getDateBegin(dto.getStartDate()));
        }
        if (!WRWUtil.isEmpty(dto.getEndDate())) {
            conditionSql.append(" AND CREATE_DATE <= :END ");
            param.put("END", DateTimeUtil.getDateEnd(dto.getEndDate()));
        }
        conditionSql.append(" ORDER BY CREATE_DATE DESC ");
        Query query = entityManager.createNativeQuery(sql.append(conditionSql).toString());
        QueryUtil.processParamForQuery(query, param);
        Query countQuery = entityManager.createNativeQuery(countSql.append(conditionSql).append(" ) A").toString());
        QueryUtil.processParamForQuery(countQuery, param);
        query.setFirstResult(dto.getPageSize() * (dto.getCurrentPage() - 1));
        query.setMaxResults(dto.getPageSize());
        QueryUtil.buildPagingDto(dto, Long.parseLong(countQuery.getSingleResult().toString()), buildIntersectionListDto(query.getResultList()));
    }

    private List<IntersectionListDto> buildIntersectionListDto(List<Object[]> list) {
        List<IntersectionListDto> dtoList = new ArrayList<IntersectionListDto>();
        for (int i = 0; i < list.size(); i++) {
            IntersectionListDto dto = new IntersectionListDto();
            dto.setId(WRWUtil.objToLong(list.get(i)[0]));
            dto.setIndex(i + 1);
            dto.setName(WRWUtil.objToString(list.get(i)[1]));
            dto.setCreateDate(DateTimeUtil.parseDateToString((Date) list.get(i)[2], DateTimeUtil.SIMPLE_M_D));
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    @Transactional
    public ResultDto<?> save(IntersectionSaveDto dto) {
        if (!WRWUtil.isEmpty(dto.getName()) && !WRWUtil.isEmpty(dto.getDescription())) {
            if (!WRWUtil.isEmpty(dto.getOpration())) {
                SUserEntity user = (SUserEntity) AuthorityContext.getCurrentUser();
                Date date = new Date();
                if (EDITOR.equals(dto.getOpration())) {
                    //编辑
                    SIntersectionEntity entity = sIntersectionDao.findByIdAndStatus(dto.getId(), StatusEnum.NORMAL.getCode());
                    entity.setName(dto.getName());
                    entity.setDescription(dto.getDescription());
                    entity.setModifyId(user.getUserId());
                    entity.setModifyDate(date);
                    sIntersectionDao.save(entity);
                    return ResultDtoFactory.toAck("编辑成功！");
                } else {
                    // 添加
                    SIntersectionEntity entity = new SIntersectionEntity();
                    entity.setName(dto.getName());
                    entity.setDescription(dto.getDescription());
                    entity.setCreateDate(date);
                    entity.setCreateId(user.getUserId());
                    entity.setStatus(StatusEnum.NORMAL.getCode());
                    sIntersectionDao.save(entity);
                    return ResultDtoFactory.toAck("添加成功！");
                }
            }
            return ResultDtoFactory.toNack("操作类型有误！");
        } else {
            return ResultDtoFactory.toNack("请填写完整的信息！");
        }
    }

    @Override
    public SIntersectionEntity findIntersection(Long id) {
        return sIntersectionDao.findByIdAndStatus(id, StatusEnum.NORMAL.getCode());
    }

    @Override
    @Transactional
    public ResultDto<?> delete(List<Long> ids) {
        if (!ids.isEmpty()) {
            List<SIntersectionEntity> needDeleteList = new ArrayList<SIntersectionEntity>();
            List<SIntersectionEntity> deleteList = sIntersectionDao.findByIdsAndStatus(ids, StatusEnum.NORMAL.getCode());
            SUserEntity user = (SUserEntity) AuthorityContext.getCurrentUser();
            Date date = new Date();
            for (SIntersectionEntity intersection : deleteList) {
                intersection.setStatus(StatusEnum.DELETE.getCode());
                intersection.setModifyId(user.getUserId());
                intersection.setModifyDate(date);
                needDeleteList.add(intersection);
            }
            sIntersectionDao.save(needDeleteList);
            return ResultDtoFactory.toAck("删除成功！");
        }
        return ResultDtoFactory.toNack("请先选中要删除的合集！");
    }

    @Override
    public ResultDto<?> getIntersectionDetail(Long id) {
        SIntersectionEntity intersection = sIntersectionDao.findByIdAndStatus(id, StatusEnum.NORMAL.getCode());
        if (intersection != null) {
            return ResultDtoFactory.toAck("获取成功！", intersection.getDescription());
        }
        return ResultDtoFactory.toNack("热门信息出错！");
    }
}
