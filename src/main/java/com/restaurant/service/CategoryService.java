package com.restaurant.service;

import com.restaurant.entity.Category;
import com.restaurant.responseMessage.ApiResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CategoryService {

    public ApiResponse<Category> findByCategoryId(int categoryId);

    public ApiResponse<List<Category>> getAllCategories();

    public ApiResponse<Category> saveCategory(Category category);

    public ApiResponse<Category> updateCategory(int id, Category category);

    public ApiResponse<Category> deleteCategory(int id);
}