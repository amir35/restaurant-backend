package com.restaurant.serviceImpl;

import com.amir35.logutil.TimingLog;
import com.restaurant.entity.Category;
import com.restaurant.exception.ResourceNotFoundException;
import com.restaurant.repository.CategoryRepository;
import com.restaurant.responseMessage.ApiResponse;
import com.restaurant.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @TimingLog
    @Override
    public ApiResponse<Category> findByCategoryId(int categoryId) {

        System.out.println("Category Id: "+ categoryId);
        Category category = categoryRepository.findByCategoryId(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", categoryId)
        );

        log.info("Category: " + category);

//        if (category.isPresent()) {
//            log.info("Category: " + category.get());
//            return new ApiResponse<>(category.get(), "Category Found");
//        } else {
//            log.info("Category not found for ID: " + categoryId);
//            return new ApiResponse<>(null, "Category Not Found");
//        }

        return new ApiResponse<>(category, "Category Found");
    }


    @TimingLog
    @Override
    public ApiResponse<List<Category>> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();

        log.info("All Categories: " , categories);

        if(categories.isEmpty())
        {
            return new ApiResponse<>(null, "Owners List is Empty");
        } else {
            return new ApiResponse<>(categories, "Owners List is fetched");
        }
    }

    @TimingLog
    @Override
    public ApiResponse<Category> saveCategory(Category category) {

        ApiResponse apiResponse;
        Category temp = new Category();

        temp.setCategoryName(category.getCategoryName());
        temp.setCategoryStatus(category.getCategoryStatus());

        Category saveCategory = categoryRepository.save(temp);

        apiResponse = new ApiResponse(saveCategory, "Category Inserted");
        return apiResponse;

    }

    @TimingLog
    @Override
    public ApiResponse<Category> updateCategory(int id, Category category) {

        category.setCategoryId(id);

        Optional<Category> categoryOptional = categoryRepository.findById(id);
        ApiResponse apiResponse;

        if(categoryOptional.isPresent())
        {
            Category updateCategory = categoryRepository.save(category);

            apiResponse = new ApiResponse(updateCategory, "Category Record successfully updated");
        } else
        {
            apiResponse = new ApiResponse(null, "Category Record not updated");
        }
        return apiResponse;
    }

    @TimingLog
    @Override
    public ApiResponse<Category> deleteCategory(int id) {

        Optional<Category> categoryOptional = categoryRepository.findById(id);

        ApiResponse apiResponse;

        if(categoryOptional.isPresent())
        {
            categoryRepository.deleteById(id);
            apiResponse = new ApiResponse(categoryOptional.get(), "Owner successfully deleted");
        } else {
            apiResponse = new ApiResponse(null, "Owner Not deleted");
        }
        return apiResponse;
    }
}