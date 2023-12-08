package com.restaurant.service;

import com.restaurant.entity.Bill;
import com.restaurant.responseMessage.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface BillService {

    ResponseEntity<byte[]> generateReport(Map<String, Object> requestMap, String uuid);

    ApiResponse<List<Bill>> getBills();

    ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap);

    ApiResponse<Bill> deleteBill(int id);
}
