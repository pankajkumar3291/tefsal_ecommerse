package com.tefsalkw.models;

import java.io.Serializable;
import java.util.List;

public class OrderDetailsResponse   {

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
