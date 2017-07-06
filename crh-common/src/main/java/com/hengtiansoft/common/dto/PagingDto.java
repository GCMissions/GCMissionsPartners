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

    // The number of data per page
    private Integer           pageSize         = 50;

    // Current page number
    private Integer           currentPage      = 1;

    // Total number of data
    private Long              totalRecord;

    // total pages
    private Integer           totalPages;

    // Data container
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
