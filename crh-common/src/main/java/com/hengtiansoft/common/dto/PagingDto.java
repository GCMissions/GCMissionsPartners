/*
 * Project Name: zc-collect-common
 * File Name: PagingDto.java
 * Class Name: PagingDto
 * Copyright 2014 Hengtian Software Inc
 * Licensed under the Hengtiansoft
 * http://www.hengtiansoft.com
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.common.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Class Name: PagingDto Description:Paging Dto
 * 
 * @author jialiangli
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
