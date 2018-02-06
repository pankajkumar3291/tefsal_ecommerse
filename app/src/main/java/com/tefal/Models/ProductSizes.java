package com.tefal.Models;

import java.io.Serializable;

/**
 * Created by Rituparna Khadka on 11/14/2017.
 */

public class ProductSizes implements Serializable
{
    private String price;

    private String quantity;

    private String size;

    public String getPrice ()
    {
        return price;
    }

    public void setPrice (String price)
    {
        this.price = price;
    }

    public String getQuantity ()
    {
        return quantity;
    }

    public void setQuantity (String quantity)
    {
        this.quantity = quantity;
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
        return "ClassPojo [price = "+price+", quantity = "+quantity+", size = "+size+"]";
    }
}

