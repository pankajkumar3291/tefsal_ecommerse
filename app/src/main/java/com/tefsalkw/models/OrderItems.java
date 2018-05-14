package com.tefsalkw.models;

import java.io.Serializable;

/**
 * Created by Hp on 16-11-2017.
 */

public class OrderItems implements Serializable {

    private String total_amount;

    private String price;

    private String item_quantity;

    private String image;

    private String dishdasha_material;

    private String brand_name;

    private String dishdasha_pattern;

    private String item_type;

    private String dishdasha_feel;

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    private String  product_name;

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    private String product_desc;

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getStore_img() {
        return store_img;
    }

    public void setStore_img(String store_img) {
        this.store_img = store_img;
    }

    private String store_id;
    private String store_img;

    public ItemStyle getStyle() {
        return style;
    }

    public void setStyle(ItemStyle style) {
        this.style = style;
    }

    private  ItemStyle style;

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    private String store_name;
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

    public String getItem_quantity ()
    {
        return item_quantity;
    }

    public void setItem_quantity (String item_quantity)
    {
        this.item_quantity = item_quantity;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public String getDishdasha_material ()
    {
        return dishdasha_material;
    }

    public void setDishdasha_material (String dishdasha_material)
    {
        this.dishdasha_material = dishdasha_material;
    }

    public String getBrand_name ()
    {
        return brand_name;
    }

    public void setBrand_name (String brand_name)
    {
        this.brand_name = brand_name;
    }

    public String getDishdasha_pattern ()
    {
        return dishdasha_pattern;
    }

    public void setDishdasha_pattern (String dishdasha_pattern)
    {
        this.dishdasha_pattern = dishdasha_pattern;
    }

    public String getItem_type ()
    {
        return item_type;
    }

    public void setItem_type (String item_type)
    {
        this.item_type = item_type;
    }

    public String getDishdasha_feel ()
    {
        return dishdasha_feel;
    }

    public void setDishdasha_feel (String dishdasha_feel)
    {
        this.dishdasha_feel = dishdasha_feel;
    }


    public String getColor_name() {
        return color_name;
    }

    public void setColor_name(String color_name) {
        this.color_name = color_name;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String color_name;
    public String brandname;
    public String size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String  name;


    public int getMin_delivery_days() {
        return min_delivery_days;
    }

    public void setMin_delivery_days(int min_delivery_days) {
        this.min_delivery_days = min_delivery_days;
    }

    public int getMax_delivery_days() {
        return max_delivery_days;
    }

    public void setMax_delivery_days(int max_delivery_days) {
        this.max_delivery_days = max_delivery_days;
    }

    public int min_delivery_days;
    public int max_delivery_days;



}

