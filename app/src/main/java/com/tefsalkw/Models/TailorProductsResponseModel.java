package com.tefsalkw.Models;

/**
 * Created by new on 9/26/2017.
 */

public class TailorProductsResponseModel {

    String response;

    TailorProductsModel record;

    public String getResponse() {
        return response;
    }

    public TailorProductsModel getData() {
        return record;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setData(TailorProductsModel record) {
        this.record = record;
    }
}
