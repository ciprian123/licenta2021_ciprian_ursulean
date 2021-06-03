package com.ciprianursulean.licenta.entities;

import lombok.Data;

import java.util.Date;

@Data
public class Drug {
    private Integer drugId;

    private String name;

    private String company;

    private String diseaseType;

    private float dateOfUsage;

    private float price;

    private float quantity;

    public Drug(Integer drugId, String name, String company, String diseaseType, float dateOfUsage, float price, float quantity) {
        this.drugId = drugId;
        this.name = name;
        this.company = company;
        this.diseaseType = diseaseType;
        this.dateOfUsage = dateOfUsage;
        this.price = price;
        this.quantity = quantity;
    }
}
