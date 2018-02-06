package com.tefal.Models;

import java.io.Serializable;

/**
 * Created by Rituparna Khadka on 11/15/2017.
 */

public class AddressRecordModel implements Serializable
{
    private String area;

    private String paci_number;

    private String floor;

    private String avenue;

    private String street;

    private String address_name;

    private String block;

    private String addt_info;

    private String house;

    private String customer_id;

    private String city;

    private String country;

    public String getArea ()
    {
        return area;
    }

    public void setArea (String area)
    {
        this.area = area;
    }

    public String getPaci_number ()
    {
         return paci_number;
    }

    public void setPaci_number (String paci_number)
    {
        this.paci_number = paci_number;
    }

    public String getFloor ()
    {
        return floor;
    }

    public void setFloor (String floor)
    {
        this.floor = floor;
    }

    public String getAvenue ()
{
    return avenue;
}

    public void setAvenue (String avenue)
    {
        this.avenue = avenue;
    }

    public String getStreet ()
    {
        return street;
    }

    public void setStreet (String street)
    {
        this.street = street;
    }

    public String getAddress_name ()
    {
        return address_name;
    }

    public void setAddress_name (String address_name)
    {
        this.address_name = address_name;
    }

    public String getBlock ()
    {
        return block;
    }

    public void setBlock (String block)
    {
        this.block = block;
    }

    public String getAddt_info ()
    {
        return addt_info;
    }

    public void setAddt_info (String addt_info)
    {
        this.addt_info = addt_info;
    }

    public String getHouse ()
{
    return house;
}

    public void setHouse (String house)
    {
        this.house = house;
    }

    public String getCustomer_id ()
    {
        return customer_id;
    }

    public void setCustomer_id (String customer_id)
    {
        this.customer_id = customer_id;
    }

    public String getCity ()
    {
        return city;
    }

    public void setCity (String city)
    {
        this.city = city;
    }

    public String getCountry ()
    {
        return country;
    }

    public void setCountry (String country)
    {
        this.country = country;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [area = "+area+", paci_number = "+paci_number+", floor = "+floor+", avenue = "+avenue+", street = "+street+", address_name = "+address_name+", block = "+block+", addt_info = "+addt_info+", house = "+house+", customer_id = "+customer_id+", city = "+city+", country = "+country+"]";
    }
}
