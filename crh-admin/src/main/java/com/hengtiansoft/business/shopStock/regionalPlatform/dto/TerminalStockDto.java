package com.hengtiansoft.business.shopStock.regionalPlatform.dto;

import java.io.Serializable;

/**
 * 
* Class Name: TerminalStockDto
* Description: 终端配送商库存详情dto
* @author fengpan
*
 */
public class TerminalStockDto implements Serializable{

    /**
    * Variables Name: serialVersionUID
    * Description: TODO
    * Value Description: TODO
    */
    private static final long serialVersionUID = 7553937038530597010L;
    
    private String orgCode;
    
    private String orgName;
    
    private String contactName;
    
    private String phone;
    
    private Integer stockNum;
    
    private String status;
    
    private String date;
    
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

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
}
