package com.nosto.graphql.adapters;

import java.util.List;

/**
 * Simple holder class to contain all the elements needed to display a single recommendation element
 * Each recommendation element normally consists of a recommendation title and an collection of
 * products.
 *
 * @author mridang
 */
@SuppressWarnings("WeakerAccess")
public class SectionDataModel {

    private String title;
    private List<SingleItemModel> products;

    private SectionDataModel(String title, List<SingleItemModel> products) {
        this.title = title;
        this.products = products;
    }

    public String getTitle() {
        return title;
    }

    public List<SingleItemModel> getProducts() {
        return products;
    }

    public static class Builder {

        private String title;
        private List<SingleItemModel> products;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setProducts(List<SingleItemModel> products) {
            this.products = products;
            return this;
        }

        public SectionDataModel build() {
            return new SectionDataModel(title, products);
        }
    }
}