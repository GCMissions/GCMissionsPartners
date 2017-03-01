package com.hengtiansoft.business.context.dto;

import java.io.Serializable;
import java.util.List;

public class MaterialListDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<ImageAndIdDto> imageAndIdDtos;

    public List<ImageAndIdDto> getImageAndIdDtos() {
        return imageAndIdDtos;
    }

    public void setImageAndIdDtos(List<ImageAndIdDto> imageAndIdDtos) {
        this.imageAndIdDtos = imageAndIdDtos;
    }
}
