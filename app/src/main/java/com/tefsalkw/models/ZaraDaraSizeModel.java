package com.tefsalkw.models;

import java.io.Serializable;

/**
 * Created by Dell on 20-03-2018.
 */

public class ZaraDaraSizeModel implements Serializable{

    private String size;

    public String getSize_arabic() {
        return size_arabic;
    }

    public void setSize_arabic(String size_arabic) {
        this.size_arabic = size_arabic;
    }

    private String size_arabic;

    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private int quantity;
    private String price;

    public String getAttribute_meta_id() {
        return attribute_meta_id;
    }

    public void setAttribute_meta_id(String attribute_meta_id) {
        this.attribute_meta_id = attribute_meta_id;
    }

    private String attribute_meta_id;
}
