package com.tefsalkw.Models;

import java.util.List;

/**
 * Created by Dell on 03/13/2018.
 */

public class AccessoryDetailRecord {

    private List<Sizes> sizes;

    private String sub_cat_name;

    private String product_generated_id;

    private String tag;

    private String max_delivery_days;

    private String product_name;

    private String product_desc;

    private String tefsal_product_id;

    private String published;

    private String min_delivery_days;

    private String store_name;

    private String brand_name;

    public List<Sizes> getSizes ()
    {
        return sizes;
    }

    public void setSizes (List<Sizes> sizes)
    {
        this.sizes = sizes;
    }

    public String getSub_cat_name ()
    {
        return sub_cat_name;
    }

    public void setSub_cat_name (String sub_cat_name)
    {
        this.sub_cat_name = sub_cat_name;
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

    @Override
    public String toString()
    {
        return "ClassPojo [sizes = "+sizes+", sub_cat_name = "+sub_cat_name+", product_generated_id = "+product_generated_id+", tag = "+tag+", max_delivery_days = "+max_delivery_days+", product_name = "+product_name+", product_desc = "+product_desc+", tefsal_product_id = "+tefsal_product_id+", published = "+published+", min_delivery_days = "+min_delivery_days+", store_name = "+store_name+", brand_name = "+brand_name+"]";
    }

}
