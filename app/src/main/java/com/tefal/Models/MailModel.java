package com.tefal.Models;

import java.io.Serializable;

/**
 * Created by new on 9/26/2017.
 */

public class MailModel implements Serializable {

    private String message;

    private String subject;

    private String created_at;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getSubject ()
    {
        return subject;
    }

    public void setSubject (String subject)
    {
        this.subject = subject;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", subject = "+subject+", created_at = "+created_at+"]";
    }
}

