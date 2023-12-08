package com.restaurant.controller;

import com.restaurant.entity.Bill;
import com.restaurant.responseMessage.ApiResponse;
import com.restaurant.service.BillService;
import com.restaurant.utils.CafeUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/bill")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping(path = "/generateReport")
    public ResponseEntity<byte[]> generateReport(@RequestBody Map<String, Object> requestMap) {
        try {
            String fileName, uuid;
            uuid = CafeUtils.getUUID();
            fileName = uuid+ ".pdf";

            byte[] fileContent = billService.generateReport(requestMap, uuid).getBody();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=" + fileName);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileContent);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/getBills")
    ApiResponse<List<Bill>> getBills() {
        try {
            ApiResponse<List<Bill>> apiResponse = billService.getBills();

            return ResponseEntity.status(apiResponse.getMessage().equals("All Bills are fetched") ? HttpStatus.FOUND : HttpStatus.NO_CONTENT).body(apiResponse).getBody();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(path = "/getPdf")
    ResponseEntity<byte[]> getPdf(@RequestBody Map<String, Object> requestMap) {
        try {
            byte[] fileContent = billService.getPdf(requestMap).getBody();
            return ResponseEntity.ok().body(fileContent);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Bill> deleteBill(@PathVariable int id)
    {
        ApiResponse<Bill> apiResponse = billService.deleteBill(id);

        if(apiResponse.getData() != null)
        {
            return ResponseEntity.status(HttpStatus.FOUND).body(apiResponse).getBody();
        }else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse).getBody();

    }
}
