package com.restaurant.controller;

import com.restaurant.entity.OwnerDto;
import com.restaurant.responseMessage.OwnerResponse;
import com.restaurant.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/owner")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @RequestMapping(value = "/{ownerId}", method = RequestMethod.GET)
    public OwnerResponse<OwnerDto> getOwnerById(@PathVariable int ownerId) {

        OwnerResponse<OwnerDto> ownerResponse = ownerService.findByOwnerId(ownerId);

        if(ownerResponse.getOwner() != null)
        {
            return ResponseEntity.status(HttpStatus.FOUND).body(ownerResponse).getBody();
        }else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ownerResponse).getBody();
    }

    @GetMapping
    public OwnerResponse<List<OwnerDto>> getAllOwners()
    {
        OwnerResponse<List<OwnerDto>> ownerResponse = ownerService.getAllOwners();

        return ResponseEntity.status(ownerResponse.getMessage().equals("Owner List is fetched") ? HttpStatus.FOUND : HttpStatus.NO_CONTENT).body(ownerResponse).getBody();
    }

    @RequestMapping(value = "/saveOwner", method = RequestMethod.POST)
    public ResponseEntity<OwnerResponse<OwnerDto>> saveOwner(@RequestBody OwnerDto owner) {

        OwnerResponse<OwnerDto> ownerResponse = ownerService.saveOwner(owner);

        if(ownerResponse.getOwner() != null)
        {
            return ResponseEntity.status(HttpStatus.CREATED).body(ownerResponse);
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ownerResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OwnerResponse<OwnerDto>> updateOwner(@PathVariable int id, @RequestBody OwnerDto owner)
    {
        OwnerResponse<OwnerDto> ownerResponse = ownerService.updateOwner(id, owner);

        if(ownerResponse.getOwner() != null)
        {
            return ResponseEntity.status(HttpStatus.OK).body(ownerResponse);
        }else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ownerResponse);
    }

    @DeleteMapping("/{id}")
    public OwnerResponse<OwnerDto> deleteOwner(@PathVariable int id)
    {
        OwnerResponse<OwnerDto> ownerResponse = ownerService.deleteOwner(id);

        if(ownerResponse.getOwner() != null)
        {
            return ResponseEntity.status(HttpStatus.FOUND).body(ownerResponse).getBody();
        }else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ownerResponse).getBody();

    }

}