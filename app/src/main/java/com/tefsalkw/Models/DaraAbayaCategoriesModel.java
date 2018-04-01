package com.tefsalkw.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 19-03-2018.
 */

public class DaraAbayaCategoriesModel {

    private String category;

    public String getSub_category() {
        return sub_category;
    }

    public void setSub_category(String sub_category) {
        this.sub_category = sub_category;
    }

    private String sub_category;

    private ArrayList<ProductRecord>  products;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<ProductRecord> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductRecord>  products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "ClassPojo [category = " + category + ", products = " + products + "]";
    }
}
