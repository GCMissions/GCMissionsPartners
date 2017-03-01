package com.hengtiansoft.business.context.dto;

import java.io.Serializable;
import java.util.List;

public class ImageListDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<StockImageDto> stockImageDtos;

    public List<StockImageDto> getStockImageDtos() {
        return stockImageDtos;
    }

    public void setStockImageDtos(List<StockImageDto> stockImageDtos) {
        this.stockImageDtos = stockImageDtos;
    }
}
