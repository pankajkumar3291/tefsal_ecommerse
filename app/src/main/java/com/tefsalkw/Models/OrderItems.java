package com.tefsalkw.Models;

/**
 * Created by Hp on 16-11-2017.
 */

public class OrderItems {

    private String total_amount;

    private String price;

    private String product_name;

    private String item_quantity;

    private String product_desc;

    private String brand_name;

    private String item_type;

    private String size;

    public String getTotal_amount ()
    {
        return total_amount;
    }

    public void setTotal_amount (String total_amount)
    {
        this.total_amount = total_amount;
    }

    public String getPrice ()
    {
        return price;
    }

    public void setPrice (String price)
    {
        this.price = price;
    }

    public String getProduct_name ()
    {
        return product_name;
    }

    public void setProduct_name (String product_name)
    {
        this.product_name = product_name;
    }

    public String getItem_quantity ()
    {
        return item_quantity;
    }

    public void setItem_quantity (String item_quantity)
    {
        this.item_quantity = item_quantity;
    }

    public String getProduct_desc ()
    {
        return product_desc;
    }

    public void setProduct_desc (String product_desc)
    {
        this.product_desc = product_desc;
    }

    public String getBrand_name ()
    {
        return brand_name;
    }

    public void setBrand_name (String brand_name)
    {
        this.brand_name = brand_name;
    }

    public String getItem_type ()
    {
        return item_type;
    }

    public void setItem_type (String item_type)
    {
        this.item_type = item_type;
    }

    public String getSize ()
    {
        return size;
    }

    public void setSize (String size)
    {
        this.size = size;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [total_amount = "+total_amount+", price = "+price+", product_name = "+product_name+", item_quantity = "+item_quantity+", product_desc = "+product_desc+", brand_name = "+brand_name+", item_type = "+item_type+", size = "+size+"]";
    }
}

