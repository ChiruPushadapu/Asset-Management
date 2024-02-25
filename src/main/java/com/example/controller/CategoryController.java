package com.example.controller;

import com.example.Service.CategoryService;
import com.example.entity.Asset;
import com.example.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/addCategory") // add new category
    public ResponseEntity<Category> addCategory(@RequestBody Category category)
    {
        Category newCategory = categoryService.addCategory(category);
        return ResponseEntity.ok(newCategory);
    }

    @PutMapping("/{categoryId}") // updating category
    public ResponseEntity<Category> updateCategory(@PathVariable Long categoryId, @RequestBody Category updatedCategory)
    {
        Category category = categoryService.updateCategory(categoryId, updatedCategory);
        return (category != null) ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
    }

    @GetMapping("/getCategories") // get all categories
    public ResponseEntity<List<Category>> fetchAllCategories(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size)
    {
        List<Category> categories = categoryService.fetchAllCategories(page, size);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/getCategory/id/{categoryId}")  // get info of a particular asset
    public ResponseEntity<Category> fetchCategoryById(@PathVariable Long categoryId)
    {
        Category category = categoryService.fetchCategoryById(categoryId);
        if(category != null)
        {
            return ResponseEntity.ok(category);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getCategory/name/{name}")  // get info of a particular asset
    public ResponseEntity<Category> fetchCategoryByName(@PathVariable String name)
    {
        Category category = categoryService.fetchCategoryByName(name);
        if(category != null)
        {
            return ResponseEntity.ok(category);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }
}
