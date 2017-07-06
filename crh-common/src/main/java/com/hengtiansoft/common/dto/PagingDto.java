package com.hengtiansoft.common.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Class Name: PagingDto Description:Paging Dto
 * 
 * @author taochen
 */
public class PagingDto<T> implements Serializable {

    public PagingDto() {}

    public PagingDto(Integer pageSize, Integer currentPage) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

    private static final long serialVersionUID = 1L;

    // Displays the number of records per page
    private Integer           pageSize         = 50;

    // current page number
    private Integer           currentPage      = 1;

    // total
    private Long              totalRecord;

    // total pages
    private Integer           totalPages;

    // data List
    private List<T>           list;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Long totalRecord) {
        this.totalRecord = totalRecord;
    }

    public Integer getTotalPages() {
        if (this.totalPages == null && totalRecord != null) {
            return pageSize == 0 ? 1 : (int) Math.ceil((double) totalRecord / (double) pageSize);
        }
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
