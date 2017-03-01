/*
 * Project Name: wrw-admin
 * File Name: AppStartupHomepageServiceImpl.java
 * Class Name: AppStartupHomepageServiceImpl
 *
 * Copyright 2014 Hengtian Software Inc
 *
 * Licensed under the Hengtiansoft
 *
 * http://www.hengtiansoft.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.business.app.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.app.dto.AppStartUpHomepageDto;
import com.hengtiansoft.business.app.dto.SearchAppStartUpHomepageDto;
import com.hengtiansoft.business.app.service.AppStartupHomepageService;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.wrw.dao.AppStartupHomepageDao;
import com.hengtiansoft.wrw.entity.AppStartupHomepageEntity;
import com.hengtiansoft.wrw.enums.AppStartupStatusEnum;

/**
 * Class Name: AppStartupHomepageServiceImpl Description: TODO
 * 
 * @author qianqianzhu
 *
 */
@Service
public class AppStartupHomepageServiceImpl implements AppStartupHomepageService {

    @Autowired
    private AppStartupHomepageDao appStartupHomepageDao;

    @Override
    public void findList(SearchAppStartUpHomepageDto dto) {
        Integer currentPage = dto.getCurrentPage();
        if (currentPage == 0) {
            currentPage = 1;
        }
        PageRequest pageable = new PageRequest(currentPage - 1, dto.getPageSize(), new Sort(Direction.ASC, "createDate"));
        Page<AppStartupHomepageEntity> page = appStartupHomepageDao
                .findAll(new Specification<AppStartupHomepageEntity>() {
                    @Override
                    public Predicate toPredicate(Root<AppStartupHomepageEntity> root, CriteriaQuery<?> query,
                            CriteriaBuilder cb) {
                        List<Predicate> predicates = new ArrayList<Predicate>();
                        predicates.add(cb.equal(root.<String> get("status"), AppStartupStatusEnum.NORMAL.getKey()));

                        Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                        query.where(predicate);
                        return query.getRestriction();
                    }
                }, pageable);

        dto.setTotalRecord(page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setList(buildStartUpHomepageDto(page.getContent()));
    }

    private List<AppStartUpHomepageDto> buildStartUpHomepageDto(List<AppStartupHomepageEntity> content) {
        List<AppStartUpHomepageDto> dtos = new ArrayList<AppStartUpHomepageDto>();
        for (AppStartupHomepageEntity entity : content) {
            dtos.add(ConverterService.convert(entity, AppStartUpHomepageDto.class));
        }
        return dtos;
    }

    @Override
    @Transactional
    public void updateOrInsert(AppStartUpHomepageDto dto) {
        AppStartupHomepageEntity entity = ConverterService.convert(dto, AppStartupHomepageEntity.class);
        entity.setStatus(AppStartupStatusEnum.NORMAL.getKey());
        Date date = new Date();
        if (entity.getId() != null) {
            entity.setModifyDate(date);
        } else {
            entity.setCreateDate(date);
        }
        appStartupHomepageDao.save(entity);
    }

    @Override
    @Transactional
    public void deleteEntity(Long id) {
        appStartupHomepageDao.deleteEntity(id, AppStartupStatusEnum.REMOVED.getKey());
    }

    @Override
    public AppStartUpHomepageDto findDto(String id) {
        AppStartUpHomepageDto dto = new AppStartUpHomepageDto();
        AppStartupHomepageEntity entity = appStartupHomepageDao.findOne(StringUtils.isBlank(id) ? null : Long.parseLong(id));
        return entity == null ? dto : ConverterService.convert(entity, AppStartUpHomepageDto.class);
    }
}
