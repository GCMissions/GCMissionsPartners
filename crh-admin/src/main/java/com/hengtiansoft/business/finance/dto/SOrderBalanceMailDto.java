package com.hengtiansoft.business.finance.dto;

import com.hengtiansoft.wrw.entity.SOrderBalanceEntity;

/**
 * 
* Class Name: SOrderBanlaceMailDto
* Description: 平台邮寄 列表对象
* @author chenghongtu
*
 */
public class SOrderBalanceMailDto extends SOrderBalanceEntity{

    /**
    * Variables Name: serialVersionUID
    * Description: TODO
    * Value Description: TODO
    */
    private static final long serialVersionUID = -9109872496769246215L;
    
    private Long prodTotalProfit;

    public Long getProdTotalProfit() {
        return prodTotalProfit;
    }

    public void setProdTotalProfit(Long prodTotalProfit) {
        this.prodTotalProfit = prodTotalProfit;
    }

}
