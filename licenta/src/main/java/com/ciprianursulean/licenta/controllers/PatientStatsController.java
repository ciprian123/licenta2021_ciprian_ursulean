package com.ciprianursulean.licenta.controllers;

import com.ciprianursulean.licenta.services.PatientStatsService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients-stats")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PatientStatsController {
    @Autowired
    PatientStatsService patientStatsService;

    @GetMapping("/distinct-locations")
    public ResponseEntity<List<String>> getDistinctPatientLocations() {
        return new ResponseEntity<>(patientStatsService.getDistinctLocations(), HttpStatus.OK);
    }

    @GetMapping("/distinct-hospital-section")
    public ResponseEntity<List<String>> getDistinctPatientHospitalSections() {
        return new ResponseEntity<>(patientStatsService.getDistinctHospitalSections(), HttpStatus.OK);
    }

    @GetMapping("/quantity-by-location")
    public ResponseEntity<List<Float>> getQuantityByLocation(@RequestParam("location") String location) {
        return new ResponseEntity<>(patientStatsService.getQuantityByLocation(location), HttpStatus.OK);
    }

    @GetMapping("/quantity-by-location-all")
    public ResponseEntity<List<Pair<Float, String>>> getQuantityByLocation() {
        return new ResponseEntity<>(patientStatsService.getQuantityByLocationAll(), HttpStatus.OK);
    }

    @GetMapping("/quantity-by-hospital-section")
    public ResponseEntity<List<Float>> getQuantityByHospitalSection(@RequestParam("section") String location) {
        return new ResponseEntity<>(patientStatsService.getQuantityByHospitalSection(location), HttpStatus.OK);
    }

    @GetMapping("/most-used-drugs-by-hospital-section")
    public ResponseEntity<List<String>> getMostUsedDrugsByHospitalSection(@RequestParam("section") String location) {
        return new ResponseEntity<>(patientStatsService.getMostUsedDrugsByHospitalSection(location), HttpStatus.OK);
    }

    @GetMapping("/most-used-drugs-quantities-by-hospital-section")
    public ResponseEntity<List<Float>> getMostUsedDrugsQuantitiesByHospitalSection(@RequestParam("section") String location) {
        return new ResponseEntity<>(patientStatsService.getMostUsedDrugsQuantitiesByHospitalSection(location), HttpStatus.OK);
    }

    @GetMapping("/most-used-drugs-by-age-segment")
    public ResponseEntity<List<String>> getMostUsedDrugsByAgeSegment(@RequestParam("startYear") String startYear, @RequestParam("endYear") String endYear) {
        return new ResponseEntity<>(patientStatsService.getMostUsedDrugsByAgeSegment(startYear, endYear), HttpStatus.OK);
    }

    @GetMapping("/most-used-quantities-by-age-segment")
    public ResponseEntity<List<Float>> getMostUsedQuantitiesByAgeSegment(@RequestParam("startYear") String startYear, @RequestParam("endYear") String endYear) {
        return new ResponseEntity<>(patientStatsService.getMostUsedQuantitiesByAgeSegment(startYear, endYear), HttpStatus.OK);
    }

    @GetMapping("/disease-type-by-age-segment")
    public ResponseEntity<List<String>> getDiseaseTypesByAgeSegment(@RequestParam("startYear") String startYear, @RequestParam("endYear") String endYear) {
        return new ResponseEntity<>(patientStatsService.getDiseaseTypesByAgeSegment(startYear, endYear), HttpStatus.OK);
    }
}
