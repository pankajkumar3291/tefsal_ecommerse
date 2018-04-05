package com.tefsalkw.models;

import java.util.List;

/**
 * Created by Rituparna Khadka on 11/13/2017.
 */

public class MyOrderResponse
{
    private String message;

    private List<OrderRecord> record;

    private String status;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public List<OrderRecord> getRecord ()
    {
        return record;
    }

    public void setRecord (List<OrderRecord>  record)
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

