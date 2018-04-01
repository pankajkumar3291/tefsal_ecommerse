package com.tefsalkw.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by new on 9/26/2017.
 */

public class AuthTokenModel {

    private String user_id;

    private String access_token;

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getAccess_token ()
    {
        return access_token;
    }

    public void setAccess_token (String access_token)
    {
        this.access_token = access_token;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [user_id = "+user_id+", access_token = "+access_token+"]";
    }

}
