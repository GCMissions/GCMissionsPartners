package com.hengtiansoft.business.shopMall.dto;

import java.io.Serializable;
import java.util.List;


public class AdvertisementEditDto implements Serializable {

	private static final long serialVersionUID = 2874775662397814692L;

	private List<AdvertisementPositionDto> positionList;

	private AdvertisementDto adDto;

	/**
	 * @return the positionList
	 */
	public List<AdvertisementPositionDto> getPositionList() {
		return positionList;
	}

	/**
	 * @param positionList
	 *            the positionList to set
	 */
	public void setPositionList(List<AdvertisementPositionDto> positionList) {
		this.positionList = positionList;
	}

	/**
	 * @return the adDto
	 */
	public AdvertisementDto getAdDto() {
		return adDto;
	}

	/**
	 * @param adDto
	 *            the adDto to set
	 */
	public void setAdDto(AdvertisementDto adDto) {
		this.adDto = adDto;
	}

}
