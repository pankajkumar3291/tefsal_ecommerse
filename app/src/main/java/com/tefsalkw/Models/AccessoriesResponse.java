package com.tefsalkw.Models;

import java.util.List;

/**
 * Created by Hp on 16-10-2017.
 */

public class AccessoriesResponse {
    private String message;

    private List<AccessoriesModel> record;

    private String status;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public List<AccessoriesModel>  getRecord ()
    {
        return record;
    }

    public void setRecord (List<AccessoriesModel>  record)
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

