package com.tefal.Models;

/**
 * Created by new on 9/26/2017.
 */

public class CountriesResponseModel {

    String response;

    CountriesModel record;

    public String getResponse() {
        return response;
    }

    public CountriesModel getData() {
        return record;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setData(CountriesModel record) {
        this.record = record;
    }
}
