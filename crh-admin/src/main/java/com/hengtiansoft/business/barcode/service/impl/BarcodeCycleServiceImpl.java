package com.hengtiansoft.business.barcode.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.barcode.dto.BarcodeCycleDto;
import com.hengtiansoft.business.barcode.dto.BarcodeCycleSearchDto;
import com.hengtiansoft.business.barcode.service.BarcodeCycleService;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.SBarcodeCycleDao;
import com.hengtiansoft.wrw.dao.SBasicCodeDao;
import com.hengtiansoft.wrw.entity.SBasicCodeEntity;
import com.hengtiansoft.wrw.enums.SBasicCodeCycleStatusEnum;

/**
 * Class Name: BarcodeCycleServiceImpl
 * Description: TODO
 * 
 * @author hongqi
 */
@Service
public class BarcodeCycleServiceImpl implements BarcodeCycleService {
    @Autowired
    private SBarcodeCycleDao cycleDao;

    @Autowired
    private SBasicCodeDao    basicCodeDao;

    @Override
    public void searchByUrl(final BarcodeCycleSearchDto dto) {
        int index=dto.getPrefixCode().indexOf("c=");
        if(index+18>dto.getPrefixCode().length()){
            //如果长度不够则不作处理
        }else{
            dto.setPrefixCode(dto.getPrefixCode().substring(0, index+18));
        }
        System.out.println(dto.getPrefixCode());
        SBasicCodeEntity e = basicCodeDao.findByUrl(dto.getPrefixCode());
        if (e != null) {
            dto.setPrefixCode(e.getPrefixCode());
        }
        searchByPrefixCode(dto);
    }

    @Override
    public void searchByPrefixCode(final BarcodeCycleSearchDto dto) {
        if (WRWUtil.isEmpty(dto.getPrefixCode())) {
            return;
        }
        PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.DESC, "createDate"));
        Page<Object[]> page = cycleDao.findAllByPrefixCode(dto.getPrefixCode(), pageable);
        String packCode = searchPackCode(dto.getPrefixCode());
        dto.setTotalRecord(page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setList(buildMessageDtos(page.getContent(), packCode));
    }

    /**
     * Description: 通过prefix从S_BASIC_CODE表中获取对应的母码(PACK_CODE)
     *
     * @param prefixCode
     * @return
     */
    public String searchPackCode(String prefixCode) {
        SBasicCodeEntity basicCodeEntity = basicCodeDao.findByPrefixCode(prefixCode);
        if (basicCodeEntity != null) {
            return basicCodeEntity.getPackCode();
        }
        return null;
    }

    private List<BarcodeCycleDto> buildMessageDtos(List<Object[]> content, String packCode) {

        List<BarcodeCycleDto> list = new ArrayList<BarcodeCycleDto>();
        for (Object[] o : content) {
            BarcodeCycleDto dto = new BarcodeCycleDto();

            dto.setContent(WRWUtil.objToString(o[0]));

            dto.setCreateDate(WRWUtil.objToString(o[1]));
            dto.setCreateUserName(WRWUtil.objToString(o[10]));
            dto.setCycleId(WRWUtil.objToLong(o[2]));
            dto.setFromId(WRWUtil.objToLong(o[3]));
            dto.setFromName(WRWUtil.objToString(o[4]));
            dto.setOrderId(WRWUtil.objToString(o[5]));
            dto.setSource(WRWUtil.objToString(o[6]));

            dto.setToId(WRWUtil.objToString(o[7]));
            dto.setToName(WRWUtil.objToString(o[8]));
            dto.setStatus(convertStatus(WRWUtil.objToString(o[9])));
            // 添加母码，测试

            dto.setPackCode(packCode);
            // dto.setStatus(entity.getStatus());
            list.add(dto);
        }
        return list;
    }

    private String convertStatus(String status) {
        SBasicCodeCycleStatusEnum[] statuss = SBasicCodeCycleStatusEnum.values();
        for (SBasicCodeCycleStatusEnum s : statuss) {
            if (s.getKey().equals(status)) {
                return s.getValue();
            }
        }
        return "未知的状态";
    }
}
