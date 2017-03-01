package com.hengtiansoft.business.product.dto;

import java.io.Serializable;
import java.util.List;

import com.hengtiansoft.wrw.entity.PAttributeEntity;
import com.hengtiansoft.wrw.entity.PBrandEntity;

public class PTypeAddDto implements Serializable{

	private static final long serialVersionUID = 3659095311474717963L;
	
	private List<PBrandEntity> listBrands;
	
	private List<PAttributeEntity> listAttrs;

	public List<PBrandEntity> getListBrands() {
		return listBrands;
	}

	public void setListBrands(List<PBrandEntity> listBrands) {
		this.listBrands = listBrands;
	}

	public List<PAttributeEntity> getListAttrs() {
		return listAttrs;
	}

	public void setListAttrs(List<PAttributeEntity> listAttrs) {
		this.listAttrs = listAttrs;
	}
	
	
}
