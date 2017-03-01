package com.hengtiansoft.business.context.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.context.dto.ImageAndIdDto;
import com.hengtiansoft.business.context.dto.ImageMaterialIdDto;
import com.hengtiansoft.business.context.dto.MaterialListDto;
import com.hengtiansoft.business.context.service.ImageMaterialService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.wrw.dao.ImageMaterialDao;
import com.hengtiansoft.wrw.entity.ImageMaterialEntity;

@Service
public class ImageMaterialServiceImpl implements ImageMaterialService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageMaterialServiceImpl.class);
    
    @Autowired
    private ImageMaterialDao imageMaterialDao;

    @Override
    public void findList(final ImageMaterialIdDto dto) {
        PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.DESC,
                "id"));
        Page<ImageMaterialEntity> page = imageMaterialDao.findAll(new Specification<ImageMaterialEntity>() {
            @Override
            public Predicate toPredicate(Root<ImageMaterialEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.<String> get("status"), "1"));
                
                Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate);
                return query.getRestriction();
            }
        }, pageable);
        dto.setTotalPages(page.getTotalPages());
        dto.setTotalRecord(page.getTotalElements());
        dto.setList(buildMaterialListDto(page.getContent()));
    }

    private List<MaterialListDto> buildMaterialListDto(List<ImageMaterialEntity> content) {
        List<MaterialListDto> materialListDtos = new ArrayList<MaterialListDto>();
        List<ImageAndIdDto> imageAndIdList = new ArrayList<ImageAndIdDto>();
        for (ImageMaterialEntity imageMaterialRecord : content) {
            ImageAndIdDto imageAndIdDto = new ImageAndIdDto();
            imageAndIdDto.setUrl(imageMaterialRecord.getUrl());
            imageAndIdDto.setImageMaterialId(imageMaterialRecord.getId());
            imageAndIdList.add(imageAndIdDto);
            if (imageAndIdList.size() == 5) {
                MaterialListDto materialListDto = new MaterialListDto();
                List<ImageAndIdDto> newImageAndIdList = imageAndIdList;
                materialListDto.setImageAndIdDtos(newImageAndIdList);
                materialListDtos.add(materialListDto);
                imageAndIdList = new ArrayList<ImageAndIdDto>();
            }
        }
        if (!imageAndIdList.isEmpty()) {
            MaterialListDto materialListDto = new MaterialListDto();
            materialListDto.setImageAndIdDtos(imageAndIdList);;
            materialListDtos.add(materialListDto);
        }
        return materialListDtos;
    }

    @Override
    @Transactional
    public ResultDto<?> uploadImage(String url) {
        try {
            imageMaterialDao.addRecord(url);
            return ResultDtoFactory.toAck("上传成功！");
        } catch (Exception e) {
            LOGGER.error("上传素材出现错误！-------------{}", e.toString());
            return ResultDtoFactory.toNack("上传失败！请稍后再试");
        }
    }

    @Override
    @Transactional
    public ResultDto<?> deleteImages(List<Long> deleteIds) {
        try {
            imageMaterialDao.updateStatus("0", deleteIds);
            return ResultDtoFactory.toAck("删除成功！");
        } catch (Exception e) {
            LOGGER.error("删除素材出现错误！-------------{}", e.toString());
            return ResultDtoFactory.toNack("删除失败！请稍后再试");
        }
    }

}
