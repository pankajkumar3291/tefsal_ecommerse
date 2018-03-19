package com.tefal.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Dell on 19-03-2018.
 */

public class DaraAbayaProductListResponse {

    private String message;

    @SerializedName("record")
    @Expose
    private DaraaAbayaRecordsResponse record;

    private String status;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public DaraaAbayaRecordsResponse getRecord ()
    {
        return record;
    }

    public void setRecord (DaraaAbayaRecordsResponse record)
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
