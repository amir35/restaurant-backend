package com.restaurant.serviceImpl;

import com.restaurant.entity.Bill;
import com.restaurant.repository.BillRepository;
import com.restaurant.responseMessage.ApiResponse;
import com.restaurant.service.BillService;
import com.restaurant.utils.PdfFileCreation;
import org.apache.pdfbox.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    BillRepository billRepository;
    @Autowired
    private PdfFileCreation pdfFileCreation;

    @Override
    public ResponseEntity<byte[]> generateReport(Map<String, Object> requestMap, String uuid) {
        try {

            if(validateRequestMap(requestMap)) {
                insertBill(requestMap, uuid); // Insert bill into the database after generating the content
                byte[] byteArray = pdfFileCreation.generatePdfContent(requestMap);
                return new ResponseEntity<>(byteArray, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ApiResponse<List<Bill>> getBills() {

        List<Bill> list = new ArrayList<>();
        list = billRepository.getAllBills();

        return new ApiResponse<>(list, "All Bills records are fetched");
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {

        System.out.println("Inside getPdf : "+requestMap);
        byte[] byteArray = new byte[0];

        if(!requestMap.containsKey("uuid") && validateRequestMap(requestMap))
            return new ResponseEntity<>(byteArray, HttpStatus.BAD_REQUEST);

        String uuid = requestMap.get("uuid").toString();

        try{
            byteArray = pdfFileCreation.generatePdfContent(requestMap);
            return new ResponseEntity<>(byteArray, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void insertBill(Map<String, Object> requestMap,String uuid) {
        try {
            Bill bill = new Bill();

            bill.setUuid(uuid);
            bill.setName((String)requestMap.get("name"));
            bill.setEmail((String)requestMap.get("email"));
            bill.setContactNumber((String)requestMap.get("contactNumber"));
            bill.setPaymentMethod((String)requestMap.get("paymentMethod"));
            bill.setTotal(Integer.parseInt((String)requestMap.get("totalAmount")));
            bill.setProductDetails((String)requestMap.get("productDetails"));
            //bill.setCreatedBy(jwtFilter.getCurrentUser());
            bill.setCreatedBy("amir786");

            billRepository.save(bill);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public ApiResponse<Bill> deleteBill(int id) {

        Optional<Bill> billOptional = billRepository.findById(id);
        ApiResponse apiResponse;

        if(billOptional.isPresent())
        {
            billRepository.deleteById(id);
            apiResponse = new ApiResponse(billOptional.get(), "Bill successfully deleted");
        } else {
            apiResponse = new ApiResponse(null, "Bill Not deleted");
        }
        return apiResponse;
    }

    private boolean validateRequestMap(Map<String, Object> requestMap) {
        return requestMap.containsKey("name") &&
                requestMap.containsKey("contactNumber") &&
                requestMap.containsKey("email") &&
                requestMap.containsKey("paymentMethod") &&
                requestMap.containsKey("productDetails") &&
                requestMap.containsKey("totalAmount");
    }

    private byte[] getByteArray(String filePath) throws Exception {
        try {
            File initiateFile = new File(filePath);
            InputStream targetStream = new FileInputStream(initiateFile);
            byte[] byteArray = IOUtils.toByteArray(targetStream);
            targetStream.close();
            return byteArray;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
