package com.tefsalkw.models;

/**
 * Created by new on 9/26/2017.
 */

public class D_StoreRecord {

    private String open_to;

    private String store_addt_info;

    private String store_id;

    private String store_image;

    private String business_add;

    private String open_from;

    private String store_rating;

    private String cat_id;

    private String sub_cat_id;

    private String sub_cat_name;

    private String bio;

    private String store_name;

    private String store_email;


    private String min_delivery_days;
    private String max_delivery_days;


    private String store_discount;

    private String is_open;

    public String getIs_open() {
        return is_open;
    }

    public void setIs_open(String is_open) {
        this.is_open = is_open;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    private String is_active;

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

    public String getOpen_to ()
    {
        return open_to;
    }

    public void setOpen_to (String open_to)
    {
        this.open_to = open_to;
    }

    public String getStore_addt_info ()
    {
        return store_addt_info;
    }

    public void setStore_addt_info (String store_addt_info)
    {
        this.store_addt_info = store_addt_info;
    }

    public String getStore_id ()
    {
        return store_id;
    }

    public void setStore_id (String store_id)
    {
        this.store_id = store_id;
    }

    public String getStore_image ()
    {
        return store_image;
    }

    public void setStore_image (String store_image)
    {
        this.store_image = store_image;
    }

    public String getBusiness_add ()
    {
        return business_add;
    }

    public void setBusiness_add (String business_add)
    {
        this.business_add = business_add;
    }

    public String getOpen_from ()
    {
        return open_from;
    }

    public void setOpen_from (String open_from)
    {
        this.open_from = open_from;
    }

    public String getStore_rating ()
    {
        return store_rating;
    }

    public void setStore_rating (String store_rating)
    {
        this.store_rating = store_rating;
    }

    public String getCat_id ()
    {
        return cat_id;
    }

    public void setCat_id (String cat_id)
    {
        this.cat_id = cat_id;
    }

    public String getSub_cat_id ()
    {
        return sub_cat_id;
    }

    public void setSub_cat_id (String sub_cat_id)
    {
        this.sub_cat_id = sub_cat_id;
    }

    public String getSub_cat_name ()
    {
        return sub_cat_name;
    }

    public void setSub_cat_name (String sub_cat_name)
    {
        this.sub_cat_name = sub_cat_name;
    }

    public String getBio ()
    {
        return bio;
    }

    public void setBio (String bio)
    {
        this.bio = bio;
    }

    public String getStore_name ()
    {
        return store_name;
    }

    public void setStore_name (String store_name)
    {
        this.store_name = store_name;
    }

    public String getStore_email ()
    {
        return store_email;
    }

    public void setStore_email (String store_email)
    {
        this.store_email = store_email;
    }

    public String getStore_discount() {
        return store_discount;
    }

    public void setStore_discount(String store_discount) {
        this.store_discount = store_discount;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [open_to = "+open_to+", store_addt_info = "+store_addt_info+", store_id = "+store_id+", store_image = "+store_image+", business_add = "+business_add+", open_from = "+open_from+", store_rating = "+store_rating+", cat_id = "+cat_id+", sub_cat_id = "+sub_cat_id+", sub_cat_name = "+sub_cat_name+", bio = "+bio+", store_name = "+store_name+", store_email = "+store_email+",min_delivery_days = "+min_delivery_days+",max_delivery_days = "+max_delivery_days+",store_discount="+store_discount+"]";
    }
}
