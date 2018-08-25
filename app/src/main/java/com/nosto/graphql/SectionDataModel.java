package com.nosto.graphql;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class SectionDataModel {

    private String headerTitle;
    private List<SingleItemModel> allItemsInSection;

    public SectionDataModel(String headerTitle) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = new ArrayList<>();
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public List<SingleItemModel> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void addProduct(SingleItemModel recommendation) {
        this.allItemsInSection.add(recommendation);
    }
}