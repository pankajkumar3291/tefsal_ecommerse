package com.tefsalkw.Models;

/**
 * Created by AC 101 on 25-10-2017.
 */

public class AddCartResponse
{
    private AddCartRecord errors;

    private String status;

    public AddCartRecord getErrors ()
    {
        return errors;
    }

    public void AddCartRecord (AddCartRecord errors)
    {
        this.errors = errors;
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
        return "ClassPojo [errors = "+errors+", status = "+status+"]";
    }
}
