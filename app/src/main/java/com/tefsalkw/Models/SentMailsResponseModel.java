package com.tefsalkw.Models;

import java.util.List;

/**
 * Created by Hp on 09-10-2017.
 */

public class SentMailsResponseModel {
    private String message;

    private List<SentMailModel> record;

    private String status;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public List<SentMailModel> getRecord ()
    {
        return record;
    }

    public void setRecord (List<SentMailModel> record)
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

