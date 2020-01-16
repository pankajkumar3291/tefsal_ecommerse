package com.tefsalkw.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Dell on 03/15/2018.
 */

public class DaraAbayaDetailRecord implements Serializable {



    private String measurements;

    private String product_generated_id;

    private String tag;

    private String max_delivery_days;

    private String product_name;

    private String product_name_arabic;

    private String product_desc;
    private String product_desc_arabic;

    private String tefsal_product_id;

    private String published;

    private String min_delivery_days;

    private String store_name;

    public String getProduct_name_arabic() {
        return product_name_arabic;
    }

    public void setProduct_name_arabic(String product_name_arabic) {
        this.product_name_arabic = product_name_arabic;
    }

    public String getProduct_desc_arabic() {
        return product_desc_arabic;
    }

    public void setProduct_desc_arabic(String product_desc_arabic) {
        this.product_desc_arabic = product_desc_arabic;
    }

    private String brand_name;

    public String getBrand_name_arabic() {
        return brand_name_arabic;
    }

    public void setBrand_name_arabic(String brand_name_arabic) {
        this.brand_name_arabic = brand_name_arabic;
    }

    private String brand_name_arabic;

    private List<Colors> colors;
    public List<Colors> getColors ()
    {
        return colors;
    }

    public void setColors (List<Colors> colors)
    {
        this.colors = colors;
    }




    public String getMeasurements ()
    {
        return measurements;
    }

    public void setMeasurements (String measurements)
    {
        this.measurements = measurements;
    }

    public String getProduct_generated_id ()
    {
        return product_generated_id;
    }

    public void setProduct_generated_id (String product_generated_id)
    {
        this.product_generated_id = product_generated_id;
    }

    public String getTag ()
    {
        return tag;
    }

    public void setTag (String tag)
    {
        this.tag = tag;
    }

    public String getMax_delivery_days ()
    {
        return max_delivery_days;
    }

    public void setMax_delivery_days (String max_delivery_days)
    {
        this.max_delivery_days = max_delivery_days;
    }

    public String getProduct_name ()
    {
        return product_name;
    }

    public void setProduct_name (String product_name)
    {
        this.product_name = product_name;
    }

    public String getProduct_desc ()
    {
        return product_desc;
    }

    public void setProduct_desc (String product_desc)
    {
        this.product_desc = product_desc;
    }

    public String getTefsal_product_id ()
    {
        return tefsal_product_id;
    }

    public void setTefsal_product_id (String tefsal_product_id)
    {
        this.tefsal_product_id = tefsal_product_id;
    }

    public String getPublished ()
    {
        return published;
    }

    public void setPublished (String published)
    {
        this.published = published;
    }

    public String getMin_delivery_days ()
    {
        return min_delivery_days;
    }

    public void setMin_delivery_days (String min_delivery_days)
    {
        this.min_delivery_days = min_delivery_days;
    }

    public String getStore_name ()
    {
        return store_name;
    }

    public void setStore_name (String store_name)
    {
        this.store_name = store_name;
    }

    public String getBrand_name ()
    {
        return brand_name;
    }

    public void setBrand_name (String brand_name)
    {
        this.brand_name = brand_name;
    }

}
