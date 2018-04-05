package com.tefsalkw.models;

/**
 * Created by Rituparna Khadka on 11/25/2017.
 */

public class FilterCountryModel
{
    private String is_popular;

    private String country_id;

    private String country_image;

    private String country_name;

    public String getIs_popular ()
    {
        return is_popular;
    }

    public void setIs_popular (String is_popular)
    {
        this.is_popular = is_popular;
    }

    public String getCountry_id ()
    {
        return country_id;
    }

    public void setCountry_id (String country_id)
    {
        this.country_id = country_id;
    }

    public String getCountry_image ()
    {
        return country_image;
    }

    public void setCountry_image (String country_image)
    {
        this.country_image = country_image;
    }

    public String getCountry_name ()
    {
        return country_name;
    }

    public void setCountry_name (String country_name)
    {
        this.country_name = country_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [is_popular = "+is_popular+", country_id = "+country_id+", country_image = "+country_image+", country_name = "+country_name+"]";
    }
}
