package com.restaurant.repository;

import com.restaurant.entity.Category;
import com.restaurant.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    public Optional<Category> findByCategoryId(int categoryId);

}