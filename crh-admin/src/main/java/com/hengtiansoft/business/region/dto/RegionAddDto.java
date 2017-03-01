package com.hengtiansoft.business.region.dto;

import java.io.Serializable;
import java.util.List;

public class RegionAddDto implements Serializable {
    private static final long serialVersionUID = -2275525485950042745L;

    private List<RegionDto>   list;

    public List<RegionDto> getList() {
        return list;
    }

    public void setList(List<RegionDto> list) {
        this.list = list;
    }

}
