package com.hengtiansoft.church.salutatory.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.church.dao.SalutataoryDao;
import com.hengtiansoft.church.entity.SalutatoryEntity;
import com.hengtiansoft.church.salutatory.dto.SalutatorySaveDto;
import com.hengtiansoft.church.salutatory.service.SalutatoryAdminService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;

@Service
public class SalutatoryAdminServiceImpl implements SalutatoryAdminService {
    @Autowired
    private SalutataoryDao salutatoryDao;

    @Override
    public SalutatoryEntity getSalutatory() {
        List<SalutatoryEntity> sa = salutatoryDao.findAll();
        if(sa != null && sa.size() >0){
            return sa.get(0);
        }else{
            return null;
        }
        
       
    }

    @Transactional
    @Override
    public ResultDto<?> saveSalutatory(final SalutatorySaveDto dto) {
        SalutatoryEntity salutatory = null;
        String message = "";
        if (dto == null) {
            message = "Error Opearation";
        } else {
            message = "Saved Operation";
            if (dto.getId() == 0L) {
                salutatory = new SalutatoryEntity();
                salutatory.setCreateTime(new Date());
            } else {
                salutatory = salutatoryDao.findOne(dto.getId());
            }
            salutatory.setTitle(dto.getTitle());
            salutatory.setContent(dto.getContent());
            salutatory.setUpdateTimeDate(new Date());
            
            salutatoryDao.save(salutatory);
        }

        return ResultDtoFactory.toAck(message,"");
    }

}
