package com.restaurant.service;

import com.restaurant.entity.Category;
import com.restaurant.entity.Item;
import com.restaurant.responseMessage.ApiResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ItemService {

    public ApiResponse<Item> findByItemId(int itemId);

    public ApiResponse<List<Item>> getAllItem();

    public ApiResponse<Item> saveItem(Item item);

    public ApiResponse<Item> updateItem(int id, Item item);

    public ApiResponse<Item> deleteItem(int id);

    public ApiResponse<List<Item>> getItemsByCategoryId(int categoryId);
}