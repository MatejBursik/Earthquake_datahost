package com.earthquake.earthquake_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

import com.earthquake.earthquake_api.service.CsvLoaderService;

@RestController
@RequestMapping("/api/csv")
@CrossOrigin(origins = "*")
public class CsvController {

    @Autowired
    private CsvLoaderService csvLoaderService;
    
    @PostMapping("/import")
    public ResponseEntity<Map<String, Object>> importCsvFile(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        
        if (file.isEmpty()) {
            response.put("success", false);
            response.put("message", "Please select a CSV file to import");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        
        if (!file.getOriginalFilename().toLowerCase().endsWith(".csv")) {
            response.put("success", false);
            response.put("message", "Please upload a CSV file");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        
        try {
            int importedCount = csvLoaderService.importCsvStream(file.getInputStream());
            
            response.put("success", true);
            response.put("message", "CSV file imported successfully");
            response.put("importedRecords", importedCount);
            response.put("filename", file.getOriginalFilename());
            
            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error importing CSV file: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
