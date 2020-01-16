package com.tefsalkw.models;

import java.util.List;

public class AccessoriesModelNew {

    private List<SizesNew> sizes;

    private String tag;

    private String product_name;

    private String store_id;

    private String product_desc;

    private String tefsal_product_id;

    private String brand_name;

    private String brand_image;

    private String product_generated_id;

    private String max_delivery_days;

    private String published;

    private String store_name;

    private String min_delivery_days;

    private String product_discount;

    public List<SizesNew> getSizes ()
    {
        return sizes;
    }

    public void setSizes (List<SizesNew> sizes)
    {
        this.sizes = sizes;
    }

    public String getTag ()
    {
        return tag;
    }

    public void setTag (String tag)
    {
        this.tag = tag;
    }

    public String getProduct_name ()
    {
        return product_name;
    }

    public void setProduct_name (String product_name)
    {
        this.product_name = product_name;
    }

    public String getStore_id ()
    {
        return store_id;
    }

    public void setStore_id (String store_id)
    {
        this.store_id = store_id;
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

    public String getBrand_name ()
    {
        return brand_name;
    }

    public void setBrand_name (String brand_name)
    {
        this.brand_name = brand_name;
    }

    public String getBrand_image ()
    {
        return brand_image;
    }

    public void setBrand_image (String brand_image)
    {
        this.brand_image = brand_image;
    }

    public String getProduct_generated_id ()
    {
        return product_generated_id;
    }

    public void setProduct_generated_id (String product_generated_id)
    {
        this.product_generated_id = product_generated_id;
    }

    public String getMax_delivery_days ()
    {
        return max_delivery_days;
    }

    public void setMax_delivery_days (String max_delivery_days)
    {
        this.max_delivery_days = max_delivery_days;
    }

    public String getPublished ()
    {
        return published;
    }

    public void setPublished (String published)
    {
        this.published = published;
    }

    public String getStore_name ()
    {
        return store_name;
    }

    public void setStore_name (String store_name)
    {
        this.store_name = store_name;
    }

    public String getMin_delivery_days ()
    {
        return min_delivery_days;
    }

    public void setMin_delivery_days (String min_delivery_days)
    {
        this.min_delivery_days = min_delivery_days;
    }

    public String getProduct_discount ()
    {
        return product_discount;
    }

    public void setProduct_discount (String product_discount)
    {
        this.product_discount = product_discount;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [sizes = "+sizes+", tag = "+tag+", product_name = "+product_name+", store_id = "+store_id+", product_desc = "+product_desc+", tefsal_product_id = "+tefsal_product_id+", brand_name = "+brand_name+", brand_image = "+brand_image+", product_generated_id = "+product_generated_id+", max_delivery_days = "+max_delivery_days+", published = "+published+", store_name = "+store_name+", min_delivery_days = "+min_delivery_days+", product_discount = "+product_discount+"]";
    }

}
