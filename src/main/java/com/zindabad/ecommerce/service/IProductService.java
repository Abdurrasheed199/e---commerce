package com.zindabad.ecommerce.service;

import com.zindabad.ecommerce.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService {

    public List products(int id);
    public Product createProduct(String name, double price, List<String> categories, MultipartFile file, String description);
}

