package com.hengtiansoft.business.shopStock.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.shopStock.service.SStockRecordService;
import com.hengtiansoft.business.shopStock.zStock.dto.StockRecordDto;
import com.hengtiansoft.wrw.enums.StockRecordTypeEmum;

@Service
public class SStockRecordServiceImpl implements SStockRecordService {

    
    @Autowired
    private EntityManager entityManager;
    
    @Override
    public StockRecordDto findByStockId(StockRecordDto dto) {
        String sql = "select RECORD_ID,STOCK_ID,OPER_TYPE,OPER_DATE,CHANGE_NUM from S_STOCK_RECORD WHERE STOCK_ID = "+dto.getStockId() + " order by OPER_DATE DESC";
        String countSql = "select count(1) from S_STOCK_RECORD WHERE STOCK_ID = "+dto.getStockId();
        Query query = entityManager.createNativeQuery(sql);
        Query countQuery = entityManager.createNativeQuery(countSql);
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        query.setFirstResult(dto.getPageSize() * (dto.getCurrentPage() - 1));
        query.setMaxResults(dto.getPageSize());
        dto.setTotalRecord(totalRecord.longValue());
        dto.setTotalPages(totalRecord.intValue() % dto.getPageSize() == 0 ? totalRecord.intValue() / dto.getPageSize() : ((totalRecord
                .intValue() / dto.getPageSize()) + 1));
        List<Object []> list = query.getResultList();
        List<StockRecordDto> dtos = new ArrayList<StockRecordDto>();
        for(Object[] array : list){
            StockRecordDto sdto = new StockRecordDto();
            sdto.setRecordId(array[0]==null?0L:Long.valueOf(array[0].toString()));
            sdto.setStockId(array[1]==null?0L:Long.valueOf(array[1].toString()));
            String operType = array[2]==null?"":array[2].toString();
            sdto.setOperType(StockRecordTypeEmum.getTextByCode(operType));
            sdto.setOperDate(array[3]==null?"":array[3].toString());
            Integer changeNum = array[4]==null?0:Integer.valueOf(array[4].toString());
            if(StockRecordTypeEmum.AUTOREDUCE.getCode().equals(operType)
                    ||StockRecordTypeEmum.REDUCE.getCode().equals(operType)){
                sdto.setChangeNum(0-changeNum);
            }else{
                sdto.setChangeNum(changeNum);
            } 
            dtos.add(sdto);
        }
        dto.setList(dtos);
        return dto;
    }

}
