package com.zindabad.ecommerce.repository;

import com.zindabad.ecommerce.model.Category;
import com.zindabad.ecommerce.model.Image;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    @Query(value = "select c.* from category c where c.parent_id is null", nativeQuery = true)
    List<Category> findCategoryByParent();

    List<Category> findCategoriesByParentId(int id);
}
