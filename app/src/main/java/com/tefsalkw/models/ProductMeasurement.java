package com.tefsalkw.models;

import java.io.Serializable;

/**
 * Created by Rituparna Khadka on 12/20/2017.
 */

public class ProductMeasurement implements Serializable
{
    private String front_height;

    private String hip;

    private String wrist;

    private String chest;

    private String waist;

    private String product_size_id;

    private String arm;

    private String shoulders;

    private String size_m;

    public String getFront_height ()
    {
        return front_height;
    }

    public void setFront_height (String front_height)
    {
        this.front_height = front_height;
    }

    public String getHip ()
    {
        return hip;
    }

    public void setHip (String hip)
    {
        this.hip = hip;
    }

    public String getWrist ()
    {
        return wrist;
    }

    public void setWrist (String wrist)
    {
        this.wrist = wrist;
    }

    public String getChest ()
    {
        return chest;
    }

    public void setChest (String chest)
    {
        this.chest = chest;
    }

    public String getWaist ()
    {
        return waist;
    }

    public void setWaist (String waist)
    {
        this.waist = waist;
    }

    public String getProduct_size_id ()
    {
        return product_size_id;
    }

    public void setProduct_size_id (String product_size_id)
    {
        this.product_size_id = product_size_id;
    }

    public String getArm ()
    {
        return arm;
    }

    public void setArm (String arm)
    {
        this.arm = arm;
    }

    public String getShoulders ()
    {
        return shoulders;
    }

    public void setShoulders (String shoulders)
    {
        this.shoulders = shoulders;
    }

    public String getSize_m ()
    {
        return size_m;
    }

    public void setSize_m (String size_m)
    {
        this.size_m = size_m;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [front_height = "+front_height+", hip = "+hip+", wrist = "+wrist+", chest = "+chest+", waist = "+waist+", product_size_id = "+product_size_id+", arm = "+arm+", shoulders = "+shoulders+", size_m = "+size_m+"]";
    }
}
