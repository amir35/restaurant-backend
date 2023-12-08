package com.restaurant.controller;

import com.restaurant.entity.Category;
import com.restaurant.responseMessage.ApiResponse;
import com.restaurant.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/{categoryId}", method = RequestMethod.GET)
    public ApiResponse<Category> getCategoryById(@PathVariable int categoryId) {

        ApiResponse<Category> apiResponse = categoryService.findByCategoryId(categoryId);

        if(apiResponse.getData() != null)
        {
            return ResponseEntity.status(HttpStatus.FOUND).body(apiResponse).getBody();
        }else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse).getBody();
    }

    @GetMapping
    public ApiResponse<List<Category>> getAllCategories()
    {
        ApiResponse<List<Category>> apiResponse = categoryService.getAllCategories();

        return ResponseEntity.status(apiResponse.getMessage().equals("Category List is fetched") ? HttpStatus.FOUND : HttpStatus.NO_CONTENT).body(apiResponse).getBody();
    }

    @RequestMapping(value = "/saveCategory", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<Category>> saveCategory(@RequestBody Category category) {

        ApiResponse<Category> apiResponse = categoryService.saveCategory(category);

        if(apiResponse.getData() != null)
        {
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> updateOwner(@PathVariable int id, @RequestBody Category category)
    {
        ApiResponse<Category> apiResponse = categoryService.updateCategory(id, category);

        if(apiResponse.getData() != null)
        {
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Category> deleteCategory(@PathVariable int id)
    {
        ApiResponse<Category> apiResponse = categoryService.deleteCategory(id);

        if(apiResponse.getData() != null)
        {
            return ResponseEntity.status(HttpStatus.FOUND).body(apiResponse).getBody();
        }else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse).getBody();

    }

}