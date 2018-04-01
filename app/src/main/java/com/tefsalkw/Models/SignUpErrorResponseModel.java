package com.tefsalkw.Models;

/**
 * Created by Rituparna Khadka on 1/8/2018.
 */

public class SignUpErrorResponseModel
{
    private SignUpErrorMessageRecordModel message;

    private String status;

    public SignUpErrorMessageRecordModel getMessage ()
    {
        return message;
    }

    public void setMessage (SignUpErrorMessageRecordModel message)
    {
        this.message = message;
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
        return "ClassPojo [message = "+message+", status = "+status+"]";
    }
}
