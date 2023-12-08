package com.restaurant.controller;

import com.restaurant.entity.Credential;
import com.restaurant.entity.OwnerDto;
import com.restaurant.responseMessage.OwnerResponse;
import com.restaurant.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping
public class AuthenticationController {
    @Autowired
    private OwnerService ownerService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public OwnerResponse<OwnerDto> authenticateUser(@RequestBody Credential credential) {
        System.out.println("Inside Authentication Controller");
        OwnerResponse<OwnerDto> ownerResponse = ownerService.authenticateUser(credential.getUsername());

        if(ownerResponse.getOwner() != null)
        {
            return ResponseEntity.status(HttpStatus.FOUND).body(ownerResponse).getBody();
        }else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ownerResponse).getBody();
    }
}
