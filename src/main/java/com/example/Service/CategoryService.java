package com.example.Service;

import com.example.Exception.AssetManagementException;
import com.example.Exception.CategoryManagementException;
import com.example.entity.Asset;
import com.example.entity.Category;
import com.example.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }

    // q1 : category management
    public Category addCategory(Category category)
    {
        return categoryRepository.save(category);
    }

    // q2 : update category
    public Category updateCategory(Long categoryId, Category updatedCategory)
    {
        if (categoryId == null) {
            throw new IllegalArgumentException("Category ID cannot be null");
        }
        Optional<Category> existingCategory = categoryRepository.findById(categoryId);
        if(existingCategory.isPresent()) {
            Category category = existingCategory.get();
            category.setName(updatedCategory.getName());
            category.setDescription(updatedCategory.getDescription());
            return categoryRepository.save(category);
        }
        else
        {
            throw new CategoryManagementException("Category not found with Id : " + categoryId);
        }
    }

    // q3 : list all categories
    public List<Category> fetchAllCategories(int page, int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        return categoryPage.getContent();
    }

    // additional
    // search category by id
    public Category fetchCategoryById(Long categoryId)
    {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        //return categoryOptional.get();
       return categoryOptional.orElseThrow(() -> new AssetManagementException("Category not found with ID: " + categoryId));
    }

    // search category by name
    public Category fetchCategoryByName(String name)
    {
        Optional<Category> categoryOptional = categoryRepository.findByName(name);
        return categoryOptional.orElseThrow(() -> new AssetManagementException("Category not found with ID: " + name));
    }

}
