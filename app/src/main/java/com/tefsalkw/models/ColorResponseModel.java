package com.tefsalkw.models;

import java.util.ArrayList;

/**
 * Created by Rituparna Khadka on 12/26/2017.
 */

public class ColorResponseModel
{
    private String message;

    private ArrayList<ColorsRecordModel> record;

    private String status;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<ColorsRecordModel> getRecord ()
    {
        return record;
    }

    public void setRecord (ArrayList<ColorsRecordModel> record)
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
