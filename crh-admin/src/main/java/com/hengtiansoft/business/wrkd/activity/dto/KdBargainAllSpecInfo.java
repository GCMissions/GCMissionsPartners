package com.hengtiansoft.business.wrkd.activity.dto;

import java.util.List;
import java.util.Set;
/**
 * 
* Class Name: KdBargainAllSpecInfo
* Description: TODO
* @author chengchaoyin
*
 */
public class KdBargainAllSpecInfo {

    private Set<String> mainSpecArry;
    
    private String isReview;
    
    private List<KdBargainSpecDto> kdBargainSpecDtos;
    
    private Long productId;

    public Set<String> getMainSpecArry() {
        return mainSpecArry;
    }

    public void setMainSpecArry(Set<String> mainSpecArry) {
        this.mainSpecArry = mainSpecArry;
    }

    public List<KdBargainSpecDto> getKdBargainSpecDtos() {
        return kdBargainSpecDtos;
    }

    public void setKdBargainSpecDtos(List<KdBargainSpecDto> kdBargainSpecDtos) {
        this.kdBargainSpecDtos = kdBargainSpecDtos;
    }

    public String getIsReview() {
        return isReview;
    }

    public void setIsReview(String isReview) {
        this.isReview = isReview;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
}
