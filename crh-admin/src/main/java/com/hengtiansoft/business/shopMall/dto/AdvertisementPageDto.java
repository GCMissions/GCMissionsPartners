package com.hengtiansoft.business.shopMall.dto;

import java.util.Date;
import java.util.List;

import com.hengtiansoft.common.dto.PagingDto;

/**
 * Class Name: AdvertisementPageDto
 * Description: TODO
 * 
 * @author chengminmiao
 */
public class AdvertisementPageDto extends PagingDto<AdvertisementDto> {

    private static final long              serialVersionUID = 7382981447381756326L;

    private String                         title;

    private String                         local;

    private String                         type;

    private Date                           startDate;

    private Date                           endDate;

    private List<AdvertisementPositionDto> positionList;

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate
     *            the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate
     *            the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the local
     */
    public String getLocal() {
        return local;
    }

    /**
     * @param local
     *            the local to set
     */
    public void setLocal(String local) {
        this.local = local;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
