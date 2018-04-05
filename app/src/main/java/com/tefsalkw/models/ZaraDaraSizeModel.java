package com.tefsalkw.models;

import java.io.Serializable;

/**
 * Created by Dell on 20-03-2018.
 */

public class ZaraDaraSizeModel implements Serializable{

    private String size;
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
}
