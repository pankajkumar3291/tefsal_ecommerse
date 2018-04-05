package com.tefsalkw.models;

/**
 * Created by new on 9/26/2017.
 */

public class AreaResponseModel {

    String response;

    AreaModel record;

    public String getResponse() {
        return response;
    }

    public AreaModel getData() {
        return record;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setData(AreaModel record) {
        this.record = record;
    }
}
