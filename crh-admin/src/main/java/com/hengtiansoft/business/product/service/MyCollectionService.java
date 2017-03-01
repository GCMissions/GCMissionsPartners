package com.hengtiansoft.business.product.service;

public interface MyCollectionService {

    // 将收藏的商品置为失效
    void updateMyCollection(Long productId);
}
