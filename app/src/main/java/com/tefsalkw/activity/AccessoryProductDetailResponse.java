package com.tefsalkw.activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tefsalkw.models.AccessoryDetailRecord;

import java.io.Serializable;

public class  AccessoryProductDetailResponse implements Serializable {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("record")
    @Expose
    private AccessoryDetailRecord record;

    @SerializedName("status")
    @Expose
    private String status;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public AccessoryDetailRecord getRecord ()
    {
        return record;
    }

    public void setRecord (AccessoryDetailRecord record)
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
