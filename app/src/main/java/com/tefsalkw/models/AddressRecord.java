package com.tefsalkw.models;

import java.io.Serializable;

/**
 * Created by CREST-Sys-01 on 02/10/17.
 */

public class AddressRecord implements Serializable{

    private String address_id;

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

    private String province;

    private String country;

    private String country_iso;

    private String province_id;

    private String  area_id;

    private String flat_number;

    private String phone_number;

    public boolean isDefaultAddress() {
        return isDefaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        isDefaultAddress = defaultAddress;
    }

    private boolean isDefaultAddress = false;

    public String getFlat_number() {
        return flat_number;
    }

    public void setFlat_number(String flat_number) {
        this.flat_number = flat_number;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

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
        return province;
    }

    public void setCity (String city)
    {
        this.province = city;
    }

    public String getCountry ()
    {
        return country;
    }

    public void setCountry (String country)
    {
        this.country = country;
    }

    public String getCountry_iso() {
        return country_iso;
    }

    public void setCountry_iso(String country_iso) {
        this.country_iso = country_iso;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [flat_number = "+flat_number+"phone_number ="+phone_number+" address_id = "+address_id+"area = "+area+", paci_number = "+paci_number+", floor = "+floor+", avenue = "+avenue+", street = "+street+", address_name = "+address_name+", block = "+block+", addt_info = "+addt_info+", house = "+house+", customer_id = "+customer_id+", city = "+province+", country = "+country+",country_iso ="+country_iso+",province_id = "+province_id+", area_id ="+area_id+"]";
    }
}

