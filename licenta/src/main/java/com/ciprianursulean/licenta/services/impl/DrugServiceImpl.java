package com.ciprianursulean.licenta.services.impl;

import com.ciprianursulean.licenta.entities.Drug;
import com.ciprianursulean.licenta.repositories.DrugRepository;
import com.ciprianursulean.licenta.services.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DrugServiceImpl implements DrugService {
    @Autowired
    DrugRepository drugRepository;

    @Override
    public Integer createDrug(String name, String company, String diseaseType, Date dateOfUsage, float price, float quantity) {
        return drugRepository.createDrug(name, company, diseaseType, dateOfUsage, price, quantity);
    }

    @Override
    public Drug findById(Integer drugId) {
        return  drugRepository.findById(drugId);
    }

    @Override
    public List<String> getDistinctDrugList() {
        return drugRepository.getDistinctDrugList();
    }

    @Override
    public List<Double> getDrugQuantities(String drugName) {
        return drugRepository.getDrugQuantities(drugName);
    }

    @Override
    public List<Date> getDrugUsageDates(String drugName) {
        return drugRepository.getDrugUsageDates(drugName);
    }

    @Override
    public List<String> getMostUsedDrugsNames() {
        return drugRepository.getMostUsedDrugsNames();
    }

    @Override
    public List<Double> getMostUsedDrugsQuantities() {
        return drugRepository.getMostUsedDrugsQuantities();
    }

    @Override
    public List<String> getBiggestPriceDrugName() {
        return drugRepository.getBiggestPriceDrugName();
    }

    @Override
    public List<Double> getBiggestPriceDrug() {
        return drugRepository.getBiggestPriceDrug();
    }

    @Override
    public List<String> getCompanyNames() {
        return drugRepository.getCompanyNames();
    }

    @Override
    public List<Double> getCompanyQuantities() {
        return drugRepository.getCompanyQuantities();
    }
}
