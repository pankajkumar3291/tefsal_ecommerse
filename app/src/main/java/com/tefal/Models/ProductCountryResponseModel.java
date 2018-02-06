package com.tefal.Models;

import java.util.ArrayList;

/**
 * Created by Rituparna Khadka on 12/27/2017.
 */

public class ProductCountryResponseModel
{
    private String message;

    private ArrayList<ProductCountryRecordModel> record;

    private String status;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<ProductCountryRecordModel> getRecord ()
    {
        return record;
    }

    public void setRecord (ArrayList<ProductCountryRecordModel> record)
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
