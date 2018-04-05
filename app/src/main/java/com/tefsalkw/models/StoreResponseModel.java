package com.tefsalkw.models;

/**
 * Created by new on 9/26/2017.
 */

public class StoreResponseModel {

    String response;

    StoreModel record;

    public String getResponse() {
        return response;
    }

    public StoreModel getData() {
        return record;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setData(StoreModel record) {
        this.record = record;
    }
}
