package com.tefsalkw.Models;

import java.io.Serializable;

/**
 * Created by Rituparna Khadka on 1/31/2018.
 */

public class TailoringRecord implements Serializable
{
    private String product_id;

    private String remain_textile;

    private String item_quantity;

    private String total_textile;

    private String dishdasha_product_name;

    private Boolean isChecked;

    public Boolean getChecked()
    {
        return isChecked;
    }

    public void setChecked(Boolean checked)
    {
        isChecked = checked;
    }

    public String getProduct_id ()
    {
        return product_id;
    }

    public void setProduct_id (String product_id)
    {
        this.product_id = product_id;
    }

    public String getRemain_textile ()
    {
        return remain_textile;
    }

    public void setRemain_textile (String remain_textile)
    {
        this.remain_textile = remain_textile;
    }

    public String getItem_quantity ()
    {
        return item_quantity;
    }

    public void setItem_quantity (String item_quantity)
    {
        this.item_quantity = item_quantity;
    }

    public String getTotal_textile ()
    {
        return total_textile;
    }

    public void setTotal_textile (String total_textile)
    {
        this.total_textile = total_textile;
    }

    public String getDishdasha_product_name ()
    {
        return dishdasha_product_name;
    }

    public void setDishdasha_product_name (String dishdasha_product_name)
    {
        this.dishdasha_product_name = dishdasha_product_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [product_id = "+product_id+", remain_textile = "+remain_textile+", item_quantity = "+item_quantity+", total_textile = "+total_textile+", dishdasha_product_name = "+dishdasha_product_name+"]";
    }
}
