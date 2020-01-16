package com.tefsalkw.models;

import java.util.List;

/**
 * Created by new on 9/26/2017.
 */

public class TailorStoresResponseModel {


    private String message;

    private List<TailorStoresModel> record;

    private String status;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public List<TailorStoresModel> getRecord ()
    {
        return record;
    }

    public void setRecord (List<TailorStoresModel> record)
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
