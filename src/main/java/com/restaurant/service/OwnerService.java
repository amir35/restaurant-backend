package com.restaurant.service;

import com.restaurant.entity.OwnerDto;
import com.restaurant.responseMessage.OwnerResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OwnerService {

    public OwnerResponse<OwnerDto> authenticateUser(String username);

    public OwnerResponse<OwnerDto> findByOwnerId(int ownerId);

    public OwnerResponse<List<OwnerDto>> getAllOwners();

    public OwnerResponse<OwnerDto> saveOwner(OwnerDto owner);

    public OwnerResponse<OwnerDto> updateOwner(int id, OwnerDto owner);

    public OwnerResponse<OwnerDto> deleteOwner(int id);
}