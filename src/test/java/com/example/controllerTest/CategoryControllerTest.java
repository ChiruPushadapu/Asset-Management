package com.example.controllerTest;

import com.example.controller.CategoryController;
import com.example.entity.Category;
import com.example.Service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addCategory() {
        Category category = new Category();
        category.setName("Test Category");
        category.setDescription("Test Description");

        when(categoryService.addCategory(category)).thenReturn(category);

        ResponseEntity<Category> responseEntity = categoryController.addCategory(category);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(category, responseEntity.getBody());
    }

    @Test
    void updateCategory() {
        Long categoryId = 1L;
        Category updatedCategory = new Category();
        updatedCategory.setName("Updated Category");
        updatedCategory.setDescription("Updated Description");

        when(categoryService.updateCategory(categoryId, updatedCategory)).thenReturn(updatedCategory);

        ResponseEntity<Category> responseEntity = categoryController.updateCategory(categoryId, updatedCategory);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedCategory, responseEntity.getBody());
    }

    @Test
    void fetchAllCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());
        categories.add(new Category());

        when(categoryService.fetchAllCategories()).thenReturn(categories);

        List<Category> fetchedCategories = categoryController.fetchAllCategories();

        assertEquals(2, fetchedCategories.size());
    }

    @Test
    void fetchCategoryById() {
        Long categoryId = 1L;
        Category category = new Category();

        when(categoryService.fetchCategoryById(categoryId)).thenReturn(category);

        ResponseEntity<Category> responseEntity = categoryController.fetchCategoryById(categoryId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(category, responseEntity.getBody());
    }

    @Test
    void fetchCategoryByName() {
        String categoryName = "Test Category";
        Category category = new Category();

        when(categoryService.fetchCategoryByName(categoryName)).thenReturn(category);

        ResponseEntity<Category> responseEntity = categoryController.fetchCategoryByName(categoryName);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(category, responseEntity.getBody());
    }
}

