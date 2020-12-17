package com.zindabad.ecommerce.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {

    public void createImage(long productId, MultipartFile file);
    public List getProductImages(long productId);
}



