package com.ciprianursulean.licenta.repositories.impl;

import com.ciprianursulean.licenta.entities.Drug;
import com.ciprianursulean.licenta.repositories.DrugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@Repository
public class DrugRepositoryImpl implements DrugRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String SQL_CREATE_DRUG = "INSERT INTO DRUG_DATA (name, company, disease_type, date_of_usage, price, quantity) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_GET_DISTINCT_DRUG_NAMES = "SELECT DISTINCT(name) FROM DRUG_DATA ORDER BY name";
    private static final String SQL_GET_DRUG_QUANTITIES_FROM_NAME = "select quantity from (\n" +
                                                                    "select name, sum(quantity) as \"quantity\", date_of_usage\n" +
                                                                    "from DRUG_DATA\n" +
                                                                    "group by date_of_usage, name \n" +
                                                                    "having LOWER(name) = ? " +
                                                                    "order by date_of_usage asc) as foo;";
    private static final String SQL_GET_DRUG_USAGE_DATETIME_AS_TIMESTAMP_FROM_NAME = "SELECT DISTINCT(date_of_usage_timestamp) FROM DRUG_DATA WHERE LOWER(name) = ?";
    private static final String SQL_GET_DRUG_USAGE_DATETIME_AS_DATE_FROM_NAME = "SELECT DISTINCT(date_of_usage) FROM DRUG_DATA WHERE LOWER(name) = ?";
    private static final String SQL_GET_DRUG_BY_ID = "SELECT * FROM DRUG_DATA where drug_id = ?";
    private static final String SQL_GET_MOST_USED_DRUGS_NAMES = "select name from \n" +
                                                                "(select name, sum(quantity) from DRUG_DATA group by name order by sum(quantity) desc limit 25) as foo;";
    private static final String SQL_GET_MOST_USED_DRUGS_QUANTITIES = "select quantity from \n" +
                                                                     "(select name, sum(quantity) as \"quantity\" from DRUG_DATA group by name order by sum(quantity) desc limit 25) as foo;";

    private static final String SQL_GET_BIGGEST_PRICE_DRUG_NAMES = "select name from (select name, avg(quantity) from DRUG_DATA group by name order by avg(quantity) desc) as foo limit 25;";
    private static final String SQL_GET_BIGGEST_PRICE_DRUG = "select average from (select name, avg(quantity) as average from DRUG_DATA group by name order by avg(quantity) desc) as foo limit 25;";

    private static final String SQL_GET_COMPANY_NAMES = "select company from (select company, sum(quantity) as quantity from DRUG_DATA\n" +
                                                        "group by company order by sum(quantity) desc) as foo limit 25;";

    private static final String SQL_GET_COMPANY_DRUG_QUANTITIES = "select quantity from (select company, sum(quantity) as quantity from DRUG_DATA\n" +
                                                                  "group by company order by sum(quantity) desc) as foo limit 25;";


    @Override
    public Integer createDrug(String name, String company, String diseaseType, float dateOfUsage, float price, float quantity) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_DRUG, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, company);
                preparedStatement.setString(3, diseaseType);
                preparedStatement.setFloat(4, dateOfUsage);
                preparedStatement.setFloat(5, price);
                preparedStatement.setFloat(6, quantity);
                return preparedStatement;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("drug_id");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @Override
    public Drug findById(Integer drugId) {
        return jdbcTemplate.queryForObject(SQL_GET_DRUG_BY_ID, new Object[]{drugId}, Drug.class);
    }

    @Override
    public List<String> getDistinctDrugList() {
        return jdbcTemplate.queryForList(SQL_GET_DISTINCT_DRUG_NAMES, String.class);
    }

    @Override
    public List<Double> getDrugQuantities(String drugName) {
        return jdbcTemplate.queryForList(SQL_GET_DRUG_QUANTITIES_FROM_NAME, new Object[]{drugName.toLowerCase()}, Double.class);
    }

    @Override
    public List<Float> getDrugUsageDatesAsTimestamp(String drugName) {
        return jdbcTemplate.queryForList(SQL_GET_DRUG_USAGE_DATETIME_AS_TIMESTAMP_FROM_NAME, new Object[]{drugName.toLowerCase()}, Float.class);
    }

    @Override
    public List<Date> getDrugUsageDatesAsDate(String drugName) {
        return jdbcTemplate.queryForList(SQL_GET_DRUG_USAGE_DATETIME_AS_DATE_FROM_NAME, new Object[]{drugName.toLowerCase()}, Date.class);
    }

    @Override
    public List<String> getMostUsedDrugsNames() {
        return jdbcTemplate.queryForList(SQL_GET_MOST_USED_DRUGS_NAMES, String.class);
    }

    @Override
    public List<Double> getMostUsedDrugsQuantities() {
        return jdbcTemplate.queryForList(SQL_GET_MOST_USED_DRUGS_QUANTITIES, Double.class);
    }

    @Override
    public List<String> getBiggestPriceDrugName() {
        return jdbcTemplate.queryForList(SQL_GET_BIGGEST_PRICE_DRUG_NAMES, String.class);
    }

    @Override
    public List<Double> getBiggestPriceDrug() {
        return jdbcTemplate.queryForList(SQL_GET_BIGGEST_PRICE_DRUG, Double.class);
    }

    @Override
    public List<String> getCompanyNames() {
        return jdbcTemplate.queryForList(SQL_GET_COMPANY_NAMES, String.class);
    }

    @Override
    public List<Double> getCompanyQuantities() {
        return jdbcTemplate.queryForList(SQL_GET_COMPANY_DRUG_QUANTITIES, Double.class);
    }

    @Override
    public List<String> getDrugTrainingData(String drugName) throws IOException {
        List<Float> drugDatesList = getDrugUsageDatesAsTimestamp(drugName);
        List<Double> drugQuantitiesList = getDrugQuantities(drugName);
        final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("TRAMADOLUM.csv"));
        List<String> result = new ArrayList<>();
        for (int i = 0; i < drugDatesList.size(); ++i) {
            result.add(drugQuantitiesList.get(i) + "," + drugDatesList.get(i) + ",");
            bufferedWriter.append(drugQuantitiesList.get(i) + "," + drugDatesList.get(i) + "\n");
        }
        return result;
    }

    private final RowMapper<Drug> drugRowMapper = (rs, rowNum) -> new Drug(rs.getInt("drug_id"),
            rs.getString("name"),
            rs.getString("company"),
            rs.getString("diasese_type"),
            rs.getFloat("date_of_usage"),
            rs.getFloat("price"),
            rs.getFloat("quantity"));
}
