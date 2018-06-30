package com.tefsalkw.models;

import java.io.Serializable;
import java.util.List;

public class OrderDetailsResponse implements  Serializable  {

    private String message;

    private OrderDetails record;

    private int status;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public OrderDetails getRecord ()
    {
        return record;
    }

    public void setRecord (OrderDetails  record)
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
