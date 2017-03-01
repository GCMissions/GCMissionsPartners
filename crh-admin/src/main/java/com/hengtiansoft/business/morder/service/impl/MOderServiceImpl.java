package com.hengtiansoft.business.morder.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.common.util.QueryUtil;
import com.hengtiansoft.business.morder.dto.MOrderDto;
import com.hengtiansoft.business.morder.dto.MOrderSearchDto;
import com.hengtiansoft.business.morder.service.MOrderService;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.ExcleUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.SOrgDao;
import com.hengtiansoft.wrw.entity.SOrgEntity;
import com.hengtiansoft.wrw.enums.OrderStatus;
import com.hengtiansoft.wrw.enums.ProductStatusEnum;
import com.hengtiansoft.wrw.enums.ProductTypeEnum;

@Service
public class MOderServiceImpl implements MOrderService{
    
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SOrgDao sOrgDao;

    @Override
    public List<SOrgEntity> findOrgs() {
        return sOrgDao.findByStatus(ProductStatusEnum.USED.getCode());
    }

    @Override
    public void search(MOrderSearchDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder conditionSql = new StringBuilder("");
        StringBuilder countSql = new StringBuilder("");
        StringBuilder sql = new StringBuilder("SELECT O.ORDER_ID,O.CREATE_DATE,O.PHONE,"
                + "GROUP_CONCAT(P.PRODUCT_NAME separator '     '),"
                +"(SELECT SO.ORG_NAME FROM S_ORG SO WHERE SO.ORG_ID = O.ORG_ID) ORG_NAME,"
                +"(SELECT P.TYPE_ID FROM P_PRODUCT P WHERE P.PRODUCT_ID = OD.PRODUCT_ID) TYPE_ID,"
                +"OD.AMOUNT,O.STATUS "
                +"FROM (M_ORDER_MAIN O LEFT JOIN M_ORDER_DETAIL OD ON O.ORDER_ID =  OD.ORDER_ID) "
                +"LEFT JOIN P_PRODUCT P ON P.PRODUCT_ID = OD.PRODUCT_ID WHERE 1=1 ");
        countSql.append("select count(1) from (").append(sql);
        //创建时间
        if (!WRWUtil.isEmpty(dto.getCreateDate())) {
            conditionSql.append(" AND O.CREATE_DATE = :CREATE_DATE ");
            param.put("CREATE_DATE", dto.getCreateDate());
        }
        //订单号
        if (!WRWUtil.isEmpty(dto.getOrderId())) {
            String msg = "%" + dto.getOrderId() + "%";
            conditionSql.append(" AND O.ORDER_ID LIKE :ORDER_ID ");
            param.put("ORDER_ID", msg);
        }
        //手机号
        if (!WRWUtil.isEmpty(dto.getPhone())) {
            if (!validateNum(dto.getPhone())) {
                return;
            }
            String msg = "%" + dto.getPhone() + "%";
            conditionSql.append(" AND O.PHONE LIKE :PHONE ");
            param.put("PHONE", msg);
        }
     // 商品名称
        if (!WRWUtil.isEmpty(dto.getProductName())) {
            String msg = "%" + dto.getProductName() + "%";
            conditionSql.append(" AND P.PRODUCT_NAME LIKE :NAME ");
            param.put("NAME", msg);
        }
      //商品类型
        if (!WRWUtil.isEmpty(dto.getTypeId())) {
            conditionSql.append(" AND P.TYPE_ID = :TYPE ");
            param.put("TYPE", dto.getTypeId());
        } 
     // 服务商
        if (!WRWUtil.isEmpty(dto.getOrgId())) {
            conditionSql.append(" AND O.ORG_ID = :ORG ");
            param.put("ORG", dto.getOrgId());
        }
     // 支付状态
        if (!WRWUtil.isEmpty(dto.getStatus())) {
            conditionSql.append(" AND O.STATUS = :STATUS ");
            param.put("STATUS", dto.getStatus());
        }
        conditionSql.append(" group by O.ORDER_ID ");
        conditionSql.append(" ORDER BY O.CREATE_DATE DESC ");
        Query query = entityManager.createNativeQuery(sql.append(conditionSql).toString());
        QueryUtil.processParamForQuery(query, param);
        Query countQuery = entityManager.createNativeQuery(countSql.append(conditionSql).append(" ) A").toString());
        QueryUtil.processParamForQuery(countQuery, param);
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        query.setFirstResult(dto.getPageSize() * (dto.getCurrentPage() - 1));
        query.setMaxResults(dto.getPageSize());
        dto.setTotalRecord(totalRecord.longValue());
        dto.setTotalPages(totalRecord.intValue() % dto.getPageSize() == 0 ? totalRecord.intValue() / dto.getPageSize() : ((totalRecord
                .intValue() / dto.getPageSize()) + 1));
        dto.setList(buildProductDtos(query.getResultList()));
    }
    
    private List<MOrderDto> buildProductDtos(List<Object[]> list) {
        List<MOrderDto> dtos = new ArrayList<MOrderDto>();
        for (Object[] array : list) {
            MOrderDto dto = new MOrderDto();
            dto.setOrderId(WRWUtil.objToString(array[0]));
            dto.setCreateDate(
                    array[1] == null ? null : DateTimeUtil.parseDate(array[1].toString(), DateTimeUtil.SIMPLE_FMT));
            dto.setPhone(WRWUtil.objToString(array[2]));
            dto.setProductName(WRWUtil.objToString(array[3]));
            dto.setOrgName(WRWUtil.objToString(array[4]));
            dto.setProductType(ProductTypeEnum.getValue(WRWUtil.objToLong(array[5])));
            dto.setAmount(WRWUtil.transFenToYuan2String(array[6] == null ? 0L :WRWUtil.objToLong(array[6])));
            dto.setStatus(OrderStatus.getValue(WRWUtil.objToString(array[7])));
            dtos.add(dto);
        }
        return dtos;
    }

    private boolean validateNum(String str) {
        String reg = "^[-+]?[0-9]+(\\.[0-9]+)?$";
        return str.matches(reg);
    }

    @Override
    public HSSFWorkbook toExcle(MOrderSearchDto pageDto) {
        ArrayList<String> fieldName = new ArrayList<String>();
        ArrayList<String> sheetName = new ArrayList<String>();
        ArrayList<String> fieldStyle = new ArrayList<String>();
        List<List<Object>> fieldData = new ArrayList<List<Object>>();
        
        fieldName.add(0, "序号");
        fieldName.add(1, "订单编号");
        fieldName.add(2, "创建时间");
        fieldName.add(3, "手机号");
        fieldName.add(4, "商品名称");
        fieldName.add(5, "供应商");
        fieldName.add(6, "商品类型");
        fieldName.add(7, "金额（元）");
        fieldName.add(8, "状态");
        
        for (int j = 0; j <= fieldName.size(); j++) {
            fieldStyle.add(j, "6000");
        }
        pageDto.setPageSize(65536);
        this.search(pageDto);
        if (pageDto.getList().size() > 0) {
            for (int i = 0; i < pageDto.getList().size(); i++) {
                MOrderDto dto = pageDto.getList().get(i);
                List<Object> rowData = new ArrayList<Object>();
                rowData.add(0, i + 1);
                rowData.add(1, dto.getOrderId());
                rowData.add(2, dto.getCreateDate());
                rowData.add(3, dto.getPhone());
                rowData.add(4, dto.getProductName());
                rowData.add(5, dto.getOrgName());
                rowData.add(6, dto.getProductType());
                rowData.add(7, dto.getAmount());
                rowData.add(8, dto.getStatus());
                fieldData.add(rowData);
                
            }
        }
        sheetName.add(0, "订单列表");
        ExcleUtil ex = new ExcleUtil(fieldName, sheetName, fieldData, fieldStyle);
        HSSFWorkbook wb = ex.createWorkbook();
        return wb;
    }
}
