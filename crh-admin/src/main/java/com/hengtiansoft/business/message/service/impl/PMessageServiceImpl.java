package com.hengtiansoft.business.message.service.impl;

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

import com.hengtiansoft.business.message.dto.MessageViewDto;
import com.hengtiansoft.business.message.dto.PMessageDto;
import com.hengtiansoft.business.message.dto.PMessageSaveDto;
import com.hengtiansoft.business.message.dto.PMessageSearchDto;
import com.hengtiansoft.business.message.dto.SOrgDto;
import com.hengtiansoft.business.message.dto.SOrgSearchDto;
import com.hengtiansoft.business.message.service.PMessageService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.SMessageDao;
import com.hengtiansoft.wrw.dao.SMessageOrgDao;
import com.hengtiansoft.wrw.dao.SOrgDao;
import com.hengtiansoft.wrw.entity.SMessageEntity;
import com.hengtiansoft.wrw.entity.SMessageOrgEntity;
import com.hengtiansoft.wrw.entity.SOrgEntity;
import com.hengtiansoft.wrw.enums.SMessageOrgReadStatusEnum;
import com.hengtiansoft.wrw.enums.SMessageOrgStatusEnum;
import com.hengtiansoft.wrw.enums.StatusEnum;

/**
 * Class Name : PMessageServiceImpl
 * 
 * Desc :平台站内信
 * 
 * @author zhisongliu
 *
 */
@Service
public class PMessageServiceImpl implements PMessageService {

    @Autowired
    private SMessageDao    sMessageDao;

    @Autowired
    private SOrgDao        orgDao;

    @Autowired
    private SMessageOrgDao sMoDao;

    @Override
    public void search(final PMessageSearchDto dto) {
        PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.DESC, "createDate"));
        Page<SMessageEntity> page = sMessageDao.findAll(new Specification<SMessageEntity>() {
            @Override
            public Predicate toPredicate(Root<SMessageEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (!WRWUtil.isEmpty(dto.getTitle())) {
                    String msg = "%" + dto.getTitle() + "%";
                    Predicate p2 = cb.like(root.<String> get("title"), msg);
                    predicates.add(p2);
                }
                if (!WRWUtil.isEmpty(dto.getType())) {
                    Predicate p3 = cb.equal(root.<String> get("type"), dto.getType());
                    predicates.add(p3);
                }
                Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate);
                return query.getRestriction();
            }
        }, pageable);

        dto.setTotalRecord(page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setList(buildMessageDtos(page.getContent()));

    }

    private List<PMessageDto> buildMessageDtos(List<SMessageEntity> content) {

        List<PMessageDto> list = new ArrayList<PMessageDto>();
        for (SMessageEntity entity : content) {
            PMessageDto dto = new PMessageDto();
            dto.setMessageId(entity.getMessageId());
            dto.setTitle(entity.getTitle());
            dto.setCreateDate(DateTimeUtil.parseDateToString(entity.getCreateDate(), DateTimeUtil.SIMPLE_FMT_MINUTE));
            list.add(dto);
        }
        return list;
    }

    @Override
    public void findMember(final SOrgSearchDto dto) {
        PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.DESC, "createDate"));
        Page<SOrgEntity> page = orgDao.findAll(new Specification<SOrgEntity>() {
            @Override
            public Predicate toPredicate(Root<SOrgEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.<String> get("orgType"), dto.getOrgType()));
                if (!WRWUtil.isEmpty(dto.getOrgName())) {
                    predicates.add(cb.like(root.<String> get("orgName"), "%" + dto.getOrgName() + "%"));
                }
                if (!WRWUtil.isEmpty(dto.getOrgCode())) {
                    predicates.add(cb.equal(root.<String> get("orgCode"), dto.getOrgCode()));
                }
                if (!WRWUtil.isEmpty(dto.getContact())) {
                    predicates.add(cb.like(root.<String> get("contact"), "%" + dto.getContact() + "%"));
                }
                if (!WRWUtil.isEmpty(dto.getPhone())) {
                    predicates.add(cb.equal(root.<String> get("phone"), dto.getPhone()));
                }
                predicates.add(cb.equal(root.<String>get("status"),StatusEnum.NORMAL.getCode()));
                Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate);
                return query.getRestriction();
            }
        }, pageable);

        dto.setTotalRecord(page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setList(buildOrgDtos(page.getContent()));
    }

    private List<SOrgDto> buildOrgDtos(List<SOrgEntity> list) {
        List<SOrgDto> dtos = new ArrayList<SOrgDto>();
        for (SOrgEntity entity : list) {
            dtos.add(ConverterService.convert(entity, new SOrgDto()));
        }
        return dtos;
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<String> save(PMessageSaveDto dto) {
        if (WRWUtil.isEmpty(dto.getOrgType())) {
            throw new WRWException("发送对象选择错误,请查证");
        }
        SMessageEntity entity = ConverterService.convert(dto, new SMessageEntity());
        entity.setType(dto.getOrgType());
        entity.setCreateId(AuthorityContext.getCurrentUser().getUserId());
        entity.setCreateDate(new Date());
        sMessageDao.save(entity);
        List<SMessageOrgEntity> entitys = new ArrayList<SMessageOrgEntity>();
        if (dto.getOrgIds() != null && dto.getOrgIds().size() > 0) {
            for (Long id : dto.getOrgIds()) {
                SMessageOrgEntity orgEntity = new SMessageOrgEntity();
                orgEntity.setMessageId(entity.getMessageId());
                orgEntity.setOrgId(id);
                orgEntity.setStatus(SMessageOrgStatusEnum.ENABLED.getKey());
                orgEntity.setReadStatus(SMessageOrgReadStatusEnum.UNREAD.getCode());
                entitys.add(orgEntity);
            }
            sMoDao.save(entitys);
        }

        return ResultDtoFactory.toAck("保存成功!");
    }

    @Override
    public MessageViewDto findById(Long id) {
        SMessageEntity entity = sMessageDao.findOne(id);
        MessageViewDto dto = new MessageViewDto();
        if (entity != null) {
            dto = ConverterService.convert(entity, new MessageViewDto());
            List<Long> list = sMoDao.finByMessageId(id);
            dto.setOrglist(orgDao.findAll(list));
        }
        return dto;
    }

}
