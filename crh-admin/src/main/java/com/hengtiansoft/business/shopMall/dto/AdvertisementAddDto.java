package com.hengtiansoft.business.shopMall.dto;

import java.io.Serializable;
import java.util.List;

public class AdvertisementAddDto implements Serializable{

	private static final long serialVersionUID = -4095195498612037087L;
	
	private List<AdvertisementPositionDto> positionList;

	/**
	 * @return the positionList
	 */
	public List<AdvertisementPositionDto> getPositionList() {
		return positionList;
	}

	/**
	 * @param positionList the positionList to set
	 */
	public void setPositionList(List<AdvertisementPositionDto> positionList) {
		this.positionList = positionList;
	}


}
