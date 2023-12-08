package com.restaurant.controller;

import com.restaurant.entity.Item;
import com.restaurant.responseMessage.ApiResponse;
import com.restaurant.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/category/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
    public ApiResponse<Item> getItemById(@PathVariable int itemId) {

        ApiResponse<Item> apiResponse = itemService.findByItemId(itemId);

        if(apiResponse.getData() != null)
        {
            return ResponseEntity.status(HttpStatus.FOUND).body(apiResponse).getBody();
        }else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse).getBody();
    }

    @GetMapping
    @CrossOrigin
    public ApiResponse<List<Item>> getAllItems()
    {
        ApiResponse<List<Item>> apiResponse = itemService.getAllItem();

        return ResponseEntity.status(apiResponse.getMessage().equals("Item List is fetched") ? HttpStatus.FOUND : HttpStatus.NO_CONTENT).body(apiResponse).getBody();
    }

    @RequestMapping(value = "/saveItem", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<Item>> saveItem(@RequestBody Item item) {

        ApiResponse<Item> apiResponse = itemService.saveItem(item);

        if(apiResponse.getData() != null)
        {
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Item>> updateItem(@PathVariable int id, @RequestBody Item item)
    {
        ApiResponse<Item> apiResponse = itemService.updateItem(id, item);

        if(apiResponse.getData() != null)
        {
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Item> deleteItem(@PathVariable int id)
    {
        ApiResponse<Item> apiResponse = itemService.deleteItem(id);

        if(apiResponse.getData() != null)
        {
            return ResponseEntity.status(HttpStatus.FOUND).body(apiResponse).getBody();
        }else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse).getBody();

    }

    @GetMapping("/byCategory/{categoryId}")
    public ApiResponse<List<Item>> getItemsByCategory(@PathVariable int categoryId) {

        ApiResponse<List<Item>> apiResponse = itemService.getItemsByCategoryId(categoryId);

        return ResponseEntity.status(apiResponse.getMessage().equals("Item List is fetched") ? HttpStatus.FOUND : HttpStatus.NO_CONTENT).body(apiResponse).getBody();

    }

}