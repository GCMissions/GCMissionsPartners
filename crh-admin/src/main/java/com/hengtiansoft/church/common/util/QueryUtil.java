package com.hengtiansoft.church.common.util;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Query;

import com.hengtiansoft.common.dto.PagingDto;

public class QueryUtil {
    // 设置sql参数
    public static void processParamForQuery(Query query, Map<String, Object> paramMap) {
        for (Entry<String, Object> entry : paramMap.entrySet()) {
            String paramName = entry.getKey();
            Object paramVal = entry.getValue();
            query.setParameter(paramName, paramVal);
        }
    }
    
    public static void buildPagingDto(PagingDto pagingDto, Long totalRecord,List list){
        pagingDto.setTotalRecord(totalRecord);
        pagingDto.setTotalPages(totalRecord.intValue() % pagingDto.getPageSize() == 0?totalRecord.intValue() / pagingDto.getPageSize():((totalRecord.intValue() / pagingDto.getPageSize()) + 1));
        pagingDto.setList(list);
    }
}
