package com.tefal.Models;

/**
 * Created by Rituparna Khadka on 1/4/2018.
 */

public class PhoneCountryCodeRecord
{
    private String flag;

    private String phonecode;

    private String nicename;

    public String getFlag ()
    {
        return flag;
    }

    public void setFlag (String flag)
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

    @Override
    public String toString()
    {
        return "ClassPojo [flag = "+flag+", phonecode = "+phonecode+", nicename = "+nicename+"]";
    }
}
