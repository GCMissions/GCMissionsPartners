package com.hengtiansoft.business.setting.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.setting.dto.MessageModelDto;
import com.hengtiansoft.business.setting.dto.SBasicParaDto;
import com.hengtiansoft.business.setting.service.MessageModelService;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.xmemcached.constant.CacheType;
import com.hengtiansoft.wrw.dao.SBasicParaDao;
import com.hengtiansoft.wrw.entity.SBasicParaEntity;

@Service
public class MessageModelServiceImpl implements MessageModelService {

	@Autowired
	private SBasicParaDao paraDao;

	@Override
	@Cacheable(value = CacheType.DEFAULT, key = "'Message_Data'")
	public MessageModelDto getMessageModel() {
		//找到消息模板下的所有数据
		List<SBasicParaEntity> paraList = paraDao.findByTypeId(1);
		List<String> value1List = new ArrayList<String>();
		MessageModelDto dto = new MessageModelDto();
		for (SBasicParaEntity one : paraList) {
			value1List.add(one.getParaValue1());
		}
		dto.setSms_C_getCaptcha(value1List.get(0));
		dto.setSms_C_sendOrderANotify(value1List.get(1));
		dto.setSms_C_sendOrderBNotify(value1List.get(2));
		dto.setSms_S_sendNewOrderNotify(value1List.get(3));
		dto.setSms_C_refundNotify(value1List.get(4));
		dto.setSms_S_refundNotify(value1List.get(5));
		dto.setSms_C_sendCouponNoitfy(value1List.get(6));
		dto.setSms_C_KdProductRefundNotify(value1List.get(7));
		dto.setSms_C_KdActRefundNotify(value1List.get(8));
		return dto;
	}

	@Override
	@CacheEvict(value = CacheType.DEFAULT, key = "'Message_Data'")
	public void save(MessageModelDto dto) {
		//找到消息模板下的所有数据
		List<SBasicParaEntity> paraList = paraDao.findByTypeId(1);
		paraList.get(0).setParaValue1(dto.getSms_C_getCaptcha());
		paraList.get(1).setParaValue1(dto.getSms_C_sendOrderANotify());
		paraList.get(2).setParaValue1(dto.getSms_C_sendOrderBNotify());
		paraList.get(3).setParaValue1(dto.getSms_S_sendNewOrderNotify());
		paraList.get(4).setParaValue1(dto.getSms_C_refundNotify());
		paraList.get(5).setParaValue1(dto.getSms_S_refundNotify());
		paraList.get(6).setParaValue1(dto.getSms_C_sendCouponNoitfy());
		paraDao.save(paraList);
	}

	@Override
	public SBasicParaDto getBasicParaByParaName(String paraName) {
		SBasicParaEntity entity = paraDao.findByParaName(paraName);
		SBasicParaDto dto = ConverterService.convert(entity,
				SBasicParaDto.class);
		return dto;
	}

}
