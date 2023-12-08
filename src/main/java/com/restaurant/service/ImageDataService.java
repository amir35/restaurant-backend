package com.restaurant.service;

import com.restaurant.entity.Category;
import com.restaurant.entity.ImageData;
import com.restaurant.responseMessage.ApiResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
public interface ImageDataService {

    public ApiResponse<ImageData> saveImageData(MultipartFile file) throws IOException;

    public ApiResponse<ImageData> getImageByImageId(int imageId);
}