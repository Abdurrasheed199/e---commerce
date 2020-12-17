package com.zindabad.ecommerce.repository;


import com.zindabad.ecommerce.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query(value = "select p.* from product p inner join product_category pc on pc.product_id=p.id where pc.category_id = :id", nativeQuery = true)
    List<Product> findProductsByCategoryId(int id);

    @Query(value = "SELECT p.* FROM product p where p.name like %:searchText% ", nativeQuery = true)
    List<Product> search(String searchText);

}



