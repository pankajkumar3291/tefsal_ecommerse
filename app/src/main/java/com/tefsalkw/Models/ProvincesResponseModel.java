package com.tefsalkw.Models;

/**
 * Created by new on 9/26/2017.
 */

public class ProvincesResponseModel {

    String response;

    ProvincesModel record;

    public String getResponse() {
        return response;
    }

    public ProvincesModel getData() {
        return record;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setData(ProvincesModel record) {
        this.record = record;
    }
}
