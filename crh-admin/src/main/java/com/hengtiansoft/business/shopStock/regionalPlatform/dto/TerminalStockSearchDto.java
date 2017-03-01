package com.hengtiansoft.business.shopStock.regionalPlatform.dto;

import com.hengtiansoft.common.dto.PagingDto;

/**
 * Class Name: TerminalStockSearchDto
 * Description: 终端配送商库存查看搜索条件
 * 
 * @author fengpan
 */
public class TerminalStockSearchDto extends PagingDto<TerminalStockDto> {

    /**
     * Variables Name: serialVersionUID
     * Description: TODO
     * Value Description: TODO
     */
    private static final long serialVersionUID = 7042592620288560896L;

    private String            orgCode;

    private String            orgName;

    private String            startTime;

    private String            endTime;

    private String            contactName;

    private String            phone;

    private String            isWaring;

    private String            orgIds;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsWaring() {
        return isWaring;
    }

    public void setIsWaring(String isWaring) {
        this.isWaring = isWaring;
    }

    public String getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(String orgIds) {
        this.orgIds = orgIds;
    }

}
