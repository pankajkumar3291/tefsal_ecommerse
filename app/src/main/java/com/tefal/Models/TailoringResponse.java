package com.tefal.Models;

import java.util.ArrayList;

/**
 * Created by Rituparna Khadka on 1/31/2018.
 */

public class TailoringResponse
{
    private String message;

    private ArrayList<TailoringRecord> record;

    private String status;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<TailoringRecord> getRecord ()
    {
        return record;
    }

    public void setRecord (ArrayList<TailoringRecord> record)
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
