package com.hengtiansoft.business.marketing.dto;

import com.hengtiansoft.common.dto.PagingDto;

/**
 * 
* Class Name: BargainPageDto
* Description: 砍价商品Dto
* @author chenghongtu
*
 */
public class BargainPageDto extends PagingDto<BargainDto>{
    
    private static final long serialVersionUID = -747973138817881560L;

    private String startDate;
    
    private String endDate;
    
    private String status;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
