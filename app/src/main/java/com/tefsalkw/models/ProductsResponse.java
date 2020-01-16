package com.tefsalkw.models;

import java.util.List;

/**
 * Created by Rituparna Khadka on 11/14/2017.
 */

public class ProductsResponse
{
    private String message;

    private List<ProductRecord> record;

    private String status;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public List<ProductRecord> getRecord ()
    {
        return record;
    }

    public void setRecord (List<ProductRecord> record)
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

