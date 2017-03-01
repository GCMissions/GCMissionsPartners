package com.hengtiansoft.business.message.dto;

import com.hengtiansoft.common.dto.PagingDto;

/**
 * Class Name: SMessagePageDto
 * Description: 站内信--分页包装类
 * 
 * @author chenghongtu
 */
public class SMessagePageDto extends PagingDto<MessageListDto> {

    /**
     * Variables Name: serialVersionUID
     * Description: TODO
     * Value Description: TODO
     */
    private static final long serialVersionUID = -7519075673695248319L;

    private String            title;

    private String            csDate;

    private String            ceDate;

    private String            readStatus;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCsDate() {
        return csDate;
    }

    public void setCsDate(String csDate) {
        this.csDate = csDate;
    }

    public String getCeDate() {
        return ceDate;
    }

    public void setCeDate(String ceDate) {
        this.ceDate = ceDate;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

}
