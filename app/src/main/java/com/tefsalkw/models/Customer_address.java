package com.tefsalkw.models;

import java.io.Serializable;

public class Customer_address implements Serializable {

    private String phone_number = "";

    private String isdefault= "";

    private String flat_number= "";

    private String street= "";

    private String deleted_at= "";

    private String block= "";

    private String addt_info= "";

    private String house= "";

    private String country= "";

    private String id;

    private String updated_at= "";

    private String area= "";

    private String floor= "";

    private String paci_number= "";

    private String avenue = "";

    private String address_name= "";

    private String province= "";

    private String created_at= "";

    private String customer_id= "";

    public String getPhone_number ()
    {
        return phone_number;
    }

    public void setPhone_number (String phone_number)
    {
        this.phone_number = phone_number;
    }

    public String getIsdefault ()
    {
        return isdefault;
    }

    public void setIsdefault (String isdefault)
    {
        this.isdefault = isdefault;
    }

    public String getFlat_number ()
    {
        return flat_number;
    }

    public void setFlat_number (String flat_number)
    {
        this.flat_number = flat_number;
    }

    public String getStreet ()
    {
        return street;
    }

    public void setStreet (String street)
    {
        this.street = street;
    }

    public String getDeleted_at ()
    {
        return deleted_at;
    }

    public void setDeleted_at (String deleted_at)
    {
        this.deleted_at = deleted_at;
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

    public String getCountry ()
    {
        return country;
    }

    public void setCountry (String country)
    {
        this.country = country;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public void setUpdated_at (String updated_at)
    {
        this.updated_at = updated_at;
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

    public String getPaci_number ()
    {
        return paci_number;
    }

    public void setPaci_number (String paci_number)
    {
        this.paci_number = paci_number;
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

    public String getCustomer_id ()
    {
        return customer_id;
    }

    public void setCustomer_id (String customer_id)
    {
        this.customer_id = customer_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [phone_number = "+phone_number+", isdefault = "+isdefault+", flat_number = "+flat_number+", street = "+street+", deleted_at = "+deleted_at+", block = "+block+", addt_info = "+addt_info+", house = "+house+", country = "+country+", id = "+id+", updated_at = "+updated_at+", area = "+area+", floor = "+floor+", paci_number = "+paci_number+", avenue = "+avenue+", address_name = "+address_name+", province = "+province+", created_at = "+created_at+", customer_id = "+customer_id+"]";
    }
    
}
