package com.nosto.graphql.adapters;

import com.nosto.graphql.utils.MoneyUtils;

public class SingleItemModel {

    private final String name;
    private final String url;
    private final Double price;
    private final String currency;
    private final String image;

    private SingleItemModel(String name, String url, Double price, String currency, String image) {
        this.name = name;
        this.url = url;
        this.price = price;
        this.currency = currency;
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return MoneyUtils.getFormattedCurrencyString(currency, price);
    }

    public String getImage() {
        return image;
    }

    public static class Builder {

        private String name;
        private String url;
        private Double price;
        private String currency;
        private String image;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setPrice(Double price) {
            this.price = price;
            return this;
        }

        public Builder setCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder setImage(String image) {
            this.image = image;
            return this;
        }


        public SingleItemModel build() {
            return new SingleItemModel(name, url, price, currency, image);
        }
    }
}