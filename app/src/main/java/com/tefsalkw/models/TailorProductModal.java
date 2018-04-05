package com.tefsalkw.models;

/**
 * Created by Hp on 16-10-2017.
 */

public class TailorProductModal {
    private String dishdasha_tailor_product_name;

    private String dishdasha_tailor_product_price;

    private String dishdasha_tailor_product_id;

    private String store_id;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private int position;


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

    public String getDishdasha_tailor_product_id ()
    {
        return dishdasha_tailor_product_id;
    }

    public void setDishdasha_tailor_product_id (String dishdasha_tailor_product_id)
    {
        this.dishdasha_tailor_product_id = dishdasha_tailor_product_id;
    }

    public String getStore_id ()
    {
        return store_id;
    }

    public void setStore_id (String store_id)
    {
        this.store_id = store_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [dishdasha_tailor_product_name = "+dishdasha_tailor_product_name+", dishdasha_tailor_product_price = "+dishdasha_tailor_product_price+", dishdasha_tailor_product_id = "+dishdasha_tailor_product_id+", store_id = "+store_id+"]";
    }
}

