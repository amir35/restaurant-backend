package com.restaurant.repository;

import com.restaurant.entity.Category;
import com.restaurant.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findByCategory(Category category);


}