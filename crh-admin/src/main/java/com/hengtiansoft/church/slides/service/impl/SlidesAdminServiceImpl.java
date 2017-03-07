package com.hengtiansoft.church.slides.service.impl;

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

import com.hengtiansoft.church.common.constants.OprationTypeConstants;
import com.hengtiansoft.church.common.constants.SortConstants;
import com.hengtiansoft.church.common.util.QueryUtil;
import com.hengtiansoft.church.dao.SlidesDao;
import com.hengtiansoft.church.entity.SlidesEntity;
import com.hengtiansoft.church.enums.StatusEnum;
import com.hengtiansoft.church.slides.dto.SlidesListDto;
import com.hengtiansoft.church.slides.dto.SlidesSaveDto;
import com.hengtiansoft.church.slides.dto.SlidesSearchDto;
import com.hengtiansoft.church.slides.service.SlidesAdminService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.authority.domain.UserInfo;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.util.BasicUtil;

@Service
public class SlidesAdminServiceImpl implements SlidesAdminService {
    
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private SlidesDao slidesDao;

    @SuppressWarnings("unchecked")
    @Override
    public void getSlidesList(final SlidesSearchDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder conditionSql = new StringBuilder("");
        StringBuilder countSql = new StringBuilder("");
        StringBuilder sql = new StringBuilder(
                " select id,image,description,display,sort from slides where 1=1 ");
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
        dto.setList(buildSlidesList(query.getResultList()));
    }

    private List<SlidesListDto> buildSlidesList(List<Object[]> list) {
        List<SlidesListDto> slideList = new ArrayList<SlidesListDto>();
        for (Object[] obj : list) {
            SlidesListDto dto = new SlidesListDto();
            dto.setId(BasicUtil.objToLong(obj[0]));
            dto.setImage(BasicUtil.objToString(obj[1]));
            dto.setDescription(BasicUtil.objToString(obj[2]));
            String displayed = BasicUtil.objToString(obj[3]);
            if (StatusEnum.NORMAL.getCode().equals(displayed)) {
                dto.setDisplayed("Displayed");
            } else {
                dto.setDisplayed("Hidden");
            }
            dto.setIndex(BasicUtil.objToString(obj[4]));
            slideList.add(dto);
        }
        return slideList;
    }

    @Override
    @Transactional
    public ResultDto<?> deleteSlide(Long id) {
        UserInfo userInfo = AuthorityContext.getCurrentUser();
        Long userId = 0L;
        if (userInfo != null) {
            userId = userInfo.getUserId();
        }
        SlidesEntity slide = slidesDao.findOne(id);
        slide.setDelFlag(StatusEnum.DELETE.getCode());
        slide.setModifyId(userId);
        slide.setModifyDate(new Date());
        slidesDao.save(slide);
        slidesDao.updateSort(slide.getSort());
        return ResultDtoFactory.toAck(" Delete Success!", null);
    }

    @Override
    public SlidesListDto slideDetail(Long id) {
        SlidesListDto dto = new SlidesListDto();
        SlidesEntity entity = slidesDao.findOne(id);
        dto.setIndex(entity.getSort());
        dto.setImage(entity.getImage());
        dto.setDescription(entity.getDescription());
        dto.setDisplayed(entity.getDisplay());
        return dto;
    }

    @Override
    @Transactional
    public ResultDto<?> saveSlides(SlidesSaveDto dto) {
        SlidesEntity slide = null;
        UserInfo userInfo = AuthorityContext.getCurrentUser();
        Long userId = 0L;
        if (userInfo != null) {
            userId = userInfo.getUserId();
        }
        if (OprationTypeConstants.SAVE_OPRATION.equals(dto.getType())) {
            // Save
            slide = new SlidesEntity();
            slide.setCreateDate(new Date());
            slide.setCreateId(userId);
            slide.setDelFlag(StatusEnum.NORMAL.getCode());
            SlidesEntity lastSortSlide = slidesDao.findLastSortEntity();
            if (lastSortSlide == null) {
                slide.setSort(SortConstants.FIRST_SORT);
            } else {
                slide.setSort(Integer.parseInt(lastSortSlide.getSort()) + 1 + "");
            }
        } else {
            // Editor
            slide = slidesDao.findOne(dto.getId());
            slide.setModifyDate(new Date());
            slide.setModifyId(userId);
        }
        slide.setDescription(dto.getDescription());
        slide.setDisplay(dto.getDisplay());
        slide.setImage(dto.getImage());
        slidesDao.save(slide);
        return ResultDtoFactory.toAck("Operation is successful!", null);
    }

    @Override
    @Transactional
    public ResultDto<?> adjustSort(Long id, String type) {
        SlidesEntity originSlide = slidesDao.findOne(id);
        Integer originSort = Integer.parseInt(originSlide.getSort());
        SlidesEntity nextSlide = null;
        if (OprationTypeConstants.UP_SORT.equals(type)) {
            // up sort
            nextSlide = slidesDao.findBySort(originSort - 1 + "");
            originSlide.setSort(originSort + 1 + "");
        } else {
            // down sort
            nextSlide = slidesDao.findBySort(originSort + 1 + "");
            originSlide.setSort(originSort - 1 + "");
        }
        slidesDao.save(originSlide);
        nextSlide.setSort(originSort.toString());
        slidesDao.save(nextSlide);
        return ResultDtoFactory.toAck("Success!", null);
    }
}
