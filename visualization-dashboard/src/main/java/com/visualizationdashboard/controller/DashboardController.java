package com.visualizationdashboard.controller;

import com.visualizationdashboard.entity.DashboardEntity;
import com.visualizationdashboard.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/data")
    public ResponseEntity<Page<DashboardEntity>> getData(
            @RequestParam(required = false) Integer endYear,
            @RequestParam(required = false) String topic,
            @RequestParam(required = false) String sector,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String pestle,
            @RequestParam(required = false) String source,
            @RequestParam(required = false) String swot,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            Pageable pageable) {
        // Transfer CSV data to the database before fetching
        try {
            // Uncomment the next line if you want to trigger the CSV transfer in this endpoint
            // dashboardService.transferCSVDataToDB("file");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        Page<DashboardEntity> data = dashboardService.getDataWithFilters(
                endYear, topic, sector, region, pestle, source, swot, country, city, pageable);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestPart("file") MultipartFile file) {
        try {
            // Log the received file details
            System.out.println("Received file: " + file.getOriginalFilename());

            // Pass the MultipartFile to the transferCSVDataToDB method
            dashboardService.transferCSVDataToDB(file);
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading the file.");
        }
    }
}