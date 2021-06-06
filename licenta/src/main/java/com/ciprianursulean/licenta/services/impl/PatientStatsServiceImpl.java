package com.ciprianursulean.licenta.services.impl;

import com.ciprianursulean.licenta.repositories.PatientStatsRepository;
import com.ciprianursulean.licenta.services.PatientStatsService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientStatsServiceImpl implements PatientStatsService {
    @Autowired
    PatientStatsRepository patientStatsRepository;

    @Override
    public List<String> getDistinctLocations() {
        return patientStatsRepository.getDistinctLocations();
    }

    @Override
    public List<String> getDistinctHospitalSections() {
        return patientStatsRepository.getDistinctHospitalSections();
    }

    @Override
    public List<Float> getQuantityByLocation(String locations) {
        return patientStatsRepository.getQuantityByLocation(locations);
    }

    @Override
    public List<Float> getQuantityByHospitalSection(String hospitalSection) {
        return patientStatsRepository.getQuantityByHospitalSection(hospitalSection);
    }

    @Override
    public List<String> getMostUsedDrugsByHospitalSection(String hospitalSection) {
        return patientStatsRepository.getMostUsedDrugsByHospitalSection(hospitalSection);
    }

    @Override
    public List<Float> getMostUsedDrugsQuantitiesByHospitalSection(String hospitalSection) {
        return patientStatsRepository.getMostUsedDrugsQuantitiesByHospitalSection(hospitalSection);
    }

    @Override
    public List<String> getMostUsedDrugsByAgeSegment(String startYear, String endYear) {
        return patientStatsRepository.getMostUsedDrugsByAgeSegment(startYear, endYear);
    }

    @Override
    public List<Float> getMostUsedQuantitiesByAgeSegment(String startYear, String endYear) {
        return patientStatsRepository.getMostUsedQuantitiesByAgeSegment(startYear, endYear);
    }

    @Override
    public List<String> getDiseaseTypesByAgeSegment(String startYear, String endYear) {
        return patientStatsRepository.getDiseaseTypesByAgeSegment(startYear, endYear);
    }

    public List<Pair<Float, String>> getQuantityByLocationAll() {
        return patientStatsRepository.getQuantityByLocationAll();
    }
}
