package com.tefal.Models;

/**
 * Created by new on 9/26/2017.
 */

public class UserResponseModel {

    String response;

    UsernModel record;

    public String getResponse() {
        return response;
    }

    public UsernModel getData() {
        return record;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setData(UsernModel record) {
        this.record = record;
    }
}
