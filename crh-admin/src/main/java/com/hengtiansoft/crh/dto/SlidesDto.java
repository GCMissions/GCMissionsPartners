package com.hengtiansoft.crh.dto;

import java.io.Serializable;
import java.util.List;

public class SlidesDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> imageList;

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}
