package com.hengtiansoft.business.product.dto;

import java.io.Serializable;
import java.util.List;

import com.hengtiansoft.wrw.entity.PBrandEntity;
import com.hengtiansoft.wrw.entity.PCategoryEntity;

public class PCateProductDto implements Serializable{
	
	private static final long serialVersionUID = 7336273007382831403L;
	
	private List<PBrandEntity> listBrands;
	private List<AttributeDto> listAttr;
	private PCategoryEntity pCategoryEntity;
	
	public List<PBrandEntity> getListBrands() {
		return listBrands;
	}
	public void setListBrands(List<PBrandEntity> listBrands) {
		this.listBrands = listBrands;
	}
	public List<AttributeDto> getListAttr() {
		return listAttr;
	}
	public void setListAttr(List<AttributeDto> listAttr) {
		this.listAttr = listAttr;
	}
	public PCategoryEntity getpCategoryEntity() {
		return pCategoryEntity;
	}
	public void setpCategoryEntity(PCategoryEntity pCategoryEntity) {
		this.pCategoryEntity = pCategoryEntity;
	}
	
	
	
}
