package com.tefsalkw.models;

import java.io.Serializable;

public class CartItems implements Serializable {
    private String product_id;

    private String item_id;

    private Item_details item_details;

    private String category_id;

    public String getProduct_id ()
    {
        return product_id;
    }

    public void setProduct_id (String product_id)
    {
        this.product_id = product_id;
    }

    public String getItem_id ()
    {
        return item_id;
    }

    public void setItem_id (String item_id)
    {
        this.item_id = item_id;
    }

    public Item_details getItem_details ()
    {
        return item_details;
    }

    public void setItem_details (Item_details item_details)
    {
        this.item_details = item_details;
    }

    public String getCategory_id ()
    {
        return category_id;
    }

    public void setCategory_id (String category_id)
    {
        this.category_id = category_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [product_id = "+product_id+", item_id = "+item_id+", item_details = "+item_details+", category_id = "+category_id+"]";
    }
}
