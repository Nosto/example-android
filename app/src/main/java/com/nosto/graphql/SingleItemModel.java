package com.nosto.graphql;

import com.nosto.graphql.utils.MoneyUtils;

public class SingleItemModel {

    private String name;
    private String url;
    private Double price;
    private String currency;
    private String image;
    private String description;

    public SingleItemModel(String name, String url, Double price, String currency, String image) {
        this.name = name;
        this.url = url;
        this.price = price;
        this.currency = currency;
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return MoneyUtils.getFormattedCurrencyString(currency, price);
    }

    public String getImage() {
        return image;
    }
}