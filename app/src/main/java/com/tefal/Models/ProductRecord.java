package com.tefal.Models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Rituparna Khadka on 11/14/2017.
 */

public class ProductRecord implements Serializable
{
    private List<ProductSizes> sizes;


    private String measurements;

    private String brand_image;

    private String sub_color;

    private String color;

    private String product_name;

    private String product_desc;

    private List<String> product_images;

    private String tefsal_product_id;

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    private String store_id;

    private String attribute_id;

    private String store_name;

    private String brand_name;

    private String min_delivery_days;
    private String max_delivery_days;

    private  String product_discount;

    private  String thumb_url;

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public List<ProductSizes>  getSizes ()
    {
        return sizes;
    }

    public void setSizes (List<ProductSizes>  sizes)
    {
        this.sizes = sizes;
    }

    public String getMeasurements() {
        return measurements;
    }

    public void setMeasurements(String measurements) {
        this.measurements = measurements;
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

    public String getTefsal_product_id ()
    {
        return tefsal_product_id;
    }

    public void setTefsal_product_id (String tefsal_product_id)
    {
        this.tefsal_product_id = tefsal_product_id;
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


    public String getMin_delivery_days() {
        return min_delivery_days;
    }

    public void setMin_delivery_days(String min_delivery_days) {
        this.min_delivery_days = min_delivery_days;
    }

    public String getMax_delivery_days() {
        return max_delivery_days;
    }

    public void setMax_delivery_days(String max_delivery_days) {
        this.max_delivery_days = max_delivery_days;
    }
    public String getProduct_discount() {
        return product_discount;
    }

    public void setProduct_discount(String product_discount) {
        this.product_discount = product_discount;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [sizes = "+sizes+",measurements ="+measurements+", brand_image = "+brand_image+", sub_color = "+sub_color+", color = "+color+", product_name = "+product_name+", product_desc = "+product_desc+", product_images = "+product_images+", tefsal_product_id = "+tefsal_product_id+", attribute_id = "+attribute_id+", store_name = "+store_name+", brand_name = "+brand_name+",min_delivery_days ="+min_delivery_days+",max_delivery_days="+max_delivery_days+", product_discount="+product_discount+"]";
    }
}

