package com.tefsalkw.models;

public class ColorsNew {

    private String sub_color;

    private String price;

    private String color;

    private String[] images;

    private String qty;

    private String attribute_id;

    private String info;

    public String getSub_color() {
        return sub_color;
    }

    public void setSub_color(String sub_color) {
        this.sub_color = sub_color;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getAttribute_id() {
        return attribute_id;
    }

    public void setAttribute_id(String attribute_id) {
        this.attribute_id = attribute_id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "ClassPojo [sub_color = " + sub_color + ", price = " + price + ", color = " + color + ", images = " + images + ", qty = " + qty + ", attribute_id = " + attribute_id + ", info = " + info + "]";
    }
}


