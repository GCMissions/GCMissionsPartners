package com.hengtiansoft.business.message.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.message.dto.MessageListDto;
import com.hengtiansoft.business.message.dto.SMessagePageDto;
import com.hengtiansoft.business.message.service.SMessageService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.wrw.dao.SMessageDao;
import com.hengtiansoft.wrw.dao.SMessageOrgDao;
import com.hengtiansoft.wrw.dao.SUserDao;
import com.hengtiansoft.wrw.entity.SMessageEntity;
import com.hengtiansoft.wrw.entity.SMessageOrgEntity;
import com.hengtiansoft.wrw.enums.SMessageOrgReadStatusEnum;
import com.hengtiansoft.wrw.enums.SMessageOrgStatusEnum;

/**
 * Class Name: SMessageServiceImpl
 * Description: TODO
 * 
 * @author chenghongtu
 */
@Service
public class SMessageServiceImpl implements SMessageService {

    @Autowired
    private SMessageDao    sMessageDao;

    @Autowired
    private SMessageOrgDao sMessageOrgDao;

    @Autowired
    private SUserDao       sUserDao;

    @Override
    public void search(final SMessagePageDto dto) {
        final Long orgIdId = sUserDao.findOne(AuthorityContext.getCurrentUser().getUserId()).getOrgId();
        PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.DESC, "createDate"));
        List<SMessageOrgEntity> sMessageOrgEntitys = sMessageOrgDao.findAll(new Specification<SMessageOrgEntity>() {
            @Override
            public Predicate toPredicate(Root<SMessageOrgEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.<Long> get("orgId"), orgIdId));
                predicates.add(cb.equal(root.<String> get("status"), SMessageOrgStatusEnum.ENABLED.getKey()));

                if (StringUtils.isNotEmpty(dto.getReadStatus())) {
                    predicates.add(cb.equal(root.<String> get("readStatus"), dto.getReadStatus()));
                }
                Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate);
                return query.getRestriction();
            }

        });
        List<Long> messageIds = new ArrayList<Long>();
        Map<Long, String> idStatusMap = new HashMap<Long, String>();
        for (SMessageOrgEntity entity : sMessageOrgEntitys) {
            messageIds.add(entity.getMessageId());
            idStatusMap.put(entity.getMessageId(), entity.getReadStatus());
        }
        final List<Long> ids = messageIds;
        if (ids.size() == 0) {
            dto.setList(new ArrayList<MessageListDto>());
            return;
        }
        Page<SMessageEntity> page = sMessageDao.findAll(new Specification<SMessageEntity>() {
            @Override
            public Predicate toPredicate(Root<SMessageEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (!StringUtils.isEmpty(dto.getTitle())) {
                    Predicate p0 = cb.like(root.<String> get("title"), "%"+dto.getTitle()+"%");
                    predicates.add(p0);
                }
                if (null == dto.getCsDate() || "".equals(dto.getCsDate())) {
                    if (null != dto.getCeDate() && !"".equals(dto.getCeDate())) {
                        Predicate p2 = cb.lessThanOrEqualTo(root.<Date> get("createDate"), DateTimeUtil.getDateEnd(dto.getCeDate()));
                        predicates.add(p2);
                    }

                }
                if (null != dto.getCsDate() && !"".equals(dto.getCsDate())) {
                    if (null == dto.getCeDate() || "".equals(dto.getCeDate())) {
                        Predicate p3 = cb.greaterThanOrEqualTo(root.<Date> get("createDate"), DateTimeUtil.getDateBegin(dto.getCsDate()));
                        predicates.add(p3);
                    } else {
                        Predicate p4 = cb.between(root.<Date> get("createDate"), DateTimeUtil.getDateBegin(dto.getCsDate()),
                                DateTimeUtil.getDateEnd(dto.getCeDate()));
                        predicates.add(p4);
                    }
                }
                predicates.add(root.<Long> get("messageId").in(ids));
                Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate);
                return query.getRestriction();
            }

        }, pageable);

        dto.setTotalRecord(page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        List<SMessageEntity> entitys = page.getContent();
        List<MessageListDto> dtoList = null;
        if (CollectionUtils.isNotEmpty(entitys)) {
            dtoList = new ArrayList<MessageListDto>();
            for (SMessageEntity e : entitys) {
                MessageListDto messageDto = ConverterService.convert(e, MessageListDto.class);
                messageDto.setReadStatus(idStatusMap.get(e.getMessageId()));
                dtoList.add(messageDto);
            }
            dto.setList(dtoList);
        }else{
            dto.setList(new ArrayList<MessageListDto>());
        }
        
    }

    @Override
    @Transactional
    public SMessageEntity editOne(final String messageId) {
        final Long orgIdId = sUserDao.findOne(AuthorityContext.getCurrentUser().getUserId()).getOrgId();
        List<SMessageOrgEntity> entitys = sMessageOrgDao.findAll(new Specification<SMessageOrgEntity>() {
            @Override
            public Predicate toPredicate(Root<SMessageOrgEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.<Long> get("orgId"), orgIdId));
                predicates.add(cb.equal(root.<Long> get("messageId"), messageId));
                Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate);
                return query.getRestriction();
            }

        });
        for (SMessageOrgEntity entity : entitys) {
            entity.setReadStatus(SMessageOrgReadStatusEnum.READ.getCode());
        }
        sMessageOrgDao.save(entitys);
        return sMessageDao.findOne(Long.valueOf(messageId));
    }

    @Override
    public Integer getUnredMessageNum() {
        final Long orgIdId = sUserDao.findOne(AuthorityContext.getCurrentUser().getUserId()).getOrgId();
        List<SMessageOrgEntity> sMessageOrgEntitys = sMessageOrgDao.findAll(new Specification<SMessageOrgEntity>() {
            @Override
            public Predicate toPredicate(Root<SMessageOrgEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.<Long> get("orgId"), orgIdId));
                predicates.add(cb.equal(root.<String> get("readStatus"), SMessageOrgReadStatusEnum.UNREAD.getCode()));
                predicates.add(cb.equal(root.<String> get("status"), SMessageOrgStatusEnum.ENABLED.getKey()));
                Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate);
                return query.getRestriction();
            }

        });
        return sMessageOrgEntitys.size();
    }

}
