package com.ciprianursulean.licenta.repositories.impl;

import com.ciprianursulean.licenta.repositories.PatientStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PatientStatsRepositoryImpl implements PatientStatsRepository {
    private static final String SQL_GET_DISTINCT_LOCATIONS = "select distinct(patient_county) from drug_data";
    private static final String SQL_GET_DISTINCT_HOSPITAL_SECTIONS = "select distinct(hospital_section) from drug_data";
    private static final String SQL_GET_DRUGS_QUANTITY_FROM_LOCATION = "select sum(quantity) from drug_data where patient_county = ?";
    private static final String SQL_GET_DRUGS_QUANTITY_FROM_HOSPITAL_SECTION = "select sum(quantity) from drug_data where hospital_section = ?";
    private static final String SQL_GET_DRUGS_MOST_USED_FROM_HOSPITAL_SECTION = "select name from (\n" +
                                                                                "\tselect name, sum(quantity) as total, hospital_section from drug_data group by name, hospital_section having hospital_section = ? order by total desc\n" +
                                                                                ") as foo;";
    private static final String SQL_GET_QUANTITY_MOST_USED_FROM_HOSPITAL_SECTION = "select total from (\n" +
                                                                                   "\tselect name, sum(quantity) as total, hospital_section from drug_data group by name, hospital_section having hospital_section = ? order by total desc\n" +
                                                                                  ") as foo;";

    private static final String SQL_GET_DRUGS_MOST_USED_BY_INTERVAL = "select name from (\n" +
                                                                      "\tselect name, sum(quantity) as total from (select * from drug_data where patient_date_of_birth between ? and ?) as foo group by name order by total desc\n" +
                                                                      ") as foo1;";
    private static final String SQL_GET_QUANTITIES_MOST_USED_BY_INTERVAL = "select total from (\n" +
                                                                           "\tselect name, sum(quantity) as total from (select * from drug_data where patient_date_of_birth between ? and ?) as foo group by name order by total desc\n" +
                                                                           ") as foo1;";

    private static final String SQL_GET_DISEASE_TYPE_BY_INTERVAL = "select distinct(disease_type) from drug_data where patient_date_of_birth  between ? and ?;";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<String> getDistinctLocations() {
        return jdbcTemplate.queryForList(SQL_GET_DISTINCT_LOCATIONS, String.class);
    }

    @Override
    public List<String> getDistinctHospitalSections() {
        return jdbcTemplate.queryForList(SQL_GET_DISTINCT_HOSPITAL_SECTIONS, String.class);
    }

    @Override
    public List<Float> getQuantityByLocation(String location) {
        return jdbcTemplate.queryForList(SQL_GET_DRUGS_QUANTITY_FROM_LOCATION, new Object[]{location}, Float.class);
    }

    @Override
    public List<Float> getQuantityByHospitalSection(String hospitalSection) {
        return jdbcTemplate.queryForList(SQL_GET_DRUGS_QUANTITY_FROM_HOSPITAL_SECTION, new Object[]{hospitalSection}, Float.class);
    }

    @Override
    public List<String> getMostUsedDrugsByHospitalSection(String hospitalSection) {
        return jdbcTemplate.queryForList(SQL_GET_DRUGS_MOST_USED_FROM_HOSPITAL_SECTION, new Object[]{hospitalSection}, String.class);
    }

    @Override
    public List<Float> getMostUsedDrugsQuantitiesByHospitalSection(String hospitalSection) {
        return jdbcTemplate.queryForList(SQL_GET_QUANTITY_MOST_USED_FROM_HOSPITAL_SECTION, new Object[]{hospitalSection}, Float.class);
    }

    @Override
    public List<String> getMostUsedDrugsByAgeSegment(String startYear, String endYear) {
        return jdbcTemplate.queryForList(SQL_GET_DRUGS_MOST_USED_BY_INTERVAL, new Object[]{startYear, endYear}, String.class);
    }

    @Override
    public List<Float> getMostUsedQuantitiesByAgeSegment(String startYear, String endYear) {
        return jdbcTemplate.queryForList(SQL_GET_QUANTITIES_MOST_USED_BY_INTERVAL, new Object[]{startYear, endYear}, Float.class);
    }

    @Override
    public List<String> getDiseaseTypesByAgeSegment(String startYear, String endYear) {
        return jdbcTemplate.queryForList(SQL_GET_DISEASE_TYPE_BY_INTERVAL, new Object[]{startYear, endYear}, String.class);
    }

    public List<Pair<Float, String>> getQuantityByLocationAll() {
        List<Pair<Float, String>> result = new ArrayList<>();
        List<String> locations = getDistinctLocations();
        for (String location : locations) {
            result.add(new Pair<Float, String>(getQuantityByLocation(location).get(0), location));
        }
        return result;
    }
}
