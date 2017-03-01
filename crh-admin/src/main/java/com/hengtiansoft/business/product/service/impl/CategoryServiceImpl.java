package com.hengtiansoft.business.product.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.product.dto.ProductCategoryDto;
import com.hengtiansoft.business.product.service.CategoryService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.ApplicationContextUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.PCategoryDao;
import com.hengtiansoft.wrw.dao.PProductDao;
import com.hengtiansoft.wrw.entity.PCategoryEntity;
import com.hengtiansoft.wrw.entity.PProductEntity;
import com.hengtiansoft.wrw.enums.ProductStatusEnum;
import com.hengtiansoft.wrw.enums.StatusEnum;

/**
 * @author jiekaihu
 *
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private PCategoryDao cateDao;

	@Autowired
	private PProductDao productDao;

	@Override
	@Transactional(value = "jpaTransactionManager")
	public ResultDto<String> saveCategory(ProductCategoryDto dto) {
		if (StringUtils.isNotBlank(dto.getCateName())) {
			PCategoryEntity entity = cateDao.findByCateName(dto.getCateName());
			if (entity != null && !entity.getCateId().equals(dto.getCateId())) {
				throw new WRWException("品类名已存在");
			}
		} else {
			throw new WRWException("未输入品类名");
		}
		if (StringUtils.isBlank(dto.getStatus())) {
			dto.setStatus(StatusEnum.NORMAL.getCode());
		}
		if (dto.getParentId() == null) {
			throw new WRWException("未选择上级品类");
		}
		if (dto.getSort() == null) {
			dto.setSort(0L);
		}
		if (StringUtils.isBlank(dto.getImage())) {
		    throw new WRWException("请添加品类图片");
		}
		PCategoryEntity entity = ConverterService.convert(dto,
				PCategoryEntity.class);
		entity.setCreateDate(new Date());
		entity.setCreateId(AuthorityContext.getCurrentUser().getUserId());
		cateDao.save(entity);
		entity = cateDao.findByCateName(entity.getCateName());
		entity.setPath(cateDao.findPathByCateId(entity.getParentId()).getPath()
				+ entity.getCateId() + ",");
		
		cateDao.save(entity);
		// 清除品类缓存
		ApplicationContextUtil.getBean(CategoryService.class).cleanCategoryCache();
		return ResultDtoFactory.toAck("保存成功");
	}

	@Override
	@Transactional(value = "jpaTransactionManager")
	public ResultDto<String> editCategory(ProductCategoryDto dto) {
		PCategoryEntity entity = cateDao.findOne(dto.getCateId());
		PCategoryEntity parentEntity = cateDao.findOne(dto.getParentId());
		if (parentEntity.getPath().contains(entity.getCateId().toString())) {
			throw new WRWException("上级品类不能选择自己及自己下面的子品类");
		}

		// parentId
		if (dto.getParentId() == null) {
			throw new WRWException("未选择上级品类");
		} else {
			entity.setParentId(dto.getParentId());
		}
		// cateName
		if (!WRWUtil.isEmpty(dto.getCateName())) {
			PCategoryEntity entity2 = cateDao.findByCateName(dto.getCateName());
			if (entity2 != null && !entity2.getCateId().equals(dto.getCateId())) {
				throw new WRWException("品类名已存在");
			}
			entity.setCateName(dto.getCateName());
		} else {
			throw new WRWException("未输入品类名");
		}
		// status
		entity.setStatus(StatusEnum.NORMAL.getCode());
		// path
		entity.setPath(cateDao.findPathByCateId(entity.getParentId()).getPath()
				+ entity.getCateId() + ",");

		// sort
		if (dto.getSort() != null) {
			entity.setSort(dto.getSort());
		}
		
		if (StringUtils.isBlank(dto.getImage())) {
            throw new WRWException("请添加品类图片");
        }
		entity.setImage(dto.getImage());
		entity.setImageKey(dto.getImageKey());
		entity.setModifyDate(new Date());
		entity.setModifyId(Long.valueOf(AuthorityContext.getCurrentUser()
				.getUserId()));

		cateDao.save(entity);
		// 清除品类缓存
        ApplicationContextUtil.getBean(CategoryService.class).cleanCategoryCache();
		return ResultDtoFactory.toAck("修改成功");
	}

	@Override
	public ProductCategoryDto getCategory(Long cateId) {
		PCategoryEntity entity = cateDao.findOne(cateId);
		if (entity != null) {
			return ConverterService.convert(entity, ProductCategoryDto.class);
		}
		return null;
	}

	@Override
	@Transactional(value = "jpaTransactionManager")
	public ResultDto<String> deleteById(Long cateId) {
		List<PProductEntity> products = productDao.findByCateIdAndStatus(cateId,ProductStatusEnum.USED.getCode());
		List<Integer> noSonList = cateDao.noSonList();// 无下级的品类

		if (products.size() != 0) {
			throw new WRWException("无法删除！有商品属于此品类，请先清空该品类下所有商品！");
		}
		boolean noSon = noSonList.contains(Integer.valueOf(cateId.toString()));
		if (!noSon) {
			throw new WRWException("无法删除！有子品类，请先清空该品类下所有子品类！");
		}
		PCategoryEntity entity = cateDao.findOne(cateId);
		entity.setStatus(StatusEnum.DELETE.getCode());
		cateDao.save(entity);
		// 清除品类缓存
        ApplicationContextUtil.getBean(CategoryService.class).cleanCategoryCache();
		return ResultDtoFactory.toAck("删除成功");
	}

	@Override
	public List<ProductCategoryDto> getCategoryFatherList() {
		List<PCategoryEntity> list = cateDao.findAllFathers();
		List<ProductCategoryDto> dtoList = new ArrayList<ProductCategoryDto>();
		List<Integer> noSonList = cateDao.noSonList();// 无下级的品类
		for (PCategoryEntity cate : list) {
			if (cate.getStatus().equals(StatusEnum.NORMAL.getCode())) {
				dtoList.add(ConverterService.convert(cate,
						ProductCategoryDto.class));
			}
		}
		for (ProductCategoryDto dto : dtoList) {
			if (noSonList.contains(Integer.valueOf(dto.getCateId().toString()))) {
				dto.setHasSon(false);
			} else {
				dto.setHasSon(true);
			}
		}
		return dtoList;
	}

	@Override
	public List<ProductCategoryDto> getListById(Long cateId) {
		List<PCategoryEntity> list = cateDao.getListById(cateId, StatusEnum.NORMAL.getCode());
		List<ProductCategoryDto> dtoList = new ArrayList<ProductCategoryDto>();
		List<Integer> noSonList = cateDao.noSonList();// 无下级的品类

		ComparatorCate comparator = new ComparatorCate();
		Collections.sort(list, comparator);

		for (PCategoryEntity cate : list) {
			if (cate.getStatus().equals(StatusEnum.NORMAL.getCode())) {
				dtoList.add(ConverterService.convert(cate,
						ProductCategoryDto.class));
			}
		}
		for (ProductCategoryDto dto : dtoList) {
			if (noSonList.contains(Integer.valueOf(dto.getCateId().toString()))) {
				dto.setHasSon(false);
			} else {
				dto.setHasSon(true);
			}
		}
		return dtoList;
	}

	@Override
	public List<ProductCategoryDto> findAll() {
		List<PCategoryEntity> list = cateDao.findAll();
		List<ProductCategoryDto> dtoList = new ArrayList<ProductCategoryDto>();

		ComparatorCate comparator = new ComparatorCate();
		Collections.sort(list, comparator);
		for (PCategoryEntity cate : list) {
			if (cate.getCateId() != 0L
					&& cate.getStatus().equals(StatusEnum.NORMAL.getCode())) {
				dtoList.add(ConverterService.convert(cate,
						ProductCategoryDto.class));
			}
		}
		return dtoList;
	}

	public class ComparatorCate implements Comparator<PCategoryEntity> {

		public int compare(PCategoryEntity arg0, PCategoryEntity arg1) {
			PCategoryEntity cate0 = (PCategoryEntity) arg0;
			PCategoryEntity cate1 = (PCategoryEntity) arg1;

			// 首先比较Sort，如果Sort相同，则比较CreateDate
			int flag;
			if (cate0.getSort() != null && cate1.getSort() != null) {
				flag = cate0.getSort().compareTo(cate1.getSort());
				if (flag == 0) {// sort相同
					return cate1.getCreateDate().compareTo(
							cate0.getCreateDate());
				} else {
					return flag;
				}
			} else {
				return cate1.getCreateDate().compareTo(cate0.getCreateDate());
			}
		}
	}

	@Override
	public List<PCategoryEntity> findAllCates() {
		return cateDao.findAllByStatus(StatusEnum.NORMAL.getCode());
	}

	@Override
	public List<ProductCategoryDto> getParentListById(Long cateId) {

		List<ProductCategoryDto> dtoList = this.getListById(cateDao.findOne(
				cateDao.findOne(cateId).getParentId()).getParentId());
		return dtoList;
	}

    @Override
    public List<PCategoryEntity> findAllFathers() {
        return cateDao.findAllFathers();
    }

    @Override
    public void cleanCategoryCache() {
    }
}
