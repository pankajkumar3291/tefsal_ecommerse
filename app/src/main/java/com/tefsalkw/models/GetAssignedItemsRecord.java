package com.tefsalkw.models;

import java.util.ArrayList;

/**
 * Created by Rituparna Khadka on 1/31/2018.
 */

public class GetAssignedItemsRecord
{

    private String dishdasha_tailor_product_name;

    private String dishdasha_tailor_product_price;

    private ArrayList<GetAssaignedItemsData> data;

    private String store_id;

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

    public ArrayList<GetAssaignedItemsData> getData ()
    {
        return data;
    }

    public void setData (ArrayList<GetAssaignedItemsData> data)
    {
        this.data = data;
    }

    public String getStore_id ()
    {
        return store_id;
    }

    public void setStore_id (String store_id)
    {
        this.store_id = store_id;
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
        return "ClassPojo [dishdasha_tailor_product_name = "+dishdasha_tailor_product_name+", dishdasha_tailor_product_price = "+dishdasha_tailor_product_price+", data = "+data+", store_id = "+store_id+", tefsal_product_id = "+tefsal_product_id+"]";
    }
}
