package com.restaurant.serviceImpl;

import com.amir35.logutil.TimingLog;
import com.restaurant.entity.ImageData;
import com.restaurant.repository.ImageDataRepository;
import com.restaurant.responseMessage.ApiResponse;
import com.restaurant.service.ImageDataService;
import com.restaurant.utils.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class ImageDataServiceImpl implements ImageDataService {
    @Autowired
    private ImageDataRepository imageDataRepository;

    @TimingLog
    @Override
    public ApiResponse<ImageData> saveImageData(MultipartFile file) throws IOException {

        ApiResponse apiResponse;

        if (file == null || file.isEmpty()) {
            // Handle the case where the file is null or empty
            return new ApiResponse<>(null, "File is null or empty");
        }

        ImageData img = new ImageData(file.getOriginalFilename(), file.getContentType(),
                ImageUtil.compressBytes(file.getBytes()));

        ImageData saveImageData = imageDataRepository.save(img);

        // Check if the file was successfully inserted
        if (saveImageData.getId() != null) {
            return new ApiResponse<>(saveImageData, "Image Uploaded Successfully");
        } else {
            // Handle the case where the file insertion failed
            return new ApiResponse<>(null, "Failed to save image data");
        }
    }

    @Override
    public ApiResponse<ImageData> getImageByImageId(int imageId) {

        final Optional<ImageData> retrievedImage = imageDataRepository.findById(imageId);

        if(retrievedImage.isPresent())
        {
            ImageData img = new ImageData(retrievedImage.get().getId(), retrievedImage.get().getName(),
                    retrievedImage.get().getType(),
                    ImageUtil.decompressBytes(retrievedImage.get().getPicByte()));
            return new ApiResponse<>(img, "Image Retrieved");
        } else
            return new ApiResponse<>(null, "Image Not Retrieved");

    }

}