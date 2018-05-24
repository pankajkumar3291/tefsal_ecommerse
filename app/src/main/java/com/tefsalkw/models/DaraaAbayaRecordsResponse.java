package com.tefsalkw.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 19-03-2018.
 */

public class DaraaAbayaRecordsResponse implements Serializable {


    public ArrayList<ProductRecord> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductRecord> products) {
        this.products = products;
    }

    private ArrayList<ProductRecord> products;

    private List<DaraAbayaCategoriesModel> categories;

    public List<DaraAbayaCategoriesModel> getCategories ()
    {
        return categories;
    }

    public void setCategories (List<DaraAbayaCategoriesModel> categories)
    {
        this.categories = categories;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [categories = "+categories+"]";
    }

}
