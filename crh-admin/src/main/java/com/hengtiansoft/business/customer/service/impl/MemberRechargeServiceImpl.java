/*
 * Project Name: wrw-admin
 * File Name: MemberRechargeServiceImpl.java
 * Class Name: MemberRechargeServiceImpl
 * Copyright 2014 Hengtian Software Inc
 * Licensed under the Hengtiansoft
 * http://www.hengtiansoft.com
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.business.customer.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.hengtiansoft.business.customer.dto.RechargeDto;
import com.hengtiansoft.business.customer.dto.RechargeSearchDto;
import com.hengtiansoft.business.customer.service.MemberRechargeService;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.MAcctRechargeDao;
import com.hengtiansoft.wrw.dao.MMemberDao;
import com.hengtiansoft.wrw.entity.MAcctRechargeEntity;
import com.hengtiansoft.wrw.entity.MMemberEntity;

/**
 * Class Name: MemberRechargeServiceImpl
 * Description: TODO
 * 
 * @author chengminmiao
 */
@Service
public class MemberRechargeServiceImpl implements MemberRechargeService {

    @Autowired
    private MMemberDao       mMemberDao;

    @Autowired
    private MAcctRechargeDao mAcctRechargeDao;

    /**
     * 查询
     */
    @Override
    public void search(final RechargeSearchDto searchDto) {
        final List<Integer> memberIds = new ArrayList<Integer>();
        boolean memberFlag = false;
        if (!WRWUtil.isEmpty(searchDto.getMemberPhone())) {
            memberIds.add(mMemberDao.findMemberIdByLoginId(searchDto.getMemberPhone()));
            memberFlag = true;
        } else if (!WRWUtil.isEmpty(searchDto.getMemberName())) {
            memberIds.addAll(mMemberDao.findMemberIdByName(searchDto.getMemberName()));
            memberFlag = true;
        }

        if (!(memberFlag && memberIds.size() == 0)) {

            PageRequest pageable = new PageRequest(searchDto.getCurrentPage() - 1, searchDto.getPageSize(), new Sort(Direction.DESC, "payDate"));
            Page<MAcctRechargeEntity> page = mAcctRechargeDao.findAll(new Specification<MAcctRechargeEntity>() {
                @Override
                public Predicate toPredicate(Root<MAcctRechargeEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> predicates = new ArrayList<Predicate>();

                    if (memberIds.size() > 0) {
                        predicates.add(root.<Long> get("memberId").in(memberIds));
                    }
                    if (null != searchDto.getAmount()) {
                        predicates.add(cb.equal(root.<Long> get("amount"), searchDto.getAmount()));
                    }
                    if (!WRWUtil.isEmpty(searchDto.getPaymentType())) {
                        predicates.add(cb.equal(root.<String> get("paymentType"), searchDto.getPaymentType()));

                    }
                    if (null != searchDto.getPayDateBegin()) {
                        predicates.add(cb.greaterThanOrEqualTo(root.<Date> get("payDate"), searchDto.getPayDateBegin()));
                    }
                    if (null != searchDto.getPayDateEnd()) {
                        predicates.add(cb.lessThanOrEqualTo(root.<Date> get("payDate"), searchDto.getPayDateEnd()));
                    }
                    // 如果用编码查询，不匹配其他条件
                    Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    query.where(predicate);
                    return query.getRestriction();
                }

            }, pageable);

            searchDto.setTotalRecord(page.getTotalElements());
            searchDto.setTotalPages(page.getTotalPages());
            searchDto.setList(buildRechargeDtos(page.getContent()));
        }
    }

    private List<RechargeDto> buildRechargeDtos(List<MAcctRechargeEntity> content) {
        List<RechargeDto> dtos = new ArrayList<RechargeDto>();

        if (content.size() > 0) {

            List<Long> memberIdsList = new ArrayList<Long>();

            for (MAcctRechargeEntity entity : content) {
                RechargeDto dto = ConverterService.convert(entity, RechargeDto.class);
                dtos.add(dto);

                memberIdsList.add(entity.getMemberId());
            }

            if (memberIdsList.size() > 0) {
                List<MMemberEntity> memberEntities = mMemberDao.findAll(memberIdsList);
                HashMap<Long, MMemberEntity> memberMap = new HashMap<Long, MMemberEntity>();
                for (MMemberEntity entity : memberEntities) {
                    memberMap.put(entity.getUserId(), entity);
                }
                for (int i = 0; i < dtos.size(); i++) {
                    dtos.get(i).setMemberName(memberMap.get(dtos.get(i).getMemberId()).getMemberName());
                    dtos.get(i).setMemberPhone(memberMap.get(dtos.get(i).getMemberId()).getLoginId());
                }
            }
        }
        return dtos;
    }
}
