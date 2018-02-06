package com.tefal.Models;

/**
 * Created by new on 9/26/2017.
 */

public class CategorisResponseModel {

    String response;

    CategoriesModel record;

    public String getResponse() {
        return response;
    }

    public CategoriesModel getData() {
        return record;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setData(CategoriesModel record) {
        this.record = record;
    }
}
