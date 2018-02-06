package com.tefal.Models;

/**
 * Created by Rituparna Khadka on 1/8/2018.
 */

public class SignUpErrorMessageRecordModel
{
    private String first_name;

    private String email;

    private String password_confirmation;

    private String password;

    private String mobile;

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

    public String getPassword_confirmation ()
    {
        return password_confirmation;
    }

    public void setPassword_confirmation (String password_confirmation)
    {
        this.password_confirmation = password_confirmation;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public String getMobile ()
    {
        return mobile;
    }

    public void setMobile (String mobile)
    {
        this.mobile = mobile;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [first_name = "+first_name+", email = "+email+", password_confirmation = "+password_confirmation+", password = "+password+", mobile = "+mobile+"]";
    }
}
