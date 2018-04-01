package com.tefsalkw.Models;

/**
 * Created by Rituparna Khadka on 11/25/2017.
 */

public class FilterBrandModel
{
    private String brand_image;

    private String is_popular;

    private String brand_id;

    private String brand_name;

    public String getBrand_image ()
    {
        return brand_image;
    }

    public void setBrand_image (String brand_image)
    {
        this.brand_image = brand_image;
    }

    public String getIs_popular ()
    {
        return is_popular;
    }

    public void setIs_popular (String is_popular)
    {
        this.is_popular = is_popular;
    }

    public String getBrand_id ()
    {
        return brand_id;
    }

    public void setBrand_id (String brand_id)
    {
        this.brand_id = brand_id;
    }

    public String getBrand_name ()
    {
        return brand_name;
    }

    public void setBrand_name (String brand_name)
    {
        this.brand_name = brand_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [brand_image = "+brand_image+", is_popular = "+is_popular+", brand_id = "+brand_id+", brand_name = "+brand_name+"]";
    }
}
