package com.restaurant.repository;

import com.restaurant.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {

    public Owner findByOwnerId(int ownerId);

    public Owner findByUsername(String username);

}