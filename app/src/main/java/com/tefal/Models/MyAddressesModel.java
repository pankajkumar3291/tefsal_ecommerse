package com.tefal.Models;

import java.util.List;

/**
 * Created by CREST-Sys-01 on 02/10/17.
 */

public class MyAddressesModel {
    private String message;

    private List<AddressRecord> record;

    private String status;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public List<AddressRecord> getRecord ()
    {
        return record;
    }

    public void setRecord (List<AddressRecord> record)
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
