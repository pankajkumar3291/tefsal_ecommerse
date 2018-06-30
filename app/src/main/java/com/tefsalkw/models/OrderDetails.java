package com.tefsalkw.models;

import java.io.Serializable;
import java.util.List;

public class OrderDetails implements Serializable{

    private String promo_id;

    private Customer_address customer_address;

    private String guest_id;

    private String user_type;

    private String delivery_address;

    private String order_id;

    private String transaction_id;

    private String order_status;

    private String amount;

    private String updated_at;

    private String cart_id;

    private String delivery_status;

    private List<Order_items> order_items;

    private String created_at;

    private String driver_id;

    private String user_id;

    private String invoice_id;

    private String payment_method;

    private String trcking_id;

    private String promo_amount;

    public String getPromo_id ()
    {
        return promo_id;
    }

    public void setPromo_id (String promo_id)
    {
        this.promo_id = promo_id;
    }

    public Customer_address getCustomer_address ()
    {
        return customer_address;
    }

    public void setCustomer_address (Customer_address customer_address)
    {
        this.customer_address = customer_address;
    }

    public String getGuest_id ()
    {
        return guest_id;
    }

    public void setGuest_id (String guest_id)
    {
        this.guest_id = guest_id;
    }

    public String getUser_type ()
    {
        return user_type;
    }

    public void setUser_type (String user_type)
    {
        this.user_type = user_type;
    }

    public String getDelivery_address ()
    {
        return delivery_address;
    }

    public void setDelivery_address (String delivery_address)
    {
        this.delivery_address = delivery_address;
    }

    public String getOrder_id ()
    {
        return order_id;
    }

    public void setOrder_id (String order_id)
    {
        this.order_id = order_id;
    }

    public String getTransaction_id ()
    {
        return transaction_id;
    }

    public void setTransaction_id (String transaction_id)
    {
        this.transaction_id = transaction_id;
    }

    public String getOrder_status ()
    {
        return order_status;
    }

    public void setOrder_status (String order_status)
    {
        this.order_status = order_status;
    }

    public String getAmount ()
    {
        return amount;
    }

    public void setAmount (String amount)
    {
        this.amount = amount;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public void setUpdated_at (String updated_at)
    {
        this.updated_at = updated_at;
    }

    public String getCart_id ()
    {
        return cart_id;
    }

    public void setCart_id (String cart_id)
    {
        this.cart_id = cart_id;
    }

    public String getDelivery_status ()
    {
        return delivery_status;
    }

    public void setDelivery_status (String delivery_status)
    {
        this.delivery_status = delivery_status;
    }

    public List<Order_items> getOrder_items ()
    {
        return order_items;
    }

    public void setOrder_items (List<Order_items> order_items)
    {
        this.order_items = order_items;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public String getDriver_id ()
    {
        return driver_id;
    }

    public void setDriver_id (String driver_id)
    {
        this.driver_id = driver_id;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getInvoice_id ()
    {
        return invoice_id;
    }

    public void setInvoice_id (String invoice_id)
    {
        this.invoice_id = invoice_id;
    }

    public String getPayment_method ()
    {
        return payment_method;
    }

    public void setPayment_method (String payment_method)
    {
        this.payment_method = payment_method;
    }

    public String getTrcking_id ()
    {
        return trcking_id;
    }

    public void setTrcking_id (String trcking_id)
    {
        this.trcking_id = trcking_id;
    }

    public String getPromo_amount ()
    {
        return promo_amount;
    }

    public void setPromo_amount (String promo_amount)
    {
        this.promo_amount = promo_amount;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [promo_id = "+promo_id+", customer_address = "+customer_address+", guest_id = "+guest_id+", user_type = "+user_type+", delivery_address = "+delivery_address+", order_id = "+order_id+", transaction_id = "+transaction_id+", order_status = "+order_status+", amount = "+amount+", updated_at = "+updated_at+", cart_id = "+cart_id+", delivery_status = "+delivery_status+", order_items = "+order_items+", created_at = "+created_at+", driver_id = "+driver_id+", user_id = "+user_id+", invoice_id = "+invoice_id+", payment_method = "+payment_method+", trcking_id = "+trcking_id+", promo_amount = "+promo_amount+"]";
    }
    
}
