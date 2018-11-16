package com.tefsalkw.models;

import java.io.Serializable;
import java.util.List;

public class Order_items implements Serializable {

    private String total_amount;

    private List<Tailor_services> tailor_services;

    private String item_price;

    private String product_id;

    private String status;

    private String product_name;

    private String image;

    private String store_id;

    private String item_quantity;

    private String item_details;

    private String product_desc;

    private String order_id;

    private String id;

    private String item_id;

    private String store_name;

    public String getStore_name_arabic() {
        return store_name_arabic;
    }

    public void setStore_name_arabic(String store_name_arabic) {
        this.store_name_arabic = store_name_arabic;
    }

    private String store_name_arabic;
    private String item_type;

    private String Brandname;
    private String size;

    private String grand_total;

    public String getStyle_name() {
        return style_name;
    }

    public void setStyle_name(String style_name) {
        this.style_name = style_name;
    }

    private String style_name;


    public String getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(String grand_total) {
        this.grand_total = grand_total;
    }

    public String getTailor_total_qty() {
        return tailor_total_qty;
    }

    public void setTailor_total_qty(String tailor_total_qty) {
        this.tailor_total_qty = tailor_total_qty;
    }

    private String tailor_total_qty;
    public String getExpected_delivery_date() {
        return expected_delivery_date;
    }

    public void setExpected_delivery_date(String expected_delivery_date) {
        this.expected_delivery_date = expected_delivery_date;
    }

    private String expected_delivery_date = "";

    public String getStyleName() {
        return StyleName;
    }

    public void setStyleName(String styleName) {
        StyleName = styleName;
    }

    private String StyleName = "Test";


    public String getBrandname() {
        return Brandname;
    }

    public void setBrandname(String brandname) {
        Brandname = brandname;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        size = size;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    private String color;


    public String getTotal_amount ()
    {
        return total_amount;
    }

    public void setTotal_amount (String total_amount)
    {
        this.total_amount = total_amount;
    }

    public List<Tailor_services> getTailor_services ()
    {
        return tailor_services;
    }

    public void setTailor_services (List<Tailor_services> tailor_services)
    {
        this.tailor_services = tailor_services;
    }

    public String getItem_price ()
    {
        return item_price;
    }

    public void setItem_price (String item_price)
    {
        this.item_price = item_price;
    }

    public String getProduct_id ()
    {
        return product_id;
    }

    public void setProduct_id (String product_id)
    {
        this.product_id = product_id;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getProduct_name ()
    {
        return product_name;
    }

    public void setProduct_name (String product_name)
    {
        this.product_name = product_name;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public String getStore_id ()
    {
        return store_id;
    }

    public void setStore_id (String store_id)
    {
        this.store_id = store_id;
    }

    public String getItem_quantity ()
    {
        return item_quantity;
    }

    public void setItem_quantity (String item_quantity)
    {
        this.item_quantity = item_quantity;
    }

    public String getItem_details ()
    {
        return item_details;
    }

    public void setItem_details (String item_details)
    {
        this.item_details = item_details;
    }

    public String getProduct_desc ()
    {
        return product_desc;
    }

    public void setProduct_desc (String product_desc)
    {
        this.product_desc = product_desc;
    }

    public String getOrder_id ()
    {
        return order_id;
    }

    public void setOrder_id (String order_id)
    {
        this.order_id = order_id;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getItem_id ()
    {
        return item_id;
    }

    public void setItem_id (String item_id)
    {
        this.item_id = item_id;
    }

    public String getStore_name ()
    {
        return store_name;
    }

    public void setStore_name (String store_name)
    {
        this.store_name = store_name;
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
        return "ClassPojo [total_amount = "+total_amount+", tailor_services = "+tailor_services+", item_price = "+item_price+", product_id = "+product_id+", status = "+status+", product_name = "+product_name+", image = "+image+", store_id = "+store_id+", item_quantity = "+item_quantity+", item_details = "+item_details+", product_desc = "+product_desc+", order_id = "+order_id+", id = "+id+", item_id = "+item_id+", store_name = "+store_name+", item_type = "+item_type+"]";
    }
    
}
