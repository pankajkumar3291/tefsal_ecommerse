package com.tefal.Models;

import java.util.List;

/**
 * Created by Hp on 16-10-2017.
 */

public class TextileProductResponse {
    private String message;

    private List<TextileProductModel> record;

    private String status;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public List<TextileProductModel> getRecord ()
    {
        return record;
    }

    public void setRecord (List<TextileProductModel> record)
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

