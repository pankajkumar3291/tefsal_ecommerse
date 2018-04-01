package com.tefsalkw.Models;

import java.io.Serializable;

/**
 * Created by Hp on 09-10-2017.
 */

public class SentMailModel implements Serializable{
    private String message;

    private String first_name;

    private String email;

    private String subject;

    private String last_name;

    private String created_at;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getFirst_name ()
    {
        return first_name;
    }

    public void setFirst_name (String first_name)
    {
        this.first_name = first_name;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getSubject ()
    {
        return subject;
    }

    public void setSubject (String subject)
    {
        this.subject = subject;
    }

    public String getLast_name ()
    {
        return last_name;
    }

    public void setLast_name (String last_name)
    {
        this.last_name = last_name;
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
        return "ClassPojo [message = "+message+", first_name = "+first_name+", email = "+email+", subject = "+subject+", last_name = "+last_name+", created_at = "+created_at+"]";
    }
}

