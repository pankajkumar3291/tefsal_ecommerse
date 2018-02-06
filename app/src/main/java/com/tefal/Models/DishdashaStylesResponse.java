package com.tefal.Models;

import java.util.List;

/**
 * Created by Hp on 30-10-2017.
 */

public class DishdashaStylesResponse {
    private String message;

    private  List<DishdashaStylesRecord>  record;

    private String status;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public List<DishdashaStylesRecord> getRecord ()
    {
        return record;
    }

    public void setRecord ( List<DishdashaStylesRecord>  record)
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

