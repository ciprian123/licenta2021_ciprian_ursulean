package com.ciprianursulean.licenta.repositories.impl;

import com.ciprianursulean.licenta.entities.Drug;
import com.ciprianursulean.licenta.repositories.DrugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;
import java.util.List;


@Repository
public class DrugRepositoryImpl implements DrugRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String SQL_CREATE_DRUG = "INSERT INTO DRUG (name, company, disease_type, date_of_usage, price, quantity) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_GET_DISTINCT_DRUG_NAMES = "SELECT DISTINCT(name) FROM DRUG ORDER BY name";
    private static final String SQL_GET_DRUG_QUANTITIES_FROM_NAME = "select quantity from (\n" +
                                                                    "select name, sum(quantity) as \"quantity\", date_of_usage\n" +
                                                                    "from drug\n" +
                                                                    "group by date_of_usage, name \n" +
                                                                    "having LOWER(name) = ? " +
                                                                    "order by date_of_usage asc) as foo;";
    private static final String SQL_GET_DRUG_USAGE_DATETIME_FROM_NAME = "SELECT DISTINCT(date_of_usage) FROM DRUG WHERE LOWER(name) = ?";
    private static final String SQL_GET_DRUG_BY_ID = "SELECT * FROM DRUG where drug_id = ?";
    private static final String SQL_GET_MOST_USED_DRUGS_NAMES = "select name from \n" +
                                                                "(select name, sum(quantity) from drug group by name order by sum(quantity) desc limit 25) as foo;";
    private static final String SQL_GET_MOST_USED_DRUGS_QUANTITIES = "select quantity from \n" +
                                                                     "(select name, sum(quantity) as \"quantity\" from drug group by name order by sum(quantity) desc limit 25) as foo;";

    private static final String SQL_GET_BIGGEST_PRICE_DRUG_NAMES = "select name from (select name, avg(quantity) from drug group by name order by avg(quantity) desc) as foo limit 25;";
    private static final String SQL_GET_BIGGEST_PRICE_DRUG = "select average from (select name, avg(quantity) as average from drug group by name order by avg(quantity) desc) as foo limit 25;";

    private static final String SQL_GET_COMPANY_NAMES = "select company from (select company, sum(quantity) as quantity from drug\n" +
                                                        "group by company order by sum(quantity) desc) as foo limit 25;";

    private static final String SQL_GET_COMPANY_DRUG_QUANTITIES = "select quantity from (select company, sum(quantity) as quantity from drug\n" +
                                                                  "group by company order by sum(quantity) desc) as foo limit 25;";


    @Override
    public Integer createDrug(String name, String company, String diseaseType, Date dateOfUsage, float price, float quantity) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_DRUG, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, company);
                preparedStatement.setString(3, diseaseType);
                preparedStatement.setDate(4, (java.sql.Date) dateOfUsage);
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
    public List<Date> getDrugUsageDates(String drugName) {
        return jdbcTemplate.queryForList(SQL_GET_DRUG_USAGE_DATETIME_FROM_NAME, new Object[]{drugName.toLowerCase()}, Date.class);
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

    private final RowMapper<Drug> drugRowMapper = (rs, rowNum) -> new Drug(rs.getInt("drug_id"),
            rs.getString("name"),
            rs.getString("company"),
            rs.getString("diasese_type"),
            rs.getDate("date_of_usage"),
            rs.getFloat("price"),
            rs.getFloat("quantity"));
}
