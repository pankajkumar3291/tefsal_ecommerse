package com.tefsalkw.models;

import java.io.Serializable;

/**
 * Created by AC 101 on 25-10-2017.
 */

public class AddCartResponse implements Serializable
{
    private String message;

    private String cart_id;

    private int status;

    private String item_type;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getCart_id ()
    {
        return cart_id;
    }

    public void setCart_id (String cart_id)
    {
        this.cart_id = cart_id;
    }

    public int getStatus ()
    {
        return status;
    }

    public void setStatus (int status)
    {
        this.status = status;
    }

    public String getItem_type ()
    {
        return item_type;
    }

    public void setItem_type (String item_type)
    {
        this.item_type = item_type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", cart_id = "+cart_id+", status = "+status+", item_type = "+item_type+"]";
    }
}
