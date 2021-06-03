package com.ciprianursulean.licenta.controllers;

import com.ciprianursulean.licenta.entities.Drug;
import com.ciprianursulean.licenta.services.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/drugs")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DrugController {
    @Autowired
    DrugService drugService;

    @GetMapping("/distinct-names")
    public ResponseEntity<List<String>> getDistinctDrugNames() {
        return new ResponseEntity<>(drugService.getDistinctDrugList(), HttpStatus.OK);
    }

    @GetMapping("/get-quantities")
    public  ResponseEntity<List<Double>> getDrugQuantities(@RequestParam("name") String drugName) {
        return new ResponseEntity<>(drugService.getDrugQuantities(drugName), HttpStatus.OK);
    }

    @GetMapping("/get-dates-timestamp")
    public ResponseEntity<List<Float>> getDrugDateOfUsageAsTimestamp(@RequestParam("name") String drugName) {
        return new ResponseEntity<>(drugService.getDrugUsageDatesAsTimestamp(drugName), HttpStatus.OK);
    }

    @GetMapping("/get-dates")
    public ResponseEntity<List<Date>> getDrugDateOfUsageAsDate(@RequestParam("name") String drugName) {
        return new ResponseEntity<>(drugService.getDrugUsageDatesAsDate(drugName), HttpStatus.OK);
    }

    @GetMapping("/get-training-drug-data")
    public ResponseEntity<List<String>> getDrugTraingData(@RequestParam("name") String drugName) {
        return new ResponseEntity<>(drugService.getDrugTrainingData(drugName), HttpStatus.OK);
    }

    @GetMapping("/most-used-drug-names")
    public ResponseEntity<List<String>> getMostUsedDrugNames() {
        return new ResponseEntity<>(drugService.getMostUsedDrugsNames(), HttpStatus.OK);
    }

    @GetMapping("/most-used-drug-quantities")
    public ResponseEntity<List<Double>> getMostUsedDrugQuantities() {
        return new ResponseEntity<>(drugService.getMostUsedDrugsQuantities(), HttpStatus.OK);
    }

    @GetMapping("/biggest-prices-name")
    public ResponseEntity<List<String>> getBiggestPriceNames() {
        return new ResponseEntity<>(drugService.getBiggestPriceDrugName(), HttpStatus.OK);
    }

    @GetMapping("/biggest-prices-price")
    public ResponseEntity<List<Double>> getBiggestPrices() {
        return new ResponseEntity<>(drugService.getBiggestPriceDrug(), HttpStatus.OK);
    }

    @GetMapping("/company-names")
    public ResponseEntity<List<String>> getCompanyNames() {
        return new ResponseEntity<>(drugService.getCompanyNames(), HttpStatus.OK);
    }

    @GetMapping("/company-quantities")
    public ResponseEntity<List<Double>> getCompanyQuantities() {
        return new ResponseEntity<>(drugService.getCompanyQuantities(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<String> saveDrugToDatabase(@RequestBody Drug drug) {
        drugService.createDrug(drug.getName(), drug.getCompany(), drug.getDiseaseType(), drug.getDateOfUsage(), drug.getPrice(), drug.getQuantity());
        return new ResponseEntity<>("Drug saved successfully!", HttpStatus.CREATED);
    }
}
