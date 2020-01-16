package com.tefsalkw.models;

import java.util.List;

/**
 * Created by new on 9/26/2017.
 */

public class TextileStoresResponseModel {


    private String message;

    private List<TextileStoresModel> record;

    private String status;
    public String getStore_discount() {
        return store_discount;
    }
    public void setStore_discount(String store_discount) {
        this.store_discount = store_discount;
    }
    private String store_discount;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public List<TextileStoresModel> getRecord ()
    {
        return record;
    }

    public void setRecord (List<TextileStoresModel> record)
    {
        this.record = record;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", record = "+record+", status = "+status+"]";
    }
}
