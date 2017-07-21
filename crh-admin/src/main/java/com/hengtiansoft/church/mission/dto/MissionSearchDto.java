package com.hengtiansoft.church.mission.dto;

import java.io.Serializable;
import java.util.List;

import com.hengtiansoft.church.entity.MissionEntity;

public class MissionSearchDto implements Serializable{
    /**
    * Variables Name: serialVersionUID
    * Description: TODO
    * Value Description: TODO
    */
    private static final long serialVersionUID = 1L;
    private int totalRecords;
    private int currentPage;
    private int pageSize;
    private List<MissionEntity> list;
    public int getTotalRecords() {
        return totalRecords;
    }
    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }
    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public List<MissionEntity> getList() {
        return list;
    }
    public void setList(List<MissionEntity> list) {
        this.list = list;
    }
    
    

}
