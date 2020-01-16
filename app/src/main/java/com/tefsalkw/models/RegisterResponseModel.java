package com.tefsalkw.models;

/**
 * Created by new on 9/26/2017.
 */

public class RegisterResponseModel {

    private String message;

    private AuthTokenModel record;

    private String status;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public AuthTokenModel getRecord ()
    {
        return record;
    }

    public void setRecord (AuthTokenModel record)
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
