package com.tefsalkw.Models;

import java.util.ArrayList;

/**
 * Created by Rituparna Khadka on 1/4/2018.
 */

public class PhoneCountryCodeResponse
{
    private String message;

    private ArrayList<PhoneCountryCodeRecord> record;

    private String status;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<PhoneCountryCodeRecord> getRecord ()
    {
        return record;
    }

    public void setRecord (ArrayList<PhoneCountryCodeRecord> record)
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
