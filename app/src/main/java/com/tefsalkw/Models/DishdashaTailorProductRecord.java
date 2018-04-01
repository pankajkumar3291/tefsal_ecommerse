package com.tefsalkw.Models;

/**
 * Created by Rituparna Khadka on 1/11/2018.
 */

public class DishdashaTailorProductRecord
{
    private String dishdasha_tailor_product_name;

    private String dishdasha_tailor_product_price;

    private String tefsal_product_id;

    public String getDishdasha_tailor_product_name ()
    {
        return dishdasha_tailor_product_name;
    }

    public void setDishdasha_tailor_product_name (String dishdasha_tailor_product_name)
    {
        this.dishdasha_tailor_product_name = dishdasha_tailor_product_name;
    }

    public String getDishdasha_tailor_product_price ()
    {
        return dishdasha_tailor_product_price;
    }

    public void setDishdasha_tailor_product_price (String dishdasha_tailor_product_price)
    {
        this.dishdasha_tailor_product_price = dishdasha_tailor_product_price;
    }

    public String getTefsal_product_id ()
    {
        return tefsal_product_id;
    }

    public void setTefsal_product_id (String tefsal_product_id)
    {
        this.tefsal_product_id = tefsal_product_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [dishdasha_tailor_product_name = "+dishdasha_tailor_product_name+", dishdasha_tailor_product_price = "+dishdasha_tailor_product_price+", tefsal_product_id = "+tefsal_product_id+"]";
    }
}
