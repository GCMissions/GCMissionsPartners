package com.hengtiansoft.task.service.impl;

import java.text.NumberFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.task.constant.ProductSortParamConstant;
import com.hengtiansoft.task.service.ProductSortService;
import com.hengtiansoft.wrw.dao.ProductSortParamDao;
import com.hengtiansoft.wrw.dao.SBasicParaDao;
import com.hengtiansoft.wrw.entity.ProductSortParamEntity;

@Service
public class ProductSortServiceImpl implements ProductSortService {

    @Autowired
    private SBasicParaDao sBasicParaDao;

    @Autowired
    private ProductSortParamDao productSortParamDao;
    
    @Override
    public void productSortValue() {
        Double salesWeight = Double.valueOf(sBasicParaDao.findByParaName(ProductSortParamConstant.SALES).getParaValue1());
        Double clicksWeight = Double.valueOf(sBasicParaDao.findByParaName(ProductSortParamConstant.CLICKS).getParaValue1());
        Double sharesWeight = Double.valueOf(sBasicParaDao.findByParaName(ProductSortParamConstant.SHARES).getParaValue1());       
        List<ProductSortParamEntity> list = productSortParamDao.findAll();
        for(ProductSortParamEntity p : list){
            p.setSort(sortValue(p.getSales(),salesWeight,p.getClicks(),clicksWeight,p.getShareCount(),sharesWeight));
        }
        productSortParamDao.save(list);
    }

    
    /**
     * 计算空间向量模长
    * Description: TODO
    *
    * @param p1
    * @param w1
    * @param p2
    * @param w2
    * @param p3
    * @param w3
    * @return
     */
    private Double sortValue(Long p1,Double w1,Long p2,Double w2,Long p3,Double w3){
        Double d = (p1*w1)*(p1*w1)+(p2*w2)*(p2*w2)+(p3*w3)*(p3*w3);       
        Double sort =  Math.sqrt(d);
        NumberFormat nbf=NumberFormat.getInstance();   
        nbf.setMinimumFractionDigits(5);  
        return Double.valueOf(nbf.format(sort));
    }
}
