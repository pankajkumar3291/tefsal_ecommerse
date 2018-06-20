package com.tefsalkw.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccSizes {

    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("price")
    @Expose
    private String price;

    public String getAttribute_meta_id() {
        return attribute_meta_id;
    }

    public void setAttribute_meta_id(String attribute_meta_id) {
        this.attribute_meta_id = attribute_meta_id;
    }

    @SerializedName("attribute_meta_id")
    @Expose
    private String attribute_meta_id;


    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
