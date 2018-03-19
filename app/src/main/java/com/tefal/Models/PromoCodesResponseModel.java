package com.tefal.Models;

/**
 * Created by Dell on 03/16/2018.
 */

public class PromoCodesResponseModel {
    private String message;

    private PromoCodeRecordsModel record;

    private int status;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public PromoCodeRecordsModel getRecord ()
    {
        return record;
    }

    public void setRecord (PromoCodeRecordsModel record)
    {
        this.record = record;
    }

    public int getStatus ()
    {
        return status;
    }

    public void setStatus (int status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", record = "+record+", status = "+status+"]";
    }
}
