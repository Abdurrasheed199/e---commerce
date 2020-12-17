package com.zindabad.ecommerce.service;

import com.zindabad.ecommerce.Exception.FileStorageException;
import com.zindabad.ecommerce.model.Category;
import com.zindabad.ecommerce.model.Product;
import com.zindabad.ecommerce.repository.CategoryRepository;
import com.zindabad.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements IProductService{

    final
    ProductRepository productRepository;
    final
    CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    private final String UPLOAD_DIR = "C:\\Users\\USER\\IdeaProjects\\ecommerce\\src\\main\\resources\\static\\uploads";

    public List products(int id){
//        Category category = categoryRepository.findById(id).get();
        List<Product> subProducts = productRepository.findProductsByCategoryId(id);
//        for(Product p: products){
//            List<Category> categories = p.getCategories();
//            for(Category c:categories){
//                if(c.getName().equals(category.getName()));
//                subProducts.add(p);
//            }
//
//        }
        return  subProducts;
    }

    public Product createProduct(String name, double price, List<String> categories, MultipartFile file, String description){
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);

        List<Category> p_categories = new ArrayList<>();
        for(String catId:categories){
            Category category = categoryRepository.findById(Integer.parseInt(catId)).get();
            p_categories.add(category);
        }
        product.setCategories(p_categories);

        try {
            // Generate Universally Unique Identifier, convert to string and add it to original name...
            String fileName = StringUtils.cleanPath(UUID.randomUUID().toString() + file.getOriginalFilename());
            Path imagePath = Paths.get(UPLOAD_DIR + File.separator + fileName);
            Files.copy(file.getInputStream(),imagePath, StandardCopyOption.REPLACE_EXISTING);
            product.setUrl(fileName);

        } catch (IOException e) {
            e.printStackTrace();
            throw new FileStorageException("File Not Found");
        }

        return product;
    }
}
