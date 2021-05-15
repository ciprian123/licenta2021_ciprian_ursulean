package com.ciprianursulean.licenta.repositories;

import com.ciprianursulean.licenta.entities.Drug;

import java.util.Date;
import java.util.List;

public interface DrugRepository {
    Integer createDrug(String name, String company, String diseaseType, Date dateOfUsage, float price, float quantity);
    Drug findById(Integer drugId);
    List<String> getDistinctDrugList();
    List<Double> getDrugQuantities(String drugName);
    List<Date> getDrugUsageDates(String drugName);
    List<String> getMostUsedDrugsNames();
    List<Double> getMostUsedDrugsQuantities();
    List<String> getBiggestPriceDrugName();
    List<Double> getBiggestPriceDrug();
    List<String> getCompanyNames();
    List<Double> getCompanyQuantities();
}
