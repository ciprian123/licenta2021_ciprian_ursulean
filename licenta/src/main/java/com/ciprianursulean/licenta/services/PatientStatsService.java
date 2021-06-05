package com.ciprianursulean.licenta.services;

import java.util.List;

public interface PatientStatsService {
    List<String> getDistinctLocations();
    List<String> getDistinctHospitalSections();
    List<Float> getQuantityByLocation(String locations);
    List<Float> getQuantityByHospitalSection(String hospitalSection);
    List<String> getMostUsedDrugsByHospitalSection(String hospitalSection);
    List<Float> getMostUsedDrugsQuantitiesByHospitalSection(String hospitalSection);
    List<String> getMostUsedDrugsByAgeSegment(String startYear, String endYear);
    List<Float> getMostUsedQuantitiesByAgeSegment(String startYear, String endYear);
    List<String> getDiseaseTypesByAgeSegment(String startYear, String endYear);
}
