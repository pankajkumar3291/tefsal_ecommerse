package com.tefsalkw.models;

/**
 * Created by Dell on 19-03-2018.
 */

public class DaraAbayaColorsModel {

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
