package com.hengtiansoft.business.shopStock.platformStock.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.barcode.dto.BarcodeGoodDto;
import com.hengtiansoft.business.barcode.dto.BarcodeStorageDto;
import com.hengtiansoft.business.barcode.service.BarcodeService;
import com.hengtiansoft.business.shopStock.platformStock.dto.AdminSearchDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.AdminStockDto;
import com.hengtiansoft.business.shopStock.platformStock.service.AdminStockService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.SpringUtils;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.common.xmemcached.constant.CacheType;
import com.hengtiansoft.wrw.SystemConst;
import com.hengtiansoft.wrw.dao.PGoodsDao;
import com.hengtiansoft.wrw.dao.SBarcodeCycleDao;
import com.hengtiansoft.wrw.dao.SBasicCodeDao;
import com.hengtiansoft.wrw.dao.SShipmentOrderDao;
import com.hengtiansoft.wrw.dao.SStockDao;
import com.hengtiansoft.wrw.entity.PGoodsEntity;
import com.hengtiansoft.wrw.entity.SBarcodeCycleEntity;
import com.hengtiansoft.wrw.entity.SBasicCodeEntity;
import com.hengtiansoft.wrw.entity.SStockEntity;
import com.hengtiansoft.wrw.enums.SBasicCodeCycleStatusEnum;

@Service
public class AdminStockServiceImpl implements AdminStockService {

    @Autowired
    private SStockDao         stockDao;

    @Autowired
    private PGoodsDao         goodsDao;

    @Autowired
    private SShipmentOrderDao shipmentOrderDao;

    @Autowired
    private BarcodeService    barcodeService;

    @Autowired
    private SBasicCodeDao     basicCodeDao;

    @Autowired
    private SBarcodeCycleDao  cycleDao;

    @Override
    public void search(final AdminSearchDto searchDto) {
        PageRequest pageable = new PageRequest(searchDto.getCurrentPage() - 1, searchDto.getPageSize(), new Sort(Direction.ASC, "stockId"));
        final List<Long> goodIds = buildGoodIds(searchDto);
        if (CollectionUtils.isNotEmpty(goodIds) || (WRWUtil.isEmpty(searchDto.getGoodsCode()) && WRWUtil.isEmpty(searchDto.getGoodsName()))) {
            Page<SStockEntity> page = stockDao.findAll(new Specification<SStockEntity>() {
                @Override
                public Predicate toPredicate(Root<SStockEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> predicates = new ArrayList<Predicate>();
                    // 查询出组织ID为0的所有用户
                    Predicate p4 = cb.equal(root.<Long> get("orgId"), SystemConst.PLATFORM_USER_ORG_ID);
                    predicates.add(p4);
                    if (CollectionUtils.isNotEmpty(goodIds)) {
                        predicates.add(root.<Long> get("goodsId").in(goodIds));
                    }
                    Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    query.where(predicate);
                    return query.getRestriction();
                }
            }, pageable);
            searchDto.setTotalRecord(page.getTotalElements());
            searchDto.setTotalPages(page.getTotalPages());
            searchDto.setList(buildStockDtos(page.getContent()));
        }
    }

    private List<Long> buildGoodIds(AdminSearchDto searchDto) {
        List<Long> goodIds = new ArrayList<Long>();
        String code = searchDto.getGoodsCode();
        String name = searchDto.getGoodsName();
        if (WRWUtil.isNotEmpty(code) && WRWUtil.isEmpty(name)) {
            goodIds = goodsDao.findbyGoodsCode(code);
        } else if (WRWUtil.isNotEmpty(name) && WRWUtil.isEmpty(code)) {
            goodIds = goodsDao.findbyGoodsName("%" + name + "%");
        } else if (WRWUtil.isNotEmpty(name) && WRWUtil.isNotEmpty(code)) {
            goodIds = goodsDao.findbyNameAndCode(code, "%" + name + "%");
        }
        return goodIds;
    }

    private List<AdminStockDto> buildStockDtos(List<SStockEntity> content) {
        List<AdminStockDto> dtos = new ArrayList<AdminStockDto>();
        List<Long> goodIds = new ArrayList<Long>();
        Map<Long, AdminStockDto> map = new HashMap<Long, AdminStockDto>();
        for (SStockEntity entity : content) {
            AdminStockDto dto = new AdminStockDto();
            dto.setGoodsId(entity.getGoodsId());
            dto.setStockNum(entity.getStockNum());
            goodIds.add(entity.getGoodsId());
            map.put(entity.getGoodsId(), dto);
        }
        if (CollectionUtils.isNotEmpty(goodIds)) {
            List<PGoodsEntity> goodList = goodsDao.findAll(goodIds);
            for (PGoodsEntity pGoodsEntity : goodList) {
                AdminStockDto dto = map.get(pGoodsEntity.getGoodsId());
                dto.setGoodsCode(pGoodsEntity.getGoodCode());
                dto.setGoodsName(pGoodsEntity.getGoodName());
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    public List<List<String>> findAllOrder(Date startTime, Date endTime) {
        List<List<String>> listStr = new ArrayList<List<String>>();
        List<Object[]> list = new ArrayList<Object[]>();
        if (null == startTime && null == endTime) {
            list = shipmentOrderDao.findAllByOrgIdNotTime(SystemConst.PLATFORM_USER_ORG_ID);
        } else {
            if (null == startTime && null != endTime) { // 设置时间为2010年
                Calendar cal = Calendar.getInstance();
                cal.set(2010, 0, 01);
                startTime = cal.getTime();
            }
            if (null != startTime && null == endTime) {
                endTime = new Date();
            }
            list = shipmentOrderDao.findAllByTimeAndOrgId(startTime, endTime, SystemConst.PLATFORM_USER_ORG_ID);
        }
        Integer numCount = 0;
        for (int i = 0; i < list.size(); i++) {
            List<String> strList = new ArrayList<String>();
            strList.add(String.valueOf(i + 1));
            strList.add(DateTimeUtil.parseDateToString((Date) list.get(i)[0], DateTimeUtil.CHINA_YMD));
            strList.add(list.get(i)[4] != null ? list.get(i)[4].toString() : "");
            strList.add(list.get(i)[2] != null ? list.get(i)[2].toString() : "");
            strList.add(list.get(i)[3] != null ? list.get(i)[3].toString() : "");
            strList.add(list.get(i)[5] != null ? list.get(i)[5].toString() : "0");
            strList.add("");
            strList.add("");
            strList.add("");
            listStr.add(strList);
            numCount += Integer.valueOf(list.get(i)[5] != null ? list.get(i)[5].toString() : "0");
        }
        List<String> hzList = new ArrayList<String>();
        hzList.add("汇总");
        hzList.add("");
        hzList.add("");
        hzList.add("");
        hzList.add("");
        hzList.add(numCount.toString());
        hzList.add("");
        hzList.add("");
        hzList.add("");
        listStr.add(hzList);
        return listStr;
    }

    @Override
    public ResultDto<String> importExcel(InputStream inputStream) {

        long start = System.currentTimeMillis();

        List<String> prefixCodes = new ArrayList<String>();// 订单扫码信息、子码集合
        List<String> packCodes = new ArrayList<String>();// 订单扫码信息、母码集合
        List<String> codesList = new ArrayList<String>();// 条码集合
        List<Long> goodsIds = new ArrayList<Long>();// 物料编码
        try {
            Workbook wb = WorkbookFactory.create(inputStream);// 创建Excel对象
            Sheet sheet = wb.getSheetAt(0);// 取出第一个工作表，索引从0开始
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {// 从第1行开始遍历，标题不处理
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;// 如果行数据为空，则不处理
                }
                String barcode = row.getCell(0) != null ? row.getCell(0).toString() : "";

                codesList.add(barcode);// 取出的条码集合
            }
        } catch (IOException | EncryptedDocumentException | InvalidFormatException e) {
            throw new WRWException(e.toString());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new WRWException(e.toString());
                }
            }
        }

        if (CollectionUtils.isEmpty(codesList)) {
            return ResultDtoFactory.toNack("导入的条码不能为空！");
        }

        // 取出有效条码
        List<SBasicCodeEntity> basicCodeEntities = basicCodeDao.findByPrefixCodes(codesList);
        List<String> validCodesList = new ArrayList<String>();
        if (CollectionUtils.isNotEmpty(basicCodeEntities)) {
            for (SBasicCodeEntity entity : basicCodeEntities) {
                validCodesList.add(entity.getPrefixCode());
            }
            for (int i = 0; i < validCodesList.size(); i++) {
                for (int j = 0; j < basicCodeEntities.size(); j++) {
                    if (validCodesList.get(i).equals(basicCodeEntities.get(j).getPrefixCode())) {
                        if (basicCodeEntities.get(j).getPrefixCode().equals(basicCodeEntities.get(j).getPackCode())) {
                            packCodes.add(validCodesList.get(i));
                        } else {
                            prefixCodes.add(validCodesList.get(i));
                        }
                    }
                }
            }
        }
        codesList.removeAll(validCodesList);// 从codesList中移除掉所有通过认证的有效条码

        List<SBasicCodeEntity> prefixCodeEntities = new ArrayList<SBasicCodeEntity>();
        if (CollectionUtils.isNotEmpty(packCodes)) {
            prefixCodeEntities = basicCodeDao.findAllPrefixCodeByPackCode(packCodes);
        }
        Map<String, Integer> prefixCodeMap = new HashMap<String, Integer>();
        if (CollectionUtils.isNotEmpty(prefixCodeEntities)) {
            for (SBasicCodeEntity entity : prefixCodeEntities) {
                if (prefixCodeMap.get(entity.getPackCode()) != null) {
                    prefixCodeMap.put(entity.getPackCode(), prefixCodeMap.get(entity.getPackCode()) + 1);
                } else {
                    prefixCodeMap.put(entity.getPackCode(), 1);
                }
            }
            Iterator<String> it = prefixCodeMap.keySet().iterator();
            while (it.hasNext()) {
                String packCode = it.next();
                if (prefixCodeMap.get(packCode).equals(6)) {// 有效母码
                    Set<String> newPrefixCodes = new HashSet<String>();
                    for (SBasicCodeEntity entity : prefixCodeEntities) {
                        if (entity.getPackCode().equals(packCode)) {
                            newPrefixCodes.add(entity.getPrefixCode());
                        }
                    }
                    newPrefixCodes.addAll(prefixCodes);
                    prefixCodes.clear();// 去除可能重复的子码
                    prefixCodes.addAll(newPrefixCodes);
                    validCodesList.addAll(newPrefixCodes);// 将从数据库中捞出的子码加入到有效条码list中 下面从流水表中查重用得到
                } else {// 无效母码
                    packCodes.remove(packCode);
                    codesList.add(packCode);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(validCodesList)) {
            List<SBarcodeCycleEntity> barcodeCycleEntities = cycleDao.findAllByPrefixCodesAndStatus(validCodesList,
                    SBasicCodeCycleStatusEnum.ADMIN_STORAGE.getKey());
            if (CollectionUtils.isNotEmpty(barcodeCycleEntities)) {
                Set<String> existedBarcodes = new HashSet<String>();
                for (SBarcodeCycleEntity entity : barcodeCycleEntities) {
                    existedBarcodes.add(entity.getPrefixCode());// 流水表中已经入库的条码
                    for (SBasicCodeEntity codeEntity : prefixCodeEntities) {
                        if (entity.getPrefixCode().equals(codeEntity.getPrefixCode())) {
                            packCodes.remove(codeEntity.getPackCode());
                            codesList.add(codeEntity.getPackCode());// 这边可能会加入重复的数据，需要过滤一遍
                            for (SBasicCodeEntity codeEntity2 : prefixCodeEntities) {
                                if (codeEntity.getPackCode().equals(codeEntity2.getPackCode())) {
                                    prefixCodes.remove(codeEntity2.getPrefixCode());
                                }
                            }
                        }
                    }
                }
                // 从有效条码的list中移除所有已经入库的条码并加这些条码加到无效条码codesList中去
                packCodes.removeAll(existedBarcodes);
                prefixCodes.removeAll(existedBarcodes);
                codesList.addAll(existedBarcodes);
            }
        }

        if (CollectionUtils.isNotEmpty(prefixCodes)) {
            List<SBasicCodeEntity> codeEntities = basicCodeDao.findByPrefixCodes(prefixCodes);
            if (CollectionUtils.isNotEmpty(codeEntities)) {
                for (SBasicCodeEntity entity : codeEntities) {
                    for (String prefixCode : prefixCodes) {
                        if (prefixCode.equals(entity.getPrefixCode())) {
                            goodsIds.add(entity.getGoodId());// 将有效的子码对应的goodsId取出来
                        }
                    }
                }
            }
        }

        // 取出重复的值 和此值重复出现的次数
        Map<Long, Long> map = new HashMap<Long, Long>();
        if (CollectionUtils.isNotEmpty(goodsIds)) {
            for (Long goodsId : goodsIds) {
                if (map.get(goodsId) != null) {
                    map.put(goodsId, map.get(goodsId) + 1);
                } else {
                    map.put(goodsId, 1L);
                }
            }
        }
        List<BarcodeGoodDto> goodDtos = new ArrayList<BarcodeGoodDto>();
        if (CollectionUtils.isNotEmpty(goodsIds)) {
            Iterator<Long> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                BarcodeGoodDto dto = new BarcodeGoodDto();
                Long goodId = iterator.next();
                dto.setGoodId(goodId);
                dto.setGoodNum(map.get(goodId));
                goodDtos.add(dto);
            }
        }

        BarcodeStorageDto storageDto = new BarcodeStorageDto();
        storageDto.setGoodDtos(goodDtos);
        storageDto.setPackCodes(packCodes);
        storageDto.setPrefixCodes(prefixCodes);
        storageDto.setGoodNum(prefixCodes.size());
        storageDto.setStatus(SBasicCodeCycleStatusEnum.ADMIN_STORAGE.getKey());

        ResultDto<String> code = barcodeService.storageCode(storageDto);

        // 如果无效条码集合不为空，则取出这些无效条码并返回给前台
        if (CollectionUtils.isNotEmpty(codesList)) {
            Set<String> codeSet = new HashSet<String>();
            for (String barCode : codesList) {
                codeSet.add(barCode);
            }
            codesList.clear();
            codesList.addAll(codeSet);// 去除重复的条码

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < codesList.size(); i++) {
                sb.append(codesList.get(i) + "，");
            }
            sb.deleteCharAt(sb.lastIndexOf("，"));
            System.err.println(sb.toString());

            long end = System.currentTimeMillis();
            System.err.println("总共执行：" + (end - start));

            // 将失败的条码信息存入缓存
            putErrorCodeIntoCache(sb.toString());

            return ResultDtoFactory.toBusinessError(code.getMessage() + "以下条码入库失败：\n" + sb.toString());
        }

        long end = System.currentTimeMillis();
        System.err.println("总共执行：" + (end - start));

        if (code.getCode().equals("ACK")) {
            return ResultDtoFactory.toAck(code.getMessage());
        } else {
            return ResultDtoFactory.toNack(code.getMessage());
        }
    }

    private void putErrorCodeIntoCache(String errorCode) {
        Long memberId = AuthorityContext.getCurrentUser().getUserId();
        CacheManager cacheManager = (CacheManager) SpringUtils.getBean("cacheManager");
        Cache cache = cacheManager.getCache(CacheType.DEFAULT);
        cache.put(memberId + "_error_code_instock", errorCode);
    }

    private String getErrorCodeFromCache() {
        Long memberId = AuthorityContext.getCurrentUser().getUserId();
        CacheManager cacheManager = (CacheManager) SpringUtils.getBean("cacheManager");
        Cache cache = cacheManager.getCache(CacheType.DEFAULT);
        ValueWrapper value = cache.get(memberId + "_error_code_instock");
        return value != null ? value.get().toString() : "";
    }

    @Override
    public List<List<String>> getErrorCode() {
        List<List<String>> list = new ArrayList<List<String>>();
        String errorStr = getErrorCodeFromCache();
        System.err.println(errorStr);
        if (!"".equals(errorStr)) {
            String[] errorCodes = errorStr.split("，");

            if (errorCodes != null) {
                for (int i = 0; i < errorCodes.length; i++) {
                    List<String> codes = new ArrayList<String>();
                    codes.add(errorCodes[i]);
                    list.add(codes);
                }
            }
        }
        return list;
    }
}
