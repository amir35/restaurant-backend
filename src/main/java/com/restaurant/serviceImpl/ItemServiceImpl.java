package com.restaurant.serviceImpl;

import com.amir35.logutil.TimingLog;
import com.restaurant.entity.Category;
import com.restaurant.entity.Item;
import com.restaurant.repository.CategoryRepository;
import com.restaurant.repository.ItemRepository;
import com.restaurant.responseMessage.ApiResponse;
import com.restaurant.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @TimingLog
    public ApiResponse<Item> findByItemId(int itemId) {
        Optional<Item> item = itemRepository.findById(itemId);

        if(item.isPresent())
        {
            return new ApiResponse<>(item.get(), "Item Found");
        } else {
            return new ApiResponse<>(null, "Item Not Found");
        }

    }


    @TimingLog
    @Override
    public ApiResponse<List<Item>> getAllItem() {

        List<Item> items = itemRepository.findAll();

        log.info("All Item: " , items);

        if(items.isEmpty())
        {
            return new ApiResponse<>(null, "Items List is Empty");
        } else {
            return new ApiResponse<>(items, "Item List is fetched");
        }

    }

    @TimingLog
    @Override
    public ApiResponse<Item> saveItem(Item item) {
        ApiResponse apiResponse;

        Optional<Category> category = categoryRepository.findByCategoryId(item.getCategory().getCategoryId());

        if (category.isEmpty()) {
            // Handle the case where the category doesn't exist
            return new ApiResponse<>(null, "Category not found");
        }

        // Create the Item object and set the category
        Item itemTemp = new Item();
        itemTemp.setTitle(item.getTitle());
        itemTemp.setCategory(category.get());
        itemTemp.setPrice(item.getPrice());
        itemTemp.setDescription(item.getDescription());
        itemTemp.setImageId(item.getImageId());

        Item saveItem = itemRepository.save(itemTemp);

        apiResponse = new ApiResponse(saveItem, "Item Inserted");
        return apiResponse;
    }

    @TimingLog
    @Override
    public ApiResponse<Item> updateItem(int id, Item item) {

        item.setId(id);

        Optional<Item> itemOptional = itemRepository.findById(id);
        ApiResponse apiResponse;

        if(itemOptional.isPresent())
        {
            Item updateItem = itemRepository.save(item);
            apiResponse = new ApiResponse(updateItem, "Item Record successfully updated");
        }else
        {
            apiResponse = new ApiResponse(null, "Item Record not updated");
        }

        return apiResponse;
    }

    @TimingLog
    @Override
    public ApiResponse<Item> deleteItem(int id) {

        Optional<Item> itemOptional = itemRepository.findById(id);

        ApiResponse apiResponse;

        if(itemOptional.isPresent())
        {
            itemRepository.deleteById(id);
            apiResponse = new ApiResponse(itemOptional.get(), "Item successfully deleted");
        } else {
            apiResponse = new ApiResponse(null, "Item Not deleted");
        }

        return apiResponse;
    }

    @TimingLog
    @Override
    public ApiResponse<List<Item>> getItemsByCategoryId(int categoryId) {

        Category category = new Category();

        category.setCategoryId(categoryId);

        List<Item> items = itemRepository.findByCategory(category);

        if(items.isEmpty())
        {
            return new ApiResponse<>(null, "Items List is Empty");
        } else {
            return new ApiResponse<>(items, "Item List is fetched");
        }
    }
}