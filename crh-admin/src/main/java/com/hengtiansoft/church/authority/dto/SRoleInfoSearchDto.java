package com.hengtiansoft.church.authority.dto;

import com.hengtiansoft.common.dto.PagingDto;

/**
 * Class Name: SRoleInfoSearchDto
 * Description: ROLE RESEARCH DTO
 * 
 * @author zhisongliu
 */
public class SRoleInfoSearchDto extends PagingDto<SRoleInfoDto> {

    private static final long serialVersionUID = -186235214893466998L;

    private String            role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
