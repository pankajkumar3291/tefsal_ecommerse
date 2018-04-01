package com.tefsalkw.Models;

/**
 * Created by Rituparna Khadka on 11/28/2017.
 */

public class SeasonResponseModel
{
    private String message;

    private String[] record;

    private String status;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String[] getRecord ()
    {
        return record;
    }

    public void setRecord (String[] record)
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
