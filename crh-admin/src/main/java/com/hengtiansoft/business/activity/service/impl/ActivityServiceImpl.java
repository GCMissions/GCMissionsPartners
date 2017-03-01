package com.hengtiansoft.business.activity.service.impl;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.activity.dto.ActivityCopyDto;
import com.hengtiansoft.business.activity.dto.ActivityCopyPageDto;
import com.hengtiansoft.business.activity.dto.ActivityDetailSaveDto;
import com.hengtiansoft.business.activity.dto.ActivityDto;
import com.hengtiansoft.business.activity.dto.ActivityEditDto;
import com.hengtiansoft.business.activity.dto.ActivityImageDto;
import com.hengtiansoft.business.activity.dto.ActivitySaveDto;
import com.hengtiansoft.business.activity.dto.ActivitySearchDto;
import com.hengtiansoft.business.activity.service.ActivityService;
import com.hengtiansoft.business.common.util.QueryUtil;
import com.hengtiansoft.business.product.service.MyCollectionService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.CurrencyUtils;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.MCartDao;
import com.hengtiansoft.wrw.dao.PCategoryDao;
import com.hengtiansoft.wrw.dao.PProductDao;
import com.hengtiansoft.wrw.dao.PProductImageDao;
import com.hengtiansoft.wrw.dao.PShiefDao;
import com.hengtiansoft.wrw.dao.ProductSortParamDao;
import com.hengtiansoft.wrw.dao.SOrgDao;
import com.hengtiansoft.wrw.dao.SUserDao;
import com.hengtiansoft.wrw.entity.PCategoryEntity;
import com.hengtiansoft.wrw.entity.PProductEntity;
import com.hengtiansoft.wrw.entity.PProductImageEntity;
import com.hengtiansoft.wrw.entity.ProductSortParamEntity;
import com.hengtiansoft.wrw.entity.SOrgEntity;
import com.hengtiansoft.wrw.enums.CartFlag;
import com.hengtiansoft.wrw.enums.ProductStatusEnum;
import com.hengtiansoft.wrw.enums.ProductTypeEnum;
import com.hengtiansoft.wrw.enums.StatusEnum;

/**
 * 
 * Class Name: ActivityServiceImpl Description: 新增商品
 * 
 * @author chenghongtu
 *
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SOrgDao sOrgDao;

    @Autowired
    private PProductDao pProductDao;

    @Autowired
    private PCategoryDao pcDao;

    @Autowired
    private PProductImageDao pImageDao;

    @Autowired
    private MCartDao mCartDao;

    @Autowired
    private MyCollectionService myCollectionService;
    
    @Autowired
    private PShiefDao pShiefDao;
    
    @Autowired
    private SUserDao     sUserDao;
    
    @Autowired
    private ProductSortParamDao productSortParamDao;

    private final String CATE_ID_FINLA = "0";

    @Override
    public List<SOrgEntity> findOrgs() {
        return sOrgDao.findByStatus(ProductStatusEnum.USED.getCode());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void search(final ActivitySearchDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder conditionSql = new StringBuilder("");
        StringBuilder countSql = new StringBuilder("");
        StringBuilder sql = new StringBuilder(
                "SELECT P.PRODUCT_ID,P.CATE_ID,(SELECT C.CATE_NAME FROM P_CATEGORY C WHERE P.CATE_ID = C.CATE_ID) FIRST_CATE_NAME,"
                        + "(SELECT C.CATE_NAME  FROM P_CATEGORY C WHERE C.CATE_ID =(SELECT C.PARENT_ID FROM P_CATEGORY C WHERE P.CATE_ID = C.CATE_ID AND C.PARENT_ID != 0)) SECOND_CATE_NAME,P.ORG_ID,"
                        + "(SELECT O.ORG_NAME FROM S_ORG O WHERE P.ORG_ID = O.ORG_ID) ORG_NAME,"
                        + "P.PRODUCT_NAME,P.PRICE,P.CREATE_DATE,P.PRODUCT_CODE,"
                        + "(SELECT S.SALE_FLAG FROM (SELECT S.PRODUCT_ID,S.SALE_FLAG FROM P_SHIEF S group by S.PRODUCT_ID HAVING COUNT(*) > 0) S WHERE P.PRODUCT_ID = S.PRODUCT_ID) SALE_FLAG,"
                        + "CONCAT(GROUP_CONCAT(AC.LOW_PRICE SEPARATOR '/'),'/', GROUP_CONCAT(AC.HIGH_PRICE SEPARATOR '/')) priceAll "
                        +"FROM P_PRODUCT P  LEFT JOIN ACT_STOCK AC ON P.PRODUCT_ID = AC.PRODUCT_ID WHERE 1=1 ");
        conditionSql.append("AND P.STATUS = " + ProductStatusEnum.USED.getCode());
        countSql.append("select count(1) from (").append(sql);
        // 商品名称
        if (!WRWUtil.isEmpty(dto.getProductName())) {
            String msg =dto.getProductName();
            char escape = '\\';
            msg = msg.replace("\\", escape + "\\");
            msg = msg.replace("%", escape + "%");
            msg = msg.replace("_", escape + "_");
            conditionSql.append(" AND P.PRODUCT_NAME LIKE :NAME escape '\\\\' ");
            param.put("NAME", "%" + msg + "%");  
        }
        // 商品品类
        if (WRWUtil.isEmpty(dto.getSecondCateId())) {
            if (WRWUtil.isNotEmpty(dto.getFirstCateId()) && !(CATE_ID_FINLA.equals(dto.getFirstCateId()))) {
                List<Long> cateIds = new ArrayList<Long>();
                cateIds.add(Long.valueOf(dto.getFirstCateId()));
                List<PCategoryEntity> categorys = pcDao.findByParentId(Long.valueOf(dto.getFirstCateId()));
                for (PCategoryEntity entity : categorys) {
                    cateIds.add(entity.getCateId());
                }
                conditionSql.append(" AND P.CATE_ID IN :IDS");
                param.put("IDS", cateIds);
            }
        } else {
            if (WRWUtil.isNotEmpty(dto.getSecondCateId())) {
                conditionSql.append(" AND P.CATE_ID = :ID");
                param.put("ID", dto.getSecondCateId());
            }
        }
        // 服务商
        if (!WRWUtil.isEmpty(dto.getOrgId())) {
            conditionSql.append(" AND P.ORG_ID = :ORG");
            param.put("ORG", dto.getOrgId());
        }
        // vip
        if (WRWUtil.isNotEmpty(dto.getVipFlag())) {
            conditionSql.append(" AND P.VIP = :VIP");
            param.put("VIP", dto.getVipFlag());
        }
        // 商品价格
        if (WRWUtil.isNotEmpty(dto.getLowPrice())) {
            if (!validateNum(dto.getLowPrice())) {
                return;
            }
            conditionSql.append(" AND AC.HIGH_PRICE >= :LOWPRICE");
            param.put("LOWPRICE", CurrencyUtils.rmbYuanToFen(Double.parseDouble(dto.getLowPrice())));
        }
        if (WRWUtil.isNotEmpty(dto.getHighPrice())) {
            if (!validateNum(dto.getHighPrice())) {
                return;
            }
            conditionSql.append(" AND AC.LOW_PRICE <= :HIGHTPRICE");
            param.put("HIGHTPRICE", CurrencyUtils.rmbYuanToFen(Double.parseDouble(dto.getHighPrice())));
        }
        // 商品编号
        if (!WRWUtil.isEmpty(dto.getProductCode())) {
            String cmsg = "%" + dto.getProductCode() + "%";
            conditionSql.append(" AND P.PRODUCT_CODE LIKE :CODE");
            param.put("CODE", cmsg);
        }
        conditionSql.append("  group by P.PRODUCT_ID");
        conditionSql.append(" ORDER BY P.CREATE_DATE DESC ");
        Query query = entityManager.createNativeQuery(sql.append(conditionSql).toString());
        QueryUtil.processParamForQuery(query, param);
        Query countQuery = entityManager.createNativeQuery(countSql.append(conditionSql).append(" ) A").toString());
        QueryUtil.processParamForQuery(countQuery, param);
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        query.setFirstResult(dto.getPageSize() * (dto.getCurrentPage() - 1));
        query.setMaxResults(dto.getPageSize());
        dto.setTotalRecord(totalRecord.longValue());
        dto.setTotalPages(totalRecord.intValue() % dto.getPageSize() == 0 ? totalRecord.intValue() / dto.getPageSize()
                : ((totalRecord.intValue() / dto.getPageSize()) + 1));
        dto.setList(buildProductDtos(query.getResultList(), dto));
    }

    private List<ActivityDto> buildProductDtos(List<Object[]> list, ActivitySearchDto searchDto) {
        List<ActivityDto> dtos = new ArrayList<ActivityDto>();
        for (Object[] array : list) {
            ActivityDto dto = new ActivityDto();
            dto.setProductId(WRWUtil.objToLong(array[0]));
            dto.setCateId(WRWUtil.objToLong(array[1]));
            dto.setFirstCateName(WRWUtil.objToString(array[2]));
            dto.setSecondCateName(WRWUtil.objToString(array[3]));
            dto.setOrgId(WRWUtil.objToLong(array[4]));
            dto.setOrgName(WRWUtil.objToString(array[5]));
            dto.setProductName(WRWUtil.objToString(array[6]));
            dto.setCreateDate(
                    array[8] == null ? null : DateTimeUtil.parseDate(array[8].toString(), DateTimeUtil.SIMPLE_FMT));
            dto.setProductCode(WRWUtil.objToString(array[9]));
            dto.setSaleFlag(WRWUtil.objToString(array[10]));
            if (array[11] != null) {
                String[] prices = WRWUtil.objToString(array[11]).split("/");
                List<Long> priceAll = new ArrayList<Long>();
                for (String price : prices) {
                    priceAll.add(WRWUtil.objToLong(price));
                }
                Collections.sort(priceAll);
                Long lowPrice = priceAll.get(0);
                Long highPrice = priceAll.get(priceAll.size()-1);
                if (!lowPrice.equals(0L)  && !lowPrice.equals(highPrice )) {
                    dto.setPrice(WRWUtil.transFenToYuan2String(lowPrice)+"~"+WRWUtil.transFenToYuan2String(highPrice)); 
                }else if (!lowPrice.equals(0L) && lowPrice.equals(highPrice)) {
                    dto.setPrice(WRWUtil.transFenToYuan2String(lowPrice));
                }
            }
            if (dto.getSecondCateName().equals("")) {
                dto.setCateName(dto.getFirstCateName());
            } else {
                dto.setCateName(dto.getSecondCateName() + "-" + dto.getFirstCateName());
            }
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<PCategoryEntity> findAllFirstCates() {
        return pcDao.findAllFathers();
    }

    @Override
    public List<PCategoryEntity> findAllSecondCateByFirstCateId(Long id) {

        return pcDao.findByParentId(id);
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<String> delete(String ids) {
        String[] idsp = ids.split(";");
        List<String> pIds = new ArrayList<String>();
        List<String> pShiefIds = new ArrayList<String>();
        List<Long> pShiefList = pShiefDao.findByShelf();
        for (String id : idsp) {
            pIds.add(id);
         // 判断该商品是否有上架状态
            for (Object psid : pShiefList) {
                if (id.equals(psid.toString())) {
                    pShiefIds.add("P"+id);
                    break;
                }
            }
        }
        if (CollectionUtils.isNotEmpty(pShiefIds)) {
            return ResultDtoFactory.toNack("操作失败，"+WRWUtil.listToString(pShiefIds)+"已上架，请下架后再刪除");
        }
        Long mid = AuthorityContext.getCurrentUser().getUserId();
        pProductDao.deleProductByStatusAndProductId(ProductStatusEnum.DELETE.getCode(),new Date(),mid,pIds);
        mCartDao.changeCartByFlagAndProductId(CartFlag.invalide.getKey(), pIds);
        productSortParamDao.deleteByProductId(pIds);
        return ResultDtoFactory.toAck("删除成功!");   
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public PProductEntity save(ActivitySaveDto dto) {
        if (WRWUtil.isEmpty(dto.getActivityName())) {
            throw new WRWException("商品名称未填写,请重新输入!");
        }
        // 商品品类
        if (dto.getCateId() == null) {
            throw new WRWException("商品品类不可为空,请选择!");
        } 
        // 服务商
        if (dto.getOrgId() == null) {
            throw new WRWException("服务商不可为空,请选择!");
        } 
        // 商品图片
        if (dto.getListImages() == null || dto.getListImages().isEmpty()) {
            throw new WRWException("商品图片不可为空,请选择!");
        }
        
        PProductEntity entity = new PProductEntity();
        int countName = pProductDao.findCountByProductName(dto.getActivityName());
        if (countName > 0) {
            throw new WRWException("该商品名称已存在,请检查后重新输入!");
        }
        entity.setProductName(dto.getActivityName());
        
        PCategoryEntity cateEntity = pcDao.findOne(dto.getCateId());
        if (cateEntity == null) {
            throw new WRWException("新增错误,请检查输入!");
        } else {
            // 判断是否有子类
            List<Integer> noSonList = pcDao.noSonList();
            if (!noSonList.contains(Integer.valueOf(cateEntity.getCateId().toString()))) {
                throw new WRWException("请选择子品类!");
            }
        }
        entity.setCateId(cateEntity.getCateId());
        entity.setCateName(cateEntity.getCateName());
        
        SOrgEntity orgEntity = sOrgDao.findOne(dto.getOrgId());
        if (orgEntity == null) {
            throw new WRWException("新增错误,请检查输入!");
        }
        entity.setOrgId(dto.getOrgId());
        // 商品价格(2016-8-12删除该字段)
        entity.setPrice(0L);
        // 商品类型，后期需修改，目前默认都是服务类商品
        entity.setTypeId(ProductTypeEnum.SERVICE.getKey());
        // 原价
        entity.setOriginalPrice(dto.getOriginalPrice());
        // 是否是VIP商品
        entity.setVip(dto.getVip());
        //是否需要验证码
        entity.setIsCaptcha(dto.getIsCaptcha());
        // 商品介绍
        entity.setNote(dto.getNote());
        // 退货退款说明
        entity.setRebackNote(dto.getRebackNote());
        // 商品详情
        // entity.setDescription(dto.getDescription());
        entity.setCreateId(AuthorityContext.getCurrentUser().getUserId());
        entity.setCreateDate(new Date());
        entity.setStatus(ProductStatusEnum.USED.getCode());
        // 不需要属性，后期删除
        entity.setCostPrice(0L);
        entity.setSpecNum(1);
        entity.setImage(dto.getListImages().get(0).getImageUrl());
        entity.setImageKey(dto.getListImages().get(0).getImageKey());
        PProductEntity newProduct = pProductDao.save(entity);
        newProduct.setProductCode("P" + newProduct.getProductId());

        // 往product_image表中插入记录
        List<ActivityImageDto> listImages = dto.getListImages();
        List<PProductImageEntity> imagesEntity = new ArrayList<PProductImageEntity>(listImages.size());
        for (ActivityImageDto imageDto : listImages) {
            PProductImageEntity imageEntity = ConverterService.convert(imageDto, new PProductImageEntity());
            imageEntity.setStatus(ProductStatusEnum.USED.getCode());
            imageEntity.setProductId(entity.getProductId());
            imageEntity.setCreateId(AuthorityContext.getCurrentUser().getUserId());
            imageEntity.setCreateDate(new Date());
            imagesEntity.add(imageEntity);
        }
        pImageDao.save(imagesEntity);
        //商品排序参数表
        ProductSortParamEntity paramEntity = new ProductSortParamEntity();
        paramEntity.setProductId(entity.getProductId());
        paramEntity.setClicks(0L);
        paramEntity.setSales(0L);
        paramEntity.setShareCount(0L);
        productSortParamDao.save(paramEntity);
        return newProduct;
    }

    @Override
    public void search(final ActivityCopyPageDto dto) {
        PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.DESC,
                "createDate"));
        Page<PProductEntity> page = pProductDao.findAll(new Specification<PProductEntity>() {

            @Override
            public Predicate toPredicate(Root<PProductEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.<Long> get("status"), ProductStatusEnum.USED.getCode()));
                if (!WRWUtil.isEmpty(dto.getProductName())) {
                    char escape = '\\';
                    String msg = "%" + dto.getProductName().replace("\\", escape+"\\").replace("%", escape+"%").replace("_", escape+"_") + "%";
                    Predicate p1 = cb.like(cb.lower(root.<String> get("productName")), msg, escape);
                    predicates.add(p1);
                }
                // 品类
                if (null != dto.getCategoryId()) {
                    // 判断是否有子类，无:查询本身；有：查询本身及子类
                    List<Integer> noSonList = pcDao.noSonList();
                    if (noSonList.contains(Integer.valueOf(dto.getCategoryId().toString()))) {
                        predicates.add(cb.equal(root.<Long> get("cateId"), dto.getCategoryId()));
                    } else {
                        List<Long> cateIds = pcDao.findChildByParentAndStatus(dto.getCategoryId(),
                                ProductStatusEnum.USED.getCode());
                        cateIds.add(dto.getCategoryId());
                        predicates.add(root.<Long> get("cateId").in(cateIds));
                    }

                }
                if (null != dto.getOrgId()) {
                    predicates.add(cb.equal(root.<Long> get("orgId"), dto.getOrgId()));
                }
                Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate);
                return query.getRestriction();
            }

        }, pageable);
        dto.setTotalRecord(page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setList(buildActivityDtos(page.getContent()));
    }

    /*
     * 获得商品的服务商和品类名称
     */
    private List<ActivityCopyDto> buildActivityDtos(List<PProductEntity> content) {
        List<ActivityCopyDto> copyList = new ArrayList<ActivityCopyDto>();
        List<SOrgEntity> orgs = sOrgDao.findAll();
        List<PCategoryEntity> cates = pcDao.findAll();
        //品类Map
        Map<Long, String> catesMap = new HashMap<Long, String>();
        for (PCategoryEntity cate : cates) {
            catesMap.put(cate.getCateId(), cate.getCateName());
        }
        for (PProductEntity entity : content) {
            ActivityCopyDto copyEntity = ConverterService.convert(entity, new ActivityCopyDto());
            // 服务商名称
            for (SOrgEntity org : orgs) {
                if (entity.getOrgId().equals(org.getOrgId())) {
                    copyEntity.setOrgName(org.getOrgName());
                }
            }

            // 品类名称
            copyEntity.setCateName(catesMap.get(entity.getCateId()));
            copyEntity.setPsCate(getPSCate(entity));
            // 图片
            // 获取所有的照片 排序获取
            List<PProductImageEntity> listImageEntity = pImageDao.findByProductIdAndStatus(entity.getProductId(),
                    ProductStatusEnum.USED.getCode());
            List<ActivityImageDto> listImage = new ArrayList<ActivityImageDto>();
            for (PProductImageEntity pProductImageEntity : listImageEntity) {
                ActivityImageDto iDto = ConverterService.convert(pProductImageEntity, new ActivityImageDto());
                listImage.add(iDto);
            }
            copyEntity.setListImages(listImage);
            copyList.add(copyEntity);
        }
        return copyList;
    }

    
    private String getPSCate(PProductEntity entity) {
        boolean isParent = false;
        String cateName = "";
        PCategoryEntity cateEntity = pcDao.findOne(entity.getCateId());
        List<PCategoryEntity> parentList = pcDao.findAllFathers();
        for (PCategoryEntity e : parentList) {
            if (e.getCateId().equals(entity.getCateId())) {
                isParent = true;
            }
        }
        if (isParent) {
            cateName = cateEntity.getCateName();
        } else {
            PCategoryEntity parentCate = pcDao.findOne(cateEntity.getParentId());
            cateName = parentCate.getCateName() + "-" + cateEntity.getCateName();
        }
        return cateName;
    }

    @Override
    public PCategoryEntity getParentCateBySon(Long id) {
        return pcDao.findOne(id);
    }

    @Override
    public ActivityCopyDto findById(Long id) {
        PProductEntity product = pProductDao.findOne(id);
        ActivityCopyDto entity = ConverterService.convert(product, ActivityCopyDto.class);
        List<PProductImageEntity> listImageEntity = pImageDao.findByProductIdAndStatus(id,
                ProductStatusEnum.USED.getCode());
        List<ActivityImageDto> listImage = new ArrayList<ActivityImageDto>();
        for (PProductImageEntity pProductImageEntity : listImageEntity) {
            ActivityImageDto iDto = ConverterService.convert(pProductImageEntity, new ActivityImageDto());
            listImage.add(iDto);
        }
        entity.setListImages(listImage);
        return entity;
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public PProductEntity update(ActivityEditDto dto) {
        if (WRWUtil.isEmpty(dto.getActivityName())) {
            throw new WRWException("商品名称未填写,请重新输入!");
        } 
        if (null == dto.getOrgId()) {
            throw new WRWException("服务商不可为空,请选择!");
        }
        if (dto.getListImages() == null || dto.getListImages().isEmpty()) {
            throw new WRWException("商品图片不可为空,请选择!");
        } 
        
        boolean isChange = true;
        PProductEntity entity = pProductDao.findOne(dto.getProductId());
        if (entity.getStatus().equals(ProductStatusEnum.DELETE.getCode())) {
            throw new WRWException("该商品已被【" + sUserDao.findOne(entity.getModifyId()).getUserName() + "】删除,无法保存!");
        }
        // 检查是否存在名称重复的商品
        int countName = pProductDao.findCountByProductNameAndId(dto.getActivityName(), dto.getProductId());
        if (countName > 0) {
            throw new WRWException("该商品名称已存在,请检查后重新输入!");
        }
        if (entity.getProductName().equals(dto.getActivityName())) {
            isChange = false;
        }
        entity.setProductName(dto.getActivityName());
        
        SOrgEntity cateEntity = sOrgDao.findOne(dto.getOrgId());
        if (cateEntity == null) {
            throw new WRWException("新增错误,请检查输入!");
        }
        if (entity.getOrgId().equals(dto.getOrgId())) {
            isChange = false;
        }
        entity.setOrgId(dto.getOrgId());
        
        // 原价
        entity.setOriginalPrice(dto.getOriginalPrice());
        // 商品介绍
        entity.setNote(dto.getNote());
        // 退货退款说明
        entity.setRebackNote(dto.getRebackNote());
        // 商品类型，后期需修改，目前默认都是服务类商品
        entity.setTypeId(ProductTypeEnum.SERVICE.getKey());
        // 是否VIP商品
        entity.setVip(dto.getVip());
        // 是否需要验证码
        entity.setIsCaptcha(dto.getIsCaptcha());
        entity.setImage(dto.getListImages().get(0).getImageUrl());
        entity.setImageKey(dto.getListImages().get(0).getImageKey());
        
        Long userId = AuthorityContext.getCurrentUser().getUserId();
        entity.setModifyId(userId);
        entity.setModifyDate(new Date());
        pProductDao.save(entity);
        
        // 判断图片是否修改
        isChange = isImgChange(dto);
        // 将product_image表中相关记录删除
        pImageDao.delByProductId(dto.getProductId());
        // product_image插入新的记录
        List<ActivityImageDto> listImages = dto.getListImages();
        List<PProductImageEntity> imagesEntity = new ArrayList<PProductImageEntity>(listImages.size());
        for (ActivityImageDto imageDto : listImages) {
            PProductImageEntity imageEntity = ConverterService.convert(imageDto, new PProductImageEntity());
            imageEntity.setStatus(ProductStatusEnum.USED.getCode());
            imageEntity.setProductId(entity.getProductId());
            imageEntity.setCreateId(userId);
            imageEntity.setCreateDate(new Date());
            imageEntity.setImageId(null);
            imagesEntity.add(imageEntity);
        }
        pImageDao.save(imagesEntity);
        // 商品基本信息必填字段有修改
        if (isChange) {
            // 修改购物车商品状态
            // changeCartByModifyProduct(dto.getProductId());
            // 修改我的收藏商品状态
            // myCollectionService.updateMyCollection(dto.getProductId());
        }
        
        return entity;
    }

    // 判断图片是否修改
    private boolean isImgChange(ActivityEditDto dto) {
        List<PProductImageEntity> oldImages = pImageDao.findByProductIdAndStatus(dto.getProductId(),
                StatusEnum.NORMAL.getCode());
        List<ActivityImageDto> newImages = dto.getListImages();
        // 长度是否相等
        if (oldImages.size() != newImages.size()) {
            return true;
        } else {
            // 图片内容是否相同
            for (PProductImageEntity oldEntity : oldImages) {
                boolean oneChange = true;
                File oldImage = new File(oldEntity.getImageUrl());
                byte[] oldImgByte = new byte[(int) oldImage.length()];
                for (ActivityImageDto newEntity : newImages) {
                    File newImage = new File(newEntity.getImageUrl());
                    byte[] newImgByte = new byte[(int) newImage.length()];
                    // 判断当前图片能否在新dto中找到
                    if (MD5(oldImgByte).equals(MD5(newImgByte))) {
                        oneChange = false;
                    }
                }
                if (!oneChange) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String findCateByProductId(Long productId) {
        String cateName = "";
        boolean isParent = false;
        PProductEntity product = pProductDao.findOne(productId);
        PCategoryEntity cateEntity = pcDao.findOne(product.getCateId());
        // 判断是否有子类
        List<PCategoryEntity> parentList = pcDao.findAllFathers();
        for (PCategoryEntity e : parentList) {
            if (e.getCateId().equals(cateEntity.getCateId())) {
                isParent = true;
            }
        }
        if (isParent) {
            cateName = cateEntity.getCateName();
        } else {
            PCategoryEntity parentCate = pcDao.findOne(cateEntity.getParentId());
            cateName = parentCate.getCateName() + "-" + cateEntity.getCateName();
        }
        return cateName;
    }

    private boolean validateNum(String str) {
        String reg = "^[-+]?[0-9]+(\\.[0-9]+)?$";
        return str.matches(reg);
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public PProductEntity saveDetail(ActivityDetailSaveDto dto) {
        PProductEntity entity = pProductDao.findOne(dto.getProductId());
        if (entity.getStatus().equals(ProductStatusEnum.DELETE.getCode())) {
            throw new WRWException("该商品已被【" + sUserDao.findOne(entity.getModifyId()).getUserName() + "】删除,无法保存!");
        }
        entity.setDescription(dto.getDescription());
        pProductDao.save(entity);
        return entity;
    }

    @Override
    @Transactional
    public void changeCartByModifyProduct(Long pid) {
        mCartDao.changeCartByModifyProduct(pid);
    }

    /**
     * 
     * Description: MA5加密方式判断图片是否相同
     *
     * @param s
     * @return
     */
    public static String MD5(byte[] imgByte) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        // 16进制字符
        try {
            byte[] strTemp = imgByte;
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            // 移位输出字符串

            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
}
