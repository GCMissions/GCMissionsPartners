package com.hengtiansoft.church.authority.dto;

import java.io.Serializable;
import java.util.List;

import com.hengtiansoft.church.entity.SRoleInfoEntity;

/**
 * Class Name: SUserEditDto Description: User edit DTO
 * 
 * @author tao chen
 */
public class SUserEditDto implements Serializable {

    private static final long serialVersionUID = 2434154113544738268L;

    private SUserSaveAndUpdateDto dto;

    private List<SRoleInfoEntity> list;

    private String qrCodeUrl;

    public SUserSaveAndUpdateDto getDto() {
        return dto;
    }

    public void setDto(SUserSaveAndUpdateDto dto) {
        this.dto = dto;
    }

    public List<SRoleInfoEntity> getList() {
        return list;
    }

    public void setList(List<SRoleInfoEntity> list) {
        this.list = list;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

}
