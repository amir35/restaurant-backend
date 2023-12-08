package com.restaurant.controller;

import com.restaurant.entity.ImageData;
import com.restaurant.entity.Item;
import com.restaurant.repository.ImageDataRepository;
import com.restaurant.responseMessage.ApiResponse;
import com.restaurant.service.ImageDataService;
import com.restaurant.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "image")
public class ImageUploadController {

    @Autowired
    ImageDataRepository imageDataRepository;

    @Autowired
    ImageDataService imageDataService;

    @PostMapping(value  = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<ImageData>> uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {

        ApiResponse<ImageData> apiResponse = imageDataService.saveImageData(file);

        if(apiResponse.getData() != null)
        {
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @GetMapping(path = { "/get/{imageId}" })
    public ResponseEntity<Optional<ImageData>> getImage(@PathVariable("imageId") int imageId) throws IOException {

        ApiResponse<ImageData> apiResponse = imageDataService.getImageByImageId(imageId);

        if(apiResponse.getData() != null)
        {
            return ResponseEntity.status(HttpStatus.CREATED).body(Optional.ofNullable(apiResponse.getData()));
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

}
