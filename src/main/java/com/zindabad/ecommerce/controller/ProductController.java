package com.zindabad.ecommerce.controller;


import com.zindabad.ecommerce.model.Category;
import com.zindabad.ecommerce.model.Product;
import com.zindabad.ecommerce.repository.ProductRepository;
import com.zindabad.ecommerce.service.ProductServiceImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ProductController {

    final
    ProductRepository productRepository;

    final
    ProductServiceImpl productServiceImpl;

    public ProductController(ProductRepository productRepository, ProductServiceImpl productServiceImpl) {
        this.productRepository = productRepository;
        this.productServiceImpl = productServiceImpl;
    }

    //Create product.
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(value = "/product/create", method = RequestMethod.POST)
    public String create(@RequestParam String name, @RequestParam double price, @RequestParam List<String> categories, @RequestParam("file") MultipartFile file, @RequestParam String description){
        Product product = productServiceImpl.createProduct(name, price, categories, file, description);
        productRepository.save(product);
        return "Product created successfully.";
    }

    //Get all the products.
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path = "/products", method = RequestMethod.GET)
    public List products(){

        return (List) productRepository.findAll();
    }

    // Get the details of a product by its id.
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public Product product(@PathVariable("id") long id){
        Product product = productRepository.findById(id).get();

        return product;
    }

    //Get all products from a category.
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path = "/products/category/{id}", method = RequestMethod.GET)
    public List subProducts(@PathVariable("id") int id){

        List<Product> products = productServiceImpl.products(id);
        return  products;
    }

    //Update product details.
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path = "/product/update", method = RequestMethod.PUT)
    public String update(@RequestParam long id, @RequestParam String name, @RequestParam double price, @RequestParam String description)
    {
        Product product = productRepository.findById(id).get();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);

        productRepository.save(product);

        return "Successfully Updated";
    }

    //Delete a product.
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path = "/product/delete/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") long id){
        Product product = productRepository.findById(id).get();

        productRepository.delete(product);
        return "Product deleted successfully";
    }

//    @CrossOrigin(exposedHeaders = "http://localhost:8098")
//    @RequestMapping(path = "/testCode", method = RequestMethod.GET)
//    public List Test()
//    {
//
//        return UUID.randomUUID().toString();
//    }

}


