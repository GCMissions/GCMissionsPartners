package com.hengtiansoft.business.product.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Class Name: ProductImageService
 * Description: TODO
 * 
 * @author chengminmiao
 */
public interface ProductImageService {
    String uploadImage(MultipartFile multipartFile, String source);

    String addImage(MultipartFile file, String source);
}
