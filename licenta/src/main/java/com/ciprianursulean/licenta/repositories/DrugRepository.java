package com.ciprianursulean.licenta.repositories;

import com.ciprianursulean.licenta.entities.Drug;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface DrugRepository {
    Integer createDrug(String name, String company, String diseaseType, float dateOfUsage, float price, float quantity);
    Drug findById(Integer drugId);
    List<String> getDistinctDrugList();
    List<Double> getDrugQuantities(String drugName);
    List<Float> getDrugUsageDatesAsTimestamp(String drugName);
    List<Date> getDrugUsageDatesAsDate(String drugName);
    List<String> getMostUsedDrugsNames();
    List<Double> getMostUsedDrugsQuantities();
    List<String> getBiggestPriceDrugName();
    List<Double> getBiggestPriceDrug();
    List<String> getCompanyNames();
    List<Double> getCompanyQuantities();
    List<String> getDrugTrainingData(String drugName) throws IOException;
}
