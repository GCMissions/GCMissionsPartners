package com.hengtiansoft.business.product.dto;

import java.util.ArrayList;
import java.util.List;

import com.hengtiansoft.common.dto.RequestDto;

/**
 * 
* Class Name: AttributeDto
* Description: 属性DTO
* @author zhisongliu
*
 */
public class AttributeDto extends RequestDto {

    private static final long       serialVersionUID = 6571517134713667717L;

    private Long                    attrId;

    private String                  attrName;

    private String                  status;

    private String                  attrType;

    private Long                    sort;
    
    private String                  searchable  =  "0";

    private List<AttributeValueDto> list             = new ArrayList<AttributeValueDto>();

    public Long getAttrId() {
        return attrId;
    }

    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AttributeValueDto> getList() {
        return list;
    }

    public void setList(List<AttributeValueDto> list) {
        this.list = list;
    }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getSearchable() {
        return searchable;
    }

    public void setSearchable(String searchable) {
        this.searchable = searchable;
    }
    
    
}
