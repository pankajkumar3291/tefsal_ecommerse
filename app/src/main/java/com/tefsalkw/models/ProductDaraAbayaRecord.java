package com.tefsalkw.models;

import java.util.List;

/**
 * Created by Hp on 01-11-2017.
 */

public class ProductDaraAbayaRecord {

    private List<ProductDaraAbayaSizes> sizes;

    private String brand_image;

    private String sub_color;

    private String color;

    private String product_name;

    private String product_desc;

    private List<String> product_images;

    private String attribute_id;

    private String store_name;

    private String brand_name;

    public List<ProductDaraAbayaSizes> getSizes ()
    {
        return sizes;
    }

    public void setSizes (List<ProductDaraAbayaSizes> sizes)
    {
        this.sizes = sizes;
    }

    public String getBrand_image ()
    {
        return brand_image;
    }

    public void setBrand_image (String brand_image)
    {
        this.brand_image = brand_image;
    }

    public String getSub_color ()
    {
        return sub_color;
    }

    public void setSub_color (String sub_color)
    {
        this.sub_color = sub_color;
    }

    public String getColor ()
    {
        return color;
    }

    public void setColor (String color)
    {
        this.color = color;
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

    public List<String> getProduct_images ()
    {
        return product_images;
    }

    public void setProduct_images (List<String> product_images)
    {
        this.product_images = product_images;
    }

    public String getAttribute_id ()
    {
        return attribute_id;
    }

    public void setAttribute_id (String attribute_id)
    {
        this.attribute_id = attribute_id;
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
        return "ClassPojo [sizes = "+sizes+", brand_image = "+brand_image+", sub_color = "+sub_color+", color = "+color+", product_name = "+product_name+", product_desc = "+product_desc+", product_images = "+product_images+", attribute_id = "+attribute_id+", store_name = "+store_name+", brand_name = "+brand_name+"]";
    }
}

