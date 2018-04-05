package com.tefsalkw.models;

import java.util.List;

/**
 * Created by Hp on 01-11-2017.
 */

public class GetCartResponse {
    private String message;

    private List<GetCartRecord> record;

    private String status;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public List<GetCartRecord> getRecord ()
    {
        return record;
    }

    public void setRecord (List<GetCartRecord> record)
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

