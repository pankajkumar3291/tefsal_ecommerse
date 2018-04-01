package com.tefsalkw.Models;

import java.util.List;

/**
 * Created by Hp on 16-11-2017.
 */

public class OrderRecord {
    private String amount;

    private List<OrderItems> items;

    private String delivery_address;

    private String created_at;

    private String user_id;

    private String order_id;

    private String payment_method;

    private String order_status;

    public String getAmount ()
    {
        return amount;
    }

    public void setAmount (String amount)
    {
        this.amount = amount;
    }

    public List<OrderItems> getItems ()
    {
        return items;
    }

    public void setItems (List<OrderItems> items)
    {
        this.items = items;
    }

    public String getDelivery_address ()
    {
        return delivery_address;
    }

    public void setDelivery_address (String delivery_address)
    {
        this.delivery_address = delivery_address;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getOrder_id ()
    {
        return order_id;
    }

    public void setOrder_id (String order_id)
    {
        this.order_id = order_id;
    }

    public String getPayment_method ()
    {
        return payment_method;
    }

    public void setPayment_method (String payment_method)
    {
        this.payment_method = payment_method;
    }

    public String getOrder_status ()
    {
        return order_status;
    }

    public void setOrder_status (String order_status)
    {
        this.order_status = order_status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [amount = "+amount+", items = "+items+", delivery_address = "+delivery_address+", created_at = "+created_at+", user_id = "+user_id+", order_id = "+order_id+", payment_method = "+payment_method+", order_status = "+order_status+"]";
    }
}

