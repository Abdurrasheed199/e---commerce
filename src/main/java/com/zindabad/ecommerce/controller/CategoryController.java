package com.zindabad.ecommerce.controller;

import com.zindabad.ecommerce.model.Category;
import com.zindabad.ecommerce.repository.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {


    final
    CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    //Create category..
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path = "/category/create", method = RequestMethod.POST)
    public String createCategory(@RequestParam String name, String parentId){

        if((parentId == null) || (parentId == "")){
            Category category = new Category();
            category.setName(name);
            category.setParent(null);
            categoryRepository.save(category);
            return "Created Successfully.";
        }else {
            Category parent = categoryRepository.findById(Integer.parseInt(parentId)).get();
            Category category = new Category();

            category.setName(name);
            category.setParent(parent);
            categoryRepository.save(category);

            return "Created Successfully.";
        }
    }

    //Get one category details...
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path="/category/{id}", method = RequestMethod.GET)
    public Category findById(@PathVariable int id){
        Category category = categoryRepository.findById(id).get();
        return category;
    }

    //Get the list of categories...
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path="/categories", method = RequestMethod.GET)
    public List categories() {
        return (List)categoryRepository.findAll();
    }

    //Get the list of Super categories...
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path="/categories/super", method = RequestMethod.GET)
    public List superCategories() {
        return (List)categoryRepository.findCategoryByParent();
    }

    //Get the list of Sub categories...
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path="/categories/sub/{id}", method = RequestMethod.GET)
    public List subCategories(@PathVariable("id") int id) {
        return (List)categoryRepository.findCategoriesByParentId(id);
    }

    //    //Update category..
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path = "/category/update", method = RequestMethod.PUT)
    public String update(@RequestParam int id, @RequestParam String name) {
        Category category = categoryRepository.findById(id).get();
        category.setName(name);

        categoryRepository.save(category);

        return "Successfully Updated";
    }

    //Delete category...
    @CrossOrigin(exposedHeaders = "http://localhost:8896")
    @RequestMapping(path = "/category/delete/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable int id) {
        Category category = categoryRepository.findById(id).get();

        categoryRepository.delete(category);

        return "Category successfully deleted.";
    }
}
