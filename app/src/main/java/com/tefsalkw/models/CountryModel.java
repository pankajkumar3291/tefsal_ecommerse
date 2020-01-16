package com.tefsalkw.models;

import java.io.Serializable;

/**
 * Created by Dell on 09-02-2018.
 */

public class CountryModel implements Serializable {

    private int flag;

    private String phonecode;

    private String nicename;



    public int getFlag ()
    {
        return flag;
    }

    public void setFlag (int flag)
    {
        this.flag = flag;
    }

    public String getPhonecode ()
    {
        return phonecode;
    }

    public void setPhonecode (String phonecode)
    {
        this.phonecode = phonecode;
    }

    public String getNicename ()
    {
        return nicename;
    }

    public void setNicename (String nicename)
    {
        this.nicename = nicename;
    }
}
