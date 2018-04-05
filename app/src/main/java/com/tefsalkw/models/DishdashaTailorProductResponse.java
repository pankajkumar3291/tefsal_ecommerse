package com.tefsalkw.models;

import java.util.ArrayList;

/**
 * Created by Rituparna Khadka on 1/11/2018.
 */

public class DishdashaTailorProductResponse
{
    private String message;

    private ArrayList<DishdashaTailorProductRecord> record;

    private String status;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<DishdashaTailorProductRecord> getRecord ()
    {
        return record;
    }

    public void setRecord (ArrayList<DishdashaTailorProductRecord> record)
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
