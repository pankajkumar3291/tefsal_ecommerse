package com.tefsalkw.models;

import java.util.ArrayList;

/**
 * Created by Rituparna Khadka on 1/31/2018.
 */

public class TailoringResponse
{
    private String message;

    private ArrayList<TailoringRecord> records;

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
        return records;
    }

    public void setRecord (ArrayList<TailoringRecord> record)
    {
        this.records = record;
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
        return "ClassPojo [message = "+message+", record = "+records+", status = "+status+"]";
    }
}
