package com.tefsalkw.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Hp on 16-11-2017.
 */

public class OrderRecord implements Serializable {
    private String phone_number;

    private String flat_number;

    private String delivery_address;

    private String street;

    private String block;

    private String house;

    private String order_id;

    private String order_status;

    private String country;

    private String amount;

    private String area;

    private String floor;

    private List<OrderItems> items;

    private String avenue;

    private String address_name;

    private String province;

    private String created_at;

    private String user_id;

    private String payment_method;

    public String getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    private String delivery_status;

    public String getPhone_number ()
    {
        return phone_number;
    }

    public void setPhone_number (String phone_number)
    {
        this.phone_number = phone_number;
    }

    public String getFlat_number ()
    {
        return flat_number;
    }

    public void setFlat_number (String flat_number)
    {
        this.flat_number = flat_number;
    }

    public String getDelivery_address ()
    {
        return delivery_address;
    }

    public void setDelivery_address (String delivery_address)
    {
        this.delivery_address = delivery_address;
    }

    public String getStreet ()
    {
        return street;
    }

    public void setStreet (String street)
    {
        this.street = street;
    }

    public String getBlock ()
    {
        return block;
    }

    public void setBlock (String block)
    {
        this.block = block;
    }

    public String getHouse ()
    {
        return house;
    }

    public void setHouse (String house)
    {
        this.house = house;
    }

    public String getOrder_id ()
    {
        return order_id;
    }

    public void setOrder_id (String order_id)
    {
        this.order_id = order_id;
    }

    public String getOrder_status ()
    {
        return order_status;
    }

    public void setOrder_status (String order_status)
    {
        this.order_status = order_status;
    }

    public String getCountry ()
    {
        return country;
    }

    public void setCountry (String country)
    {
        this.country = country;
    }

    public String getAmount ()
    {
        return amount;
    }

    public void setAmount (String amount)
    {
        this.amount = amount;
    }

    public String getArea ()
    {
        return area;
    }

    public void setArea (String area)
    {
        this.area = area;
    }

    public String getFloor ()
    {
        return floor;
    }

    public void setFloor (String floor)
    {
        this.floor = floor;
    }

    public List<OrderItems> getItems ()
    {
        return items;
    }

    public void setItems (List<OrderItems> items)
    {
        this.items = items;
    }

    public String getAvenue ()
    {
        return avenue;
    }

    public void setAvenue (String avenue)
    {
        this.avenue = avenue;
    }

    public String getAddress_name ()
    {
        return address_name;
    }

    public void setAddress_name (String address_name)
    {
        this.address_name = address_name;
    }

    public String getProvince ()
    {
        return province;
    }

    public void setProvince (String province)
    {
        this.province = province;
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

    public String getPayment_method ()
    {
        return payment_method;
    }

    public void setPayment_method (String payment_method)
    {
        this.payment_method = payment_method;
    }


}

